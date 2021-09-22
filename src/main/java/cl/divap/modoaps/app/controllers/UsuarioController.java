package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.helpers.string.HelperString;
import cl.divap.modoaps.app.models.dao.comuna.IComunaDao;
import cl.divap.modoaps.app.models.dao.nacionalidad.INacionalidadDao;
import cl.divap.modoaps.app.models.dao.rolePerfil.IRolePerfilDao;
import cl.divap.modoaps.app.models.dao.servicioSalud.IServicioSaludDao;
import cl.divap.modoaps.app.models.dao.sexo.ISexoDao;
import cl.divap.modoaps.app.models.entity.*;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import cl.divap.modoaps.app.validation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Secured({"ROLE_ADMIN", "ROLE_MINSAL"})
@Controller
@RequestMapping("/usuario")
@SessionAttributes("usuario")
public class UsuarioController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Autowired
    private ISexoDao sexoDao;

    @Autowired
    private INacionalidadDao nacionalidadDao;

    @Autowired
    private IServicioSaludDao servicioDao;

    @Autowired
    private IComunaDao comunaDao;

    @Autowired
    private IRolePerfilDao rolePerfilDao;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private ExisteUsuarioValidator existeUsuario;

    @Autowired
    private ExisteEmailValidator emailValidator;

    @Autowired
    private ExisteUsuarioServicio usuarioServicio;

    @Autowired
    private ExisteUsuarioComunaValidator usuarioComunaValidator;

    @Autowired
    private ConfirmPasswordRegexValidator confirmPasswordRegexValidator;

    @Autowired
    private ConfirmPasswordNotBlankValidator confirmPasswordNotBlankValidator;

    @Autowired
    private ConfirmPasswordSizeValidator confirmPasswordSizeValidator;

    @Autowired
    private PasswordNotBlankValidator passwordNotBlankValidator;

    @Autowired
    private PasswordSizeValidator passwordSizeValidator;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //
        // binder.setValidator(passwordValidator);
        binder.addValidators(passwordValidator);
        binder.addValidators(existeUsuario);
        binder.addValidators(emailValidator);
        binder.addValidators(usuarioServicio);
        binder.addValidators(usuarioComunaValidator);
        binder.addValidators(confirmPasswordNotBlankValidator);
        binder.addValidators(confirmPasswordRegexValidator);
        binder.addValidators(confirmPasswordSizeValidator);
        binder.addValidators(passwordNotBlankValidator);
        binder.addValidators(passwordSizeValidator);
    }

    @GetMapping("/listar")
    public String listar(Model model, Authentication authentication, HttpServletRequest request) {

        List<Usuario> usuarios = funcionarioService.findAllUsuarios();

        model.addAttribute("titulo", "Listado de Usuarios");
        model.addAttribute("usuarios", usuarios);

        return "usuario/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        //
        Usuario usuario = new Usuario();

        model.addAttribute("usuario", usuario);
        model.addAttribute("crear", true);
        model.addAttribute("titulo", "Crear Usuario");
        model.addAttribute("flag", true);
        model.addAttribute("boton", "Crear Usuario");

        return "usuario/form";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {
        //
        Usuario usuario = null;

        if(id != null && id > 0) {
            //
            usuario = funcionarioService.findUsuarioById(id);

            if(usuario == null) {
                //
                flash.addFlashAttribute("error", "El Id del Usuario no existe en la base de datos!");
                return "redirect:/usuario/listar";
            }

        } else {
            //
            flash.addFlashAttribute("error", "El Id del Usuario no puede ser cero!");
            return "redirect:/usuario/listar";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("crear", false);
        model.addAttribute("titulo", "Editar Usuario");
        model.addAttribute("boton", "Editar Usuario");

        return "usuario/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        //
        // passwordValidator.validate(usuario, result);

        if(result.hasErrors()) {
            //
            model.addAttribute("titulo", "Formulario Crear Usuario con errores");

            if(usuario.getId() == null) {
                model.addAttribute("boton", "Crear Usuario");
                model.addAttribute("crear", true);

            } else if(usuario.getId() != null && usuario.getId() > 0) {
                model.addAttribute("boton", "Editar Usuario");
                model.addAttribute("crear", false);
            }

            return "usuario/form";
        }

        Long codigoComuna = usuario.getComuna().getCodigoComuna();
        System.out.println("Id Comuna: " + codigoComuna);
        Comuna comuna = comunaDao.findByCodigoComuna(codigoComuna);
        usuario.setComuna(comuna);

        // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.YEAR, 10);   // 16
        Date futureDate = c.getTime();

        if(usuario.getEnabled()) {
            usuario.setLockoutEnd(null);
        } else {
            usuario.setLockoutEnd(futureDate);
        }

        usuario.setNombre(HelperString.toTitleCase(usuario.getNombre().trim().replaceAll(" +", " ")).toUpperCase());
        usuario.setApellidoPaterno(HelperString.toTitleCase(usuario.getApellidoPaterno().replaceAll("\\s{2,}", " ").trim()).toUpperCase());
        usuario.setApellidoMaterno(HelperString.toTitleCase(usuario.getApellidoMaterno()).toUpperCase());
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());

        System.out.println("Id Authority: " + usuario.getRolePerfil().getId());
        System.out.println("Authority: " + usuario.getRolePerfil().getRolePerfil());

        if(usuario.getId() != null && usuario.getId() > 0) {    // Editar
            //
            Usuario usuarioDb = funcionarioService.findUsuarioById(usuario.getId());
            usuario.setCreateAt(usuarioDb.getCreateAt());

            RolePerfil rolePerfil = rolePerfilDao.findById(usuario.getRolePerfil().getId());
            logger.info("Role Id Update: " + rolePerfil.getId());
            logger.info("Role Name Update: " + rolePerfil.getRolePerfil());
            logger.info("Usuario Id: " + usuario.getId());
            usuario.setRolePerfil(rolePerfil);
            funcionarioService.updateRoleByIdUsuario(usuario);

        } else {    // Crear
            //
            RolePerfil rolePerfil = rolePerfilDao.findById(usuario.getRolePerfil().getId());
            Role role = new Role();
            role.setAuthority(rolePerfil.getRolePerfil());
            usuario.addRole(role);

            usuario.setIntentos(0);
            usuario.setOnline(false);
            usuario.setLastVisitDate(null);
            usuario.setUsername(usuario.getUsername().toUpperCase());
            usuario.setRun(usuario.getUsername().toUpperCase());

            String bcryptPassword = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(bcryptPassword);
        }

        String mensajeFlash = (usuario.getId() != null) ? "Usuario editado con éxito!" : "Usuario creado con éxito!";

        logger.info(usuario);
        funcionarioService.saveUsuario(usuario);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/usuario/listar";
    }

    /* ######################################### METODOS SELECT LIST ######################################### */

    @ModelAttribute("listaSexo")
    public List<Sexo> sexos() {
        //
        List<Sexo> sexos = sexoDao.findAll();
        return sexos;
    }

    @ModelAttribute("listaNacionalidad")
    public List<Nacionalidad> nacionalidades() {
        //
        List<Nacionalidad> nacionalidades = nacionalidadDao.findAll();
        return nacionalidades;
    }

    @ModelAttribute("listaRolePerfil")
    public List<RolePerfil> rolesPerfiles() {
        //
        List<RolePerfil> rolesPerfiles = rolePerfilDao.findAll();
        return rolesPerfiles;
    }

    @ModelAttribute("listaServiciosSalud")
    public List<ServicioSalud> servicios() {
        //
        List<ServicioSalud> serviciosSalud = servicioDao.findAll();
        return serviciosSalud;
    }

    @ModelAttribute("listaComunas")
    public List<Comuna> comunas() {
        //
        List<Comuna> comunas = comunaDao.findAll();
        return comunas;
    }
}
