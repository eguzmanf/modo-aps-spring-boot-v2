package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.helpers.string.HelperString;
import cl.divap.modoaps.app.models.dao.comuna.IComunaDao;
import cl.divap.modoaps.app.models.dao.nacionalidad.INacionalidadDao;
import cl.divap.modoaps.app.models.dao.rolePerfil.IRolePerfilDao;
import cl.divap.modoaps.app.models.dao.servicioSalud.IServicioSaludDao;
import cl.divap.modoaps.app.models.dao.servicioSaludComuna.ICustomServicioComunaDao;
import cl.divap.modoaps.app.models.dao.sexo.ISexoDao;
import cl.divap.modoaps.app.models.entity.*;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import cl.divap.modoaps.app.util.paginator.PageRender;
import cl.divap.modoaps.app.validation.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Secured({"ROLE_MINSAL"})
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

    @Autowired
    private ICustomServicioComunaDao customServicioComunaDao;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //
        //  binder.setValidator(passwordValidator);
        /*
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
        */
    }

    @GetMapping("/listar")
    public String listar(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model, Authentication authentication, HttpServletRequest request, HttpSession session) {

        if(!request.getParameterMap().containsKey("page")) {
            //
            if(session.getAttribute("params") != null) {
                session.setAttribute("params", null);
            }
        }

        // List<Usuario> usuarios = funcionarioService.findAllUsuarios();
        // List<Usuario> usuarios = funcionarioService.findAllCriteriaApi();


        Integer numeroRegistros=7;
        Pageable pageRequest = PageRequest.of(page, numeroRegistros);

        if(session.getAttribute("params") != null) {
            model.addAttribute("titulo", "Encontrados: Listado de Usuarios");
            Page<Usuario> usuarios = funcionarioService.findAllCriteriaApi(pageRequest, session);
            PageRender<Usuario> pageRender = new PageRender<>("/usuario/listar", usuarios);
            model.addAttribute("page", pageRender);
            model.addAttribute("usuarios", usuarios);
        } else {
            model.addAttribute("titulo", "Listado de Usuarios");
            Page<Usuario> usuarios = funcionarioService.findAllUsuario(pageRequest);
            PageRender<Usuario> pageRender = new PageRender<>("/usuario/listar", usuarios);
            model.addAttribute("page", pageRender);
            model.addAttribute("usuarios", usuarios);
        }

        return "usuario/listar";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model, HttpServletRequest request, HttpSession session) {

        Map<String, String> params = new HashMap<>();

        if (request.getParameterMap().containsKey("run") || request.getParameterMap().containsKey("nombres") || request.getParameterMap().containsKey("apellidoPaterno") || request.getParameterMap().containsKey("apellidoMaterno") ||
            request.getParameterMap().containsKey("sexo") || request.getParameterMap().containsKey("nacionalidad") || request.getParameterMap().containsKey("servicioSalud") || request.getParameterMap().containsKey("comuna") ||
            request.getParameterMap().containsKey("rolePerfil") || request.getParameterMap().containsKey("enabled")
        ) {
            //
            String run = request.getParameter("run");
            String nombres = request.getParameter("nombres");
            String apellidoPaterno = request.getParameter("apellidoPaterno");
            String apellidoMaterno = request.getParameter("apellidoMaterno");
            String sexo = request.getParameter("sexo");
            String nacionalidad = request.getParameter("nacionalidad");
            String servicioSalud = request.getParameter("servicioSalud");
            String comuna = request.getParameter("comuna");
            String rolePerfil = request.getParameter("rolePerfil");
            String enabled = request.getParameter("enabled");

            if(sexo.equals("")) {
                sexo = null;
            }

            nacionalidad = nacionalidad.equals("") ? null : nacionalidad;
            servicioSalud = servicioSalud.equals("") ? null : servicioSalud;
            comuna = comuna.equals("") ? null : comuna;
            rolePerfil = rolePerfil.equals("") ? null : rolePerfil;

            params.put("run", run);
            params.put("nombres", nombres);
            params.put("apellidoPaterno", apellidoPaterno);
            params.put("apellidoMaterno", apellidoMaterno);
            params.put("sexo", sexo);
            params.put("nacionalidad", nacionalidad);
            params.put("servicioSalud", servicioSalud);
            params.put("comuna", comuna);
            params.put("rolePerfil", rolePerfil);
            params.put("enabled", enabled);

            logger.info("Run Parameter from Controller: " + run);
            logger.info("Nombres Parameter from Controller: " + nombres);
            logger.info("Apellido Paterno Parameter from Controller: " + apellidoPaterno);
            logger.info("Apellido Materno Parameter from Controller: " + apellidoMaterno);
            logger.info("Sexo Parameter from Controller: " + sexo);
            logger.info("Nacionalidad Parameter from Controller: " + nacionalidad);
            logger.info("Servicio de Salud Parameter from Controller: " + servicioSalud);
            logger.info("Comuna Parameter from Controller: " + comuna);
            logger.info("Role Perfil Parameter from Controller: " + rolePerfil);
            logger.info("Habilitado Parameter from Controller: " + enabled);

            logger.info("Map params from Controller: " + params);
            
            session.setAttribute("params", params);
            logger.info("Session params from Controller: " + session.getAttribute("params"));
        }

        Integer numeroRegistros=7;
        Pageable pageRequest = PageRequest.of(page, numeroRegistros);
        Page<Usuario> usuarios = funcionarioService.findAllCriteriaApi(pageRequest, session);
        PageRender<Usuario> pageRender = new PageRender<>("/usuario/listar", usuarios);
        model.addAttribute("page", pageRender);

        model.addAttribute("titulo", "Encontrados: Listado de Usuarios");
        model.addAttribute("usuarios", usuarios);

        return "usuario/listar";
    }

    @GetMapping("/limpiar")
    public String limpiarBuscador(HttpServletRequest request, HttpSession session) {

        if(session.getAttribute("params") != null) {
            session.setAttribute("params", null);
        }

        logger.info("Limpiando Session params en vista listar");
        return "redirect:/usuario/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        //
        Usuario usuario = new Usuario();

        model.addAttribute("usuario", usuario);
        model.addAttribute("crear", true);
        model.addAttribute("disabledServicioComuna", false);
        model.addAttribute("hasErrorsScript", false);
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

        logger.info("Usuario está habilitado?: " + usuario.getEnabled());

        model.addAttribute("usuario", usuario);
        model.addAttribute("crear", false);
        model.addAttribute("titulo", "Editar Usuario");
        model.addAttribute("boton", "Editar Usuario");
        model.addAttribute("hasErrorsScript", false);

        return "usuario/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        //
        passwordValidator.validate(usuario, result);
        existeUsuario.validate(usuario, result);
        emailValidator.validate(usuario, result);
        usuarioServicio.validate(usuario, result);
        usuarioComunaValidator.validate(usuario, result);
        confirmPasswordNotBlankValidator.validate(usuario, result);
        confirmPasswordRegexValidator.validate(usuario, result);
        confirmPasswordSizeValidator.validate(usuario, result);
        passwordNotBlankValidator.validate(usuario, result);
        passwordSizeValidator.validate(usuario, result);

        if(result.hasErrors()) {
            //
            model.addAttribute("titulo", "Formulario Crear Usuario con errores");

            if(usuario.getId() == null) {
                model.addAttribute("boton", "Crear Usuario");
                model.addAttribute("crear", true);
                model.addAttribute("disabledServicioComuna", true);
                model.addAttribute("hasErrorsScript", true);

            } else if(usuario.getId() != null && usuario.getId() > 0) {
                model.addAttribute("boton", "Editar Usuario");
                model.addAttribute("crear", false);
                model.addAttribute("hasErrorsScript", true);
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
        c.add(Calendar.YEAR, 4000);   // años
        Date futureDate = c.getTime();

        if(usuario.getEnabled()) {
            usuario.setLockoutEnd(null);
            usuario.setIntentos(0);
        } else {
            usuario.setLockoutEnd(futureDate);
            usuario.setIntentos(3);
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

        String mensajeFlash = (usuario.getId() != null) ? "Usuario " + usuario.getUsername() +" editado con éxito!" : "Usuario creado con éxito!";

        logger.info(usuario);
        funcionarioService.saveUsuario(usuario);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/usuario/listar";
    }

    @Secured({"ROLE_MINSAL"})
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes flash) {
        //
        if(id > 0) {
            //
            funcionarioService.deleteUsuario(id);
            flash.addFlashAttribute("warning", "Usuario eliminado con éxito!");
        }

        return "redirect:/usuario/listar";
    }

    /* ######################################### MODAL CHANGE PASSWORD ######################################### */

    @GetMapping("cambiar-password")
    public String cambiarPasswordModal(Model model) {
        //
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "usuario/modal-password-change";
    }

    @PostMapping("cambiar-password")
    public String guardarCambiarPassword(@ModelAttribute("usuario") Usuario usuario, Model model, RedirectAttributes flash, SessionStatus status) {
        //
        // Usuario usuarioBd = funcionarioService.findUsuarioById(usuario.getId());
        String bcryptPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(bcryptPassword);

        logger.info("Hola Mundo Modals");
        logger.info("Id Usuario: " + usuario.getId());
        logger.info("Password: " + usuario.getPassword());
        logger.info("Confirmar Password: " + usuario.getConfirmPassword());

        logger.info(usuario);
        funcionarioService.updatePasswordByIdUsuario(usuario);
        status.setComplete();
        flash.addFlashAttribute("success", "Se ha cambiado la contraseña del Usuario " + usuario.getNombre() + " exitosamente!");

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

    /* ######################################### METODOS API FETCH ######################################### */

    @PostMapping(value = "/cargar-servicios", produces = {"application/json"})
    public @ResponseBody List<ServicioSalud> cargarServicios(@RequestBody ObjectNode nodo) {
        return servicioDao.findAll();
    }

    @PostMapping(value = "/cargar-comunas", produces = {"application/json"})
    public @ResponseBody List<Comuna> cargarComuna(@RequestBody ObjectNode idServicioSalud) {

        String idRoleString = idServicioSalud.get("idRole").asText();
        logger.info("El string idRole: " + idRoleString);

        String idString = idServicioSalud.get("idServicioSalud").asText();
        logger.info("El string idServicioSalud: " + idString);

        Long id = Long.parseLong(idString);
        logger.info("El Long idServicioSalud: " + id);

        return comunaDao.findComunaByIdServicioSalud(id);
    }
    
    @PostMapping(value = "/cargar-comuna-del-servicio", produces = {"application/json"})
    public @ResponseBody ServicioComuna cargarComunaDelServicio(@RequestBody ObjectNode objectNode) {

        String idRoleString = objectNode.get("idRole").asText();
        logger.info("El string idRole: " + idRoleString);

        String idStringServicio = objectNode.get("idServicioSalud").asText();
        logger.info("El string idServicioSalud: " + idStringServicio);

        Long id = Long.parseLong(idStringServicio);
        logger.info("El Long idServicioSalud: " + id);

        return customServicioComunaDao.findServicioComunaByServicioId(id);
    }

    @PostMapping(value = "/cargar-servicios-la-granja", produces = {"application/json"})
    public @ResponseBody List<ServicioSalud> cargarServiciosLaGranja(@RequestBody ObjectNode objectNode) {

        String idRoleString = objectNode.get("idRole").asText();
        logger.info("El string idRole: " + idRoleString);

        String idString = objectNode.get("idRoleLaGranja").asText();
        logger.info("El string idRoleLaGranja: " + idString);

        Long id = Long.parseLong(idString);
        logger.info("El Long idRoleLaGranja: " + id);

        return servicioDao.findServiciosLaGranja();
    }


    @PostMapping(value = "/cargar-comunas-la-granja", produces = {"application/json"})
    public @ResponseBody List<Comuna> cargarComunasLaGranja(@RequestBody ObjectNode idServicioSalud) {

        String idRoleString = idServicioSalud.get("idRole").asText();
        logger.info("El string idRole: " + idRoleString);

        String idString = idServicioSalud.get("idServicioSalud").asText();
        logger.info("El string idServicioSalud: " + idString);

        Long id = Long.parseLong(idString);
        logger.info("El Long idServicioSalud: " + id);

        return comunaDao.findComunaByIdServicioSalud(id);
    }

    /* ######################################### EXPORTAR EXCEL ######################################### */

    @GetMapping("/export-excel")
    public void exportExcel(@RequestParam(name = "page", defaultValue = "0") Integer page, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = "usuarios_" + currentDateTime + ".xlsx";

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachement; filename=" + fileName);

        logger.info("Session Get Attribute from Export : " + session.getAttribute("params"));
        if(session.getAttribute("params") == null) {

            Map<String, String> params = new HashMap<>();

            String run = "";
            String nombres = "";
            String apellidoPaterno = "";
            String apellidoMaterno = "";
            String sexo = null;
            String nacionalidad = null;
            String servicioSalud = null;
            String comuna = null;
            String rolePerfil = null;
            String enabled = "";

            params.put("run", run);
            params.put("nombres", nombres);
            params.put("apellidoPaterno", apellidoPaterno);
            params.put("apellidoMaterno", apellidoMaterno);
            params.put("sexo", sexo);
            params.put("nacionalidad", nacionalidad);
            params.put("servicioSalud", servicioSalud);
            params.put("comuna", comuna);
            params.put("rolePerfil", rolePerfil);
            params.put("enabled", enabled);

            session.setAttribute("params", params);
        }

        int countUsuarios = funcionarioService.findAllUsuarios().size();
        Pageable pageRequest = PageRequest.of(page, countUsuarios);
        Page<Usuario> usuarios = funcionarioService.findAllCriteriaApi(pageRequest, session);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Usuarios");
        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:O1"));

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        Cell cell = row.createCell(0);
        cell.setCellValue("N°    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(0);

        cell = row.createCell(1);
        cell.setCellValue("Id    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(1);

        cell = row.createCell(2);
        cell.setCellValue("Nombre    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(2);

        cell = row.createCell(3);
        cell.setCellValue("Apellido Paterno    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        cell = row.createCell(4);
        cell.setCellValue("Apellido Materno    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(4);

        cell = row.createCell(5);
        cell.setCellValue("Run    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(5);

        cell = row.createCell(6);
        cell.setCellValue("Fecha Nacimiento    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(6);

        cell = row.createCell(7);
        cell.setCellValue("Sexo    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(7);

        cell = row.createCell(8);
        cell.setCellValue("Nacionalidad    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(8);

        cell = row.createCell(9);
        cell.setCellValue("Correo    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(9);

        cell = row.createCell(10);
        cell.setCellValue("Teléfono    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(10);

        cell = row.createCell(11);
        cell.setCellValue("Servicio de Salud    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(11);

        cell = row.createCell(12);
        cell.setCellValue("Comuna    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(12);

        cell = row.createCell(13);
        cell.setCellValue("Perfil    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(13);

        cell = row.createCell(14);
        cell.setCellValue("Estado    ");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(14);

        int rowCount = 1;

        CellStyle styleData = workbook.createCellStyle();
        XSSFFont fontData = workbook.createFont();
        fontData.setFontHeight(12);
        fontData.setColor(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex());
        styleData.setFont(fontData);
        styleData.setBorderTop(BorderStyle.THIN);
        styleData.setBorderBottom(BorderStyle.THIN);
        styleData.setBorderLeft(BorderStyle.THIN);
        styleData.setBorderRight(BorderStyle.THIN);

        for(Usuario usuario : usuarios) {

            Row rowData = sheet.createRow(rowCount);

            Cell cellData = rowData.createCell(0);
            cellData.setCellValue(rowCount);
            sheet.autoSizeColumn(0);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(1);
            cellData.setCellValue(usuario.getId());
            sheet.autoSizeColumn(1);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(2);
            cellData.setCellValue(usuario.getNombre());
            sheet.autoSizeColumn(2);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(3);
            cellData.setCellValue(usuario.getApellidoPaterno());
            sheet.autoSizeColumn(3);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(4);
            cellData.setCellValue(usuario.getApellidoMaterno());
            sheet.autoSizeColumn(4);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(5);
            cellData.setCellValue(usuario.getRun());
            sheet.autoSizeColumn(5);
            cellData.setCellStyle(styleData);

            DateFormat dateFormatFechaNacimiento = new SimpleDateFormat("dd/MM/yyyy");
            String fechaNacimiento = dateFormatFechaNacimiento.format(usuario.getFechaNacimiento());
            cellData = rowData.createCell(6);
            cellData.setCellValue(fechaNacimiento);
            sheet.autoSizeColumn(6);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(7);
            cellData.setCellValue(usuario.getSexo().getSexo());
            sheet.autoSizeColumn(7);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(8);
            cellData.setCellValue(usuario.getNacionalidad().getNacionalidad());
            sheet.autoSizeColumn(8);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(9);
            cellData.setCellValue(usuario.getEmail());
            sheet.autoSizeColumn(9);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(10);
            cellData.setCellValue(usuario.getTelefono());
            sheet.autoSizeColumn(10);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(11);
            cellData.setCellValue(usuario.getServicioSalud().getServicioSalud());
            sheet.autoSizeColumn(11);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(12);
            cellData.setCellValue(usuario.getComuna().getComuna());
            sheet.autoSizeColumn(12);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(13);
            cellData.setCellValue(usuario.getRolePerfil().getRolePerfil());
            sheet.autoSizeColumn(13);
            cellData.setCellStyle(styleData);

            String estado = usuario.getEnabled() ? "Habilitado" : "Deshabilitado";
            cellData = rowData.createCell(14);
            cellData.setCellValue(estado);
            sheet.autoSizeColumn(14);
            cellData.setCellStyle(styleData);

            rowCount++;
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
