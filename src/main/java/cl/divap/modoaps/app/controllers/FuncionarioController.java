package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.models.dao.contrato.IContratoDao;
import cl.divap.modoaps.app.models.dao.nacionalidad.INacionalidadDao;
import cl.divap.modoaps.app.models.dao.sexo.ISexoDao;
import cl.divap.modoaps.app.models.dao.usuario.ICustomUsuarioDao;
import cl.divap.modoaps.app.models.entity.Contrato;
import cl.divap.modoaps.app.models.entity.Funcionario;
import cl.divap.modoaps.app.models.entity.Nacionalidad;
import cl.divap.modoaps.app.models.entity.Sexo;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import cl.divap.modoaps.app.util.paginator.PageRender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/funcionario")
@SessionAttributes("funcionario")
public class FuncionarioController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Autowired
    private ISexoDao sexoDao;

    @Autowired
    private INacionalidadDao nacionalidadDao;

    @Autowired
    private ICustomUsuarioDao customUsuarioDao;

    @Autowired
    private IContratoDao contratoDao;

    // @Secured({"ROLE_LA_GRANJA", "ROLE_COMUNA", "ROLE_SERVICIO", "ROLE_MINSAL"})
    @PreAuthorize("hasAnyRole('ROLE_LA_GRANJA', 'ROLE_COMUNA', 'ROLE_SERVICIO', 'ROLE_MINSAL')")
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Authentication authentication) {
        //
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userLoggedIn = auth.getName();
        logger.info("Usuario Autenticado: " + userLoggedIn);

        Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
        String roleString = String.valueOf(role);
        logger.info("Su Rol es: ".concat(roleString));

        Funcionario funcionario = null;

        Object servicioObject = customUsuarioDao.findServicioIdByUserName(userLoggedIn);
        Long servicioLong = Long.parseLong(servicioObject.toString());
        logger.info("Servicio de Salud del Usuario: " + servicioLong);

        Object comunaObject = customUsuarioDao.findComunaIdByUserName(userLoggedIn);
        Long comunaLong = Long.parseLong(comunaObject.toString());
        logger.info("Comuna del Usuario: " + comunaLong);

        if(roleString.equals("[ROLE_MINSAL]")) {
            // Funcionario funcionario = funcionarioService.findOne(id);
            funcionario = funcionarioService.fetchByIdWithContratos(id);
        } else if(roleString.equals("[ROLE_SERVICIO]")) {
            funcionario = funcionarioService.fetchByIdByIdServicioWithContratosRoleServicio(id, servicioLong);
        } else if(roleString.equals("[ROLE_COMUNA]")) {
            funcionario = funcionarioService.fetchByIdByIdComunaByIdServicioWithContratosRoleComuna(id, servicioLong, comunaLong);
        } else if(roleString.equals("[ROLE_LA_GRANJA]")) {
            funcionario = funcionarioService.fetchByIdWithContratosRoleLaGranja(id);
        }

        if(funcionario == null) {
            //
            flash.addFlashAttribute("error", "No existen registros de contratos para su perfil. Puede crear uno en boton 'Crear Contrato'");
            return "redirect:/funcionario/listar";
        }

        model.put("funcionario", funcionario);
        model.put("titulo", "Detalle del Funcionario: " + funcionario.getNombres() + " " + funcionario.getApellidoPaterno() + " " + funcionario.getApellidoMaterno());

        return "funcionario/contrato/ver";
    }

    @Secured({"ROLE_LA_GRANJA", "ROLE_COMUNA", "ROLE_SERVICIO", "ROLE_MINSAL"})
    @RequestMapping(value= "/listar", method= RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model, Authentication authentication, HttpServletRequest request, HttpSession session) {
        //
        if(!request.getParameterMap().containsKey("page")) {
            //
            if(session.getAttribute("params") != null) {
                session.setAttribute("params", null);
            }
        }

        if(authentication != null) {
            //
            logger.info("Hola Usuario autenticado, desde FuncionarioController, tu nombre de usuario es: ".concat(authentication.getName()));
        }

        /* Foma estatica */
        //
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null) {
            //
            logger.info("Hola Usuario autenticado, desde FuncionarioController (forma estática con SecurityContextHolder), tu nombre de usuario es: " + auth.getName());
        }

        if(hasRole("ROLE_MINSAL")) {
            logger.info("Hola ".concat(auth.getName()).concat(" SI tienes acceso!"));
        } else {
            logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
        }

        /* Utilizando SecurityContextHolderAwareRequestWrapper */

        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");

        if(securityContext.isUserInRole("ROLE_MINSAL")) {
            //
            logger.info("Forma utilizando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" SI tienes acceso"));
        } else {
            logger.info("Forma utilizando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
        }

        /* Utilizando HttpServletRequest (nativa) */

        if(request.isUserInRole("ROLE_MINSAL")) {
            //
            logger.info("Forma utilizando HttpServletRequest (nativa): Hola ".concat(auth.getName()).concat(" SI tienes acceso"));
        } else {
            logger.info("Forma utilizando HttpServletRequest (nativa): Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
        }

        Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
        String roleString = String.valueOf(role);
        logger.info("Su Rol es: ".concat(roleString));

        Integer numeroRegistros=7;
        Pageable pageRequest = PageRequest.of(page, numeroRegistros);

        if(session.getAttribute("params") != null) {
            model.addAttribute("titulo", "Encontrados: Listado de Funcionarios");
            Page<Funcionario> funcionarios = funcionarioService.findAllCriteriaApiFuncionarios(pageRequest, session);
            PageRender<Funcionario> pageRender = new PageRender<>("/funcionario/listar", funcionarios);
            model.addAttribute("page", pageRender);
            model.addAttribute("funcionarios", funcionarios);
        } else {
            model.addAttribute("titulo", "Listado de Funcionarios");
            Page<Funcionario> funcionarios = funcionarioService.findAll(pageRequest);
            PageRender<Funcionario> pageRender = new PageRender<>("/funcionario/listar", funcionarios);
            model.addAttribute("page", pageRender);
            model.addAttribute("funcionarios", funcionarios);
        }

        return "funcionario/listar";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model, HttpServletRequest request, HttpSession session) {

        Map<String, String> params = new HashMap<>();

        if (request.getParameterMap().containsKey("run") || request.getParameterMap().containsKey("nombres") || request.getParameterMap().containsKey("apellidoPaterno") || request.getParameterMap().containsKey("apellidoMaterno") ||
                request.getParameterMap().containsKey("sexo") || request.getParameterMap().containsKey("nacionalidad") || request.getParameterMap().containsKey("enabled")
        ) {
            //
            String run = request.getParameter("run");
            String nombres = request.getParameter("nombres");
            String apellidoPaterno = request.getParameter("apellidoPaterno");
            String apellidoMaterno = request.getParameter("apellidoMaterno");
            String sexo = request.getParameter("sexo");
            String nacionalidad = request.getParameter("nacionalidad");
            String enabled = request.getParameter("enabled");

            sexo = sexo.equals("") ? null : sexo;
            nacionalidad = nacionalidad.equals("") ? null : nacionalidad;

            params.put("run", run);
            params.put("nombres", nombres);
            params.put("apellidoPaterno", apellidoPaterno);
            params.put("apellidoMaterno", apellidoMaterno);
            params.put("sexo", sexo);
            params.put("nacionalidad", nacionalidad);
            params.put("enabled", enabled);

            session.setAttribute("params", params);
        }

        Integer numeroRegistros=7;
        Pageable pageRequest = PageRequest.of(page, numeroRegistros);
        Page<Funcionario> funcionarios = funcionarioService.findAllCriteriaApiFuncionarios(pageRequest, session);

        PageRender<Funcionario> pageRender = new PageRender<>("/funcionario/listar", funcionarios);

        model.addAttribute("titulo", "Encontrados: Listado de Funcionarios");
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("page", pageRender);

        return "funcionario/listar";
    }

    @GetMapping("/limpiar")
    public String limpiarBuscador(HttpServletRequest request, HttpSession session) {

        if(session.getAttribute("params") != null) {
            session.setAttribute("params", null);
        }

        return "redirect:/funcionario/listar";
    }

    @Secured({"ROLE_LA_GRANJA", "ROLE_COMUNA", "ROLE_SERVICIO", "ROLE_MINSAL"})
    @RequestMapping(value="/form")
    public String crear(Map<String, Object> model) {
        //
        Funcionario funcionario = new Funcionario();

        model.put("flag", true);
        model.put("crear", true);
        model.put("boton", "Crear Funcionario");
        model.put("funcionario", funcionario);
        model.put("titulo", "Formulario de Funcionario");
        
        return "funcionario/form";
    }

    // @Secured({"ROLE_MINSAL", "ROLE_COMUNA"})
    @PreAuthorize("hasAnyRole('ROLE_LA_GRANJA', 'ROLE_COMUNA', 'ROLE_SERVICIO', 'ROLE_MINSAL')")
    @GetMapping("/form/{id}")
    public String editar(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {
        //
        Funcionario funcionario = null;

        if(id > 0) {
            funcionario = funcionarioService.findOne(id);

            if(funcionario == null) {
                //
                flash.addFlashAttribute("error", "El Id del Funcionario no existe en la base de datos!");
                return "redirect:/funcionario/listar";
            }

        } else {
            flash.addFlashAttribute("error", "El Id del Funcionario no puede ser cero!");
            return "redirect:/funcionario/listar";
        }

        model.addAttribute("boton", "Editar Funcionario");
        model.addAttribute("funcionario", funcionario);
        model.addAttribute("titulo", "Editar Funcionario");
        return "funcionario/form";
    }

    @Secured({"ROLE_LA_GRANJA", "ROLE_COMUNA", "ROLE_SERVICIO", "ROLE_MINSAL"})
    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String guardar(@Valid @ModelAttribute("funcionario") Funcionario funcionario, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        //
        if(result.hasErrors()) {
            //
            model.addAttribute("titulo", "Formulario de Funcionario con errores");

            if(funcionario.getId() == null) {
                model.addAttribute("boton", "Crear Funcionario");

            } else if(funcionario.getId() != null && funcionario.getId() > 0) {
                model.addAttribute("boton", "Editar Funcionario");
            }

            return "funcionario/form";
        }

        if(funcionario.getId() != null && funcionario.getId() > 0) {
            //
            Funcionario funcionarioDB = funcionarioService.findOne(funcionario.getId());
            funcionario.setCreateAt(funcionarioDB.getCreateAt());

            if(funcionario.getEnabled() == false) {
                Long funcionarioId = funcionario.getId();
                funcionarioService.updateContratosEnabledFalse(funcionarioId);
            }
        }

        funcionario.setNombres(funcionario.getNombres().toUpperCase());
        funcionario.setApellidoPaterno(funcionario.getApellidoPaterno().toUpperCase());
        funcionario.setApellidoMaterno(funcionario.getApellidoMaterno().toUpperCase());
        funcionario.setRun(funcionario.getRun().toUpperCase());

        String mensajeFlash = (funcionario.getId() != null) ? "Funcionario editado con éxito!" : "Funcionario creado con éxito!";

        funcionarioService.save(funcionario);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/funcionario/listar";
    }

    @Secured({"ROLE_LA_GRANJA", "ROLE_COMUNA", "ROLE_SERVICIO", "ROLE_MINSAL"})
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes flash) {
        //
        if(id > 0) {
            //
            funcionarioService.delete(id);
            flash.addFlashAttribute("warning", "Funcionario eliminado con éxito!");
        }

        return "redirect:/funcionario/listar";
    }

    private boolean hasRole(String role) {
        //
        SecurityContext context = SecurityContextHolder.getContext();

        if(context == null) {
            return false;
        }

        Authentication auth = context.getAuthentication();

        if(auth == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role)); // El metodo contains(new SimpleGrantedAuthority(role) retorna un booleano, true o false si contiene o no el elemento en la coleccion


        /*
		for(GrantedAuthority authority : authorities) {
			//
			if(role.equals(authority.getAuthority())) {
				logger.info("Hola Usuario ".concat(auth.getName()).concat(" tu rol es: ".concat(authority.getAuthority())));
				return true;
			}
		}

		return false;
        */
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
}
