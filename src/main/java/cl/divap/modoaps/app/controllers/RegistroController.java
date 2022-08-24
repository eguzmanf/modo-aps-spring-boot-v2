package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.models.dao.comuna.IComunaDao;
import cl.divap.modoaps.app.models.dao.establecimiento.IEstablecimientoDao;
import cl.divap.modoaps.app.models.dao.nacionalidad.INacionalidadDao;
import cl.divap.modoaps.app.models.dao.servicioSalud.IServicioSaludDao;
import cl.divap.modoaps.app.models.dao.sexo.ISexoDao;
import cl.divap.modoaps.app.models.dao.usuario.ICustomUsuarioDao;
import cl.divap.modoaps.app.models.entity.*;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Autowired
    private ICustomUsuarioDao customUsuarioDao;

    @Autowired
    private ISexoDao sexoDao;

    @Autowired
    private INacionalidadDao nacionalidadDao;

    @Autowired
    private IServicioSaludDao servicioDao;

    @Autowired
    private IComunaDao comunaDao;

    @Autowired
    private IEstablecimientoDao establecimientoDao;

    @GetMapping("/listar")
    public String listar(Model model, Authentication authentication) {
        //
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userLoggedIn = auth.getName();
        logger.info("Usuario Autenticado: " + userLoggedIn);

        Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
        String roleString = String.valueOf(role);
        logger.info("Su Rol es: ".concat(roleString));

        Object servicioObject = customUsuarioDao.findServicioIdByUserName(userLoggedIn);
        Long servicioLong = Long.parseLong(servicioObject.toString());
        logger.info("Servicio de Salud del Usuario: " + servicioLong);

        Object comunaObject = customUsuarioDao.findComunaIdByUserName(userLoggedIn);
        Long comunaLong = Long.parseLong(comunaObject.toString());
        logger.info("Comuna del Usuario: " + comunaLong);

        List<Contrato> contratos = null;

        if(roleString.equals("[ROLE_MINSAL]")) {
            contratos = funcionarioService.findAllContratosNotDisabled();
            model.addAttribute("titulo", "Actualizar contratos Rol MINSAL");
        } else if(roleString.equals("[ROLE_SERVICIO]")) {
            contratos = funcionarioService.findContratosNotDisabledRoleServicio(servicioLong);
            model.addAttribute("titulo", "Actualizar contratos Rol Servicio de Salud");
        } else if(roleString.equals("[ROLE_COMUNA]")) {
            contratos = funcionarioService.findContratosNotDisabledRoleComuna(servicioLong, comunaLong);
            model.addAttribute("titulo", "Actualizar contratos Rol Comuna");
        } else if(roleString.equals("[ROLE_LA_GRANJA]")) {
            contratos = funcionarioService.findContratosNotDisabledRoleLaGranja();
            model.addAttribute("titulo", "Actualizar contratos Rol La Granja");
        }

        // model.addAttribute("titulo", "Actualiza contratos");
        model.addAttribute("contratos", contratos);
        model.addAttribute("roleUser", role);
        model.addAttribute("idServicioUsuario", servicioLong);
        model.addAttribute("idComunaUsuario", comunaLong);

        return "registro/listar";
    }

    @PostMapping("/acciones")
    public String accionesRegistros(@RequestParam(defaultValue = "0") String checkAllTextArray, Model model, HttpServletRequest request, RedirectAttributes flash, Authentication authentication) {

        String btnRevisarRegistros = request.getParameter("btnRevisarRegistros");
        String btnEliminarRegistros = request.getParameter("btnEliminarRegistros");

        logger.info("Acción Revisar Registros: " + btnRevisarRegistros);
        logger.info("Acción Eliminar Registros: " + btnEliminarRegistros);

        String usuarioRun = authentication.getName();
        Date fechaActual = new Date();

        if(request.getParameterMap().containsKey("btnRevisarRegistros")) {

            logger.info("CheckBoxes Text Array: " + checkAllTextArray);

            String[] checkAllArraySplit = checkAllTextArray.split(",");
            List<String> checkAllArray = Arrays.asList(checkAllArraySplit);
            ArrayList<String> checkAllArrayFinal = new ArrayList<String>(checkAllArray);

            logger.info("CheckBoxes Array: " + checkAllArray);
            logger.info("CheckBoxes Array Final: " + checkAllArrayFinal);
            logger.info("Size of CheckBoxes Array Final: " + checkAllArrayFinal.size());

            for(String idContratoString : checkAllArrayFinal) {
                Long idContratoLong = Long.parseLong(idContratoString);
                System.out.println(idContratoLong);
                funcionarioService.updateTrueRevisadoByIdContrato(idContratoLong, usuarioRun, fechaActual);
            }

            if (!checkAllTextArray.equals("0")) {
                flash.addFlashAttribute("success", "Registro revisado con éxito!");
            }
        }

        if(request.getParameterMap().containsKey("btnEliminarRegistros")) {

            logger.info("CheckBoxes Text Array: " + checkAllTextArray);

            String[] checkAllArraySplit = checkAllTextArray.split(",");
            List<String> checkAllArray = Arrays.asList(checkAllArraySplit);
            ArrayList<String> checkAllArrayFinal = new ArrayList<String>(checkAllArray);

            logger.info("CheckBoxes Array: " + checkAllArray);
            logger.info("CheckBoxes Array Final: " + checkAllArrayFinal);
            logger.info("Size of CheckBoxes Array Final: " + checkAllArrayFinal.size());

            for(String idContratoString : checkAllArrayFinal) {
                Long idContratoLong = Long.parseLong(idContratoString);
                System.out.println(idContratoLong);
                funcionarioService.updateFalseEnabledByIdContrato(idContratoLong, usuarioRun, fechaActual);
            }

            if (!checkAllTextArray.equals("0")) {
                flash.addFlashAttribute("error", "Registro eliminado con éxito!");
            }
        }

        if(request.getParameterMap().containsKey("btnValidarRegistros")) {

            logger.info("CheckBoxes Text Array: " + checkAllTextArray);

            String[] checkAllArraySplit = checkAllTextArray.split(",");
            List<String> checkAllArray = Arrays.asList(checkAllArraySplit);
            ArrayList<String> checkAllArrayFinal = new ArrayList<String>(checkAllArray);

            logger.info("CheckBoxes Array: " + checkAllArray);
            logger.info("CheckBoxes Array Final: " + checkAllArrayFinal);
            logger.info("Size of CheckBoxes Array Final: " + checkAllArrayFinal.size());

            for(String idContratoString : checkAllArrayFinal) {
                Long idContratoLong = Long.parseLong(idContratoString);
                System.out.println(idContratoLong);
                funcionarioService.updateTrueValidadoByIdContrato(idContratoLong, usuarioRun, fechaActual);
            }

            if (!checkAllTextArray.equals("0")) {
                flash.addFlashAttribute("info", "Registro validado con éxito!");
            }
        }

        return "redirect:/registro/listar";
    }

    @PostMapping("/buscar")
    public String buscar(Model model, HttpServletRequest request, HttpSession session, Authentication authentication) {
        //
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userLoggedIn = auth.getName();
        logger.info("Usuario Autenticado: " + userLoggedIn);

        Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
        String roleString = String.valueOf(role);
        logger.info("Su Rol es: ".concat(roleString));

        Object servicioObject = customUsuarioDao.findServicioIdByUserName(userLoggedIn);
        Long servicioLong = Long.parseLong(servicioObject.toString());
        logger.info("Servicio de Salud del Usuario: " + servicioLong);

        Object comunaObject = customUsuarioDao.findComunaIdByUserName(userLoggedIn);
        Long comunaLong = Long.parseLong(comunaObject.toString());
        logger.info("Comuna del Usuario: " + comunaLong);

        Map<String, String> params = new HashMap<>();
        params.put("userLoggedIn", userLoggedIn);
        params.put("roleString", roleString);
        params.put("servicioString", servicioLong.toString());
        params.put("comunaString", comunaLong.toString());

        if(request.getParameterMap().containsKey("run") || request.getParameterMap().containsKey("nombres") || request.getParameterMap().containsKey("apellidoPaterno") || request.getParameterMap().containsKey("apellidoMaterno") ||
                request.getParameterMap().containsKey("sexo") || request.getParameterMap().containsKey("nacionalidad") || request.getParameterMap().containsKey("servicioSalud") || request.getParameterMap().containsKey("comuna") ||
                request.getParameterMap().containsKey("establecimiento")) {

            String run = request.getParameter("run");
            String nombres = request.getParameter("nombres");
            String apellidoPaterno = request.getParameter("apellidoPaterno");
            String apellidoMaterno = request.getParameter("apellidoMaterno");
            String sexo = request.getParameter("sexo");
            String nacionalidad = request.getParameter("nacionalidad");
            String servicioSalud = request.getParameter("servicioSalud");
            String comuna = request.getParameter("comuna");
            String establecimiento = request.getParameter("establecimiento");

            if(sexo.equals("")) {
                sexo = null;
            }
            nacionalidad = nacionalidad.equals("") ? null : nacionalidad;
            servicioSalud = servicioSalud.equals("") ? null : servicioSalud;
            comuna = comuna.equals("") ? null : comuna;
            establecimiento = establecimiento.equals("") ? null : establecimiento;

            params.put("run", run);
            params.put("nombres", nombres);
            params.put("apellidoPaterno", apellidoPaterno);
            params.put("apellidoMaterno", apellidoMaterno);
            params.put("sexo", sexo);
            params.put("nacionalidad", nacionalidad);
            params.put("servicioSalud", servicioSalud);
            params.put("comuna", comuna);
            params.put("establecimiento", establecimiento);

            logger.info("Run Parameter from Controller: " + run);
            session.setAttribute("params", params);
            logger.info("Session params from Controller: " + session.getAttribute("params"));
        }

        List<Contrato> contratos = null;

        // contratos = funcionarioService.findAllContratosNotDisabled();
        contratos = funcionarioService.searchContratosCriteriaApi(session);

        if(roleString.equals("[ROLE_MINSAL]")) {
            model.addAttribute("titulo", "Actualizar contratos Rol MINSAL");
        } else if(roleString.equals("[ROLE_SERVICIO]")) {
            model.addAttribute("titulo", "Actualizar contratos Rol Servicio de Salud");
        } else if(roleString.equals("[ROLE_COMUNA]")) {
            model.addAttribute("titulo", "Actualizar contratos Rol Comuna");
        } else if(roleString.equals("[ROLE_LA_GRANJA]")) {
            model.addAttribute("titulo", "Actualizar contratos Rol La Granja");
        }
        model.addAttribute("contratos", contratos);
        model.addAttribute("roleUser", role);
        model.addAttribute("idServicioUsuario", servicioLong);
        model.addAttribute("idComunaUsuario", comunaLong);

        return "registro/listar";
    }

    @GetMapping("/limpiar")
    public String limpiarBuscador(HttpServletRequest request, HttpSession session) {

        if(session.getAttribute("params") != null) {
            session.setAttribute("params", null);
        }

        logger.info("Limpiando Session params en vista listar");
        return "redirect:/registro/listar";
    }

    @GetMapping("/export-excel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request, HttpSession session, Authentication authentication) throws IOException {
        //
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userLoggedIn = auth.getName();
        logger.info("Usuario Autenticado: " + userLoggedIn);

        Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
        String roleString = String.valueOf(role);
        logger.info("Su Rol es: ".concat(roleString));

        Object servicioObject = customUsuarioDao.findServicioIdByUserName(userLoggedIn);
        Long servicioLong = Long.parseLong(servicioObject.toString());
        logger.info("Servicio de Salud del Usuario: " + servicioLong);

        Object comunaObject = customUsuarioDao.findComunaIdByUserName(userLoggedIn);
        Long comunaLong = Long.parseLong(comunaObject.toString());
        logger.info("Comuna del Usuario: " + comunaLong);

        Map paramsSession = (Map) session.getAttribute("params");

        Map<String, String> params = new HashMap<>();

        if(session.getAttribute("params") == null) {
            params.put("run", "");
            params.put("nombres", "");
            params.put("apellidoPaterno", "");
            params.put("apellidoMaterno", "");
            params.put("sexo", null);
            params.put("nacionalidad", null);
            params.put("servicioSalud", null);
            params.put("comuna", null);
            params.put("establecimiento", null);
        } else {
            params.put("run", (String) paramsSession.get("run"));
            params.put("nombres", (String) paramsSession.get("nombres"));
            params.put("apellidoPaterno", (String) paramsSession.get("apellidoPaterno"));
            params.put("apellidoMaterno", (String) paramsSession.get("apellidoMaterno"));
            params.put("sexo", (String) paramsSession.get("sexo"));
            params.put("nacionalidad", (String) paramsSession.get("nacionalidad"));
            params.put("servicioSalud", (String) paramsSession.get("servicioSalud"));
            params.put("comuna", (String) paramsSession.get("comuna"));
            params.put("establecimiento", (String) paramsSession.get("establecimiento"));
        }

        params.put("userLoggedIn", userLoggedIn);
        params.put("roleString", roleString);
        params.put("servicioString", servicioLong.toString());
        params.put("comunaString", comunaLong.toString());

        session.setAttribute("params", params);

        List<Contrato> contratos = funcionarioService.searchContratosCriteriaApi(session);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = "contratos_" + currentDateTime + ".xlsx";

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachement; filename=" + fileName);

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        workbook.setCompressTempFiles(true);

        SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
        sheet.setRandomAccessWindowSize(100);                   // keep 100 rows in memory, exceeding rows will be flushed to disk

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        Cell cell = row.createCell(0);
        cell.setCellValue("N°    ");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Servicio de Salud    ");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Comuna    ");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Establecimiento    ");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Administración    ");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Run    ");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("DV    ");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("Apellido Paterno    ");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("Apellido Materno    ");
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue("Nombre    ");
        cell.setCellStyle(style);

        cell = row.createCell(10);
        cell.setCellValue("Sexo    ");
        cell.setCellStyle(style);

        cell = row.createCell(11);
        cell.setCellValue("Fecha Nacimiento    ");
        cell.setCellStyle(style);

        cell = row.createCell(12);
        cell.setCellValue("Nacionalidad    ");
        cell.setCellStyle(style);

        cell = row.createCell(13);
        cell.setCellValue("Ley Contratación    ");
        cell.setCellStyle(style);

        cell = row.createCell(14);
        cell.setCellValue("Tipo de contrato    ");
        cell.setCellStyle(style);

        cell = row.createCell(15);
        cell.setCellValue("Categoría    ");
        cell.setCellStyle(style);

        cell = row.createCell(16);
        cell.setCellValue("Nivel Carrera    ");
        cell.setCellStyle(style);

        cell = row.createCell(17);
        cell.setCellValue("Profesión    ");
        cell.setCellStyle(style);

        cell = row.createCell(18);
        cell.setCellValue("Especialidad    ");
        cell.setCellStyle(style);

        cell = row.createCell(19);
        cell.setCellValue("Cargo    ");
        cell.setCellStyle(style);

        cell = row.createCell(20);
        cell.setCellValue("Asig. Chofer    ");
        cell.setCellStyle(style);

        cell = row.createCell(21);
        cell.setCellValue("Jornada    ");
        cell.setCellStyle(style);

        cell = row.createCell(22);
        cell.setCellValue("Años de Serv.    ");
        cell.setCellStyle(style);

        cell = row.createCell(23);
        cell.setCellValue("Fecha Ingreso    ");
        cell.setCellStyle(style);

        cell = row.createCell(24);
        cell.setCellValue("Bienios    ");
        cell.setCellStyle(style);

        cell = row.createCell(25);
        cell.setCellValue("Previsión    ");
        cell.setCellStyle(style);

        cell = row.createCell(26);
        cell.setCellValue("Isapre    ");
        cell.setCellStyle(style);

        cell = row.createCell(27);
        cell.setCellValue("Sueldo Base comunal    ");
        cell.setCellStyle(style);

        cell = row.createCell(28);
        cell.setCellValue("Total Haberes    ");
        cell.setCellStyle(style);

        cell = row.createCell(29);
        cell.setCellValue("Validado    ");
        cell.setCellStyle(style);

        cell = row.createCell(30);
        cell.setCellValue("Revisado    ");
        cell.setCellStyle(style);

        int rowCount = 1;

        CellStyle styleData = workbook.createCellStyle();
        styleData.setBorderTop(BorderStyle.THIN);
        styleData.setBorderBottom(BorderStyle.THIN);
        styleData.setBorderLeft(BorderStyle.THIN);
        styleData.setBorderRight(BorderStyle.THIN);

        for(Contrato contrato : contratos) {
            //
            Row rowData = sheet.createRow(rowCount);

            Cell cellData = rowData.createCell(0);
            cellData.setCellValue(rowCount);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(1);
            cellData.setCellValue(contrato.getServicioSalud().getServicioSalud());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(2);
            cellData.setCellValue(contrato.getComuna().getComuna());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(3);
            cellData.setCellValue(contrato.getEstablecimiento().getEstablecimientoNombre());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(4);
            cellData.setCellValue(contrato.getAdminSalud().getAdminSalud());
            cellData.setCellStyle(styleData);

            String runCompleto = contrato.getFuncionario().getRun();
            String[] parts = runCompleto.split("-");
            String runSinDv = parts[0];
            String dv = parts[1];

            cellData = rowData.createCell(5);
            cellData.setCellValue(runSinDv);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(6);
            cellData.setCellValue(dv);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(7);
            cellData.setCellValue(contrato.getFuncionario().getApellidoPaterno());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(8);
            cellData.setCellValue(contrato.getFuncionario().getApellidoMaterno());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(9);
            cellData.setCellValue(contrato.getFuncionario().getNombres());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(10);
            cellData.setCellValue(contrato.getFuncionario().getSexo().getSexo());
            cellData.setCellStyle(styleData);

            DateFormat dateFormatFechaNacimiento = new SimpleDateFormat("yyyy-MM-dd");
            String fechaNacimiento = dateFormatFechaNacimiento.format(contrato.getFuncionario().getFechaNacimiento());
            cellData = rowData.createCell(11);
            cellData.setCellValue(fechaNacimiento);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(12);
            cellData.setCellValue(contrato.getFuncionario().getNacionalidad().getNacionalidad());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(13);
            cellData.setCellValue(contrato.getLey().getLey());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(14);
            cellData.setCellValue(contrato.getTipoContrato().getTipoContrato());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(15);
            cellData.setCellValue(contrato.getCategoriaProfesion().getCategoriaProfesion());
            cellData.setCellStyle(styleData);

            String nivelCarrera = contrato.getNivelCarrera().getNivelCarrera().equals("0") ? "N/A" : contrato.getNivelCarrera().getNivelCarrera();
            cellData = rowData.createCell(16);
            cellData.setCellValue(nivelCarrera);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(17);
            cellData.setCellValue(contrato.getProfesion().getProfesion());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(18);
            cellData.setCellValue(contrato.getEspecialidad().getEspecialidad());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(19);
            cellData.setCellValue(contrato.getCargo().getCargo());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(20);
            cellData.setCellValue(contrato.getAsignacionChofer().getAsignacionChofer());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(21);
            cellData.setCellValue(contrato.getJornadaLaboral());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(22);
            cellData.setCellValue(contrato.getAniosServicio().toString());
            cellData.setCellStyle(styleData);

            DateFormat dateFormatFechaIngreso = new SimpleDateFormat("yyyy-MM-dd");
            String fechaIngreso = dateFormatFechaIngreso.format(contrato.getFechaIngreso());
            cellData = rowData.createCell(23);
            cellData.setCellValue(fechaIngreso);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(24);
            cellData.setCellValue(contrato.getBienios().getBienios());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(25);
            cellData.setCellValue(contrato.getPrevision().getPrevision());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(26);
            cellData.setCellValue(contrato.getIsapre().getIsapre());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(27);
            cellData.setCellValue(contrato.getSueldoBase().toString());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(28);
            cellData.setCellValue(contrato.getTotalHaberes().toString());
            cellData.setCellStyle(styleData);

            String validado = contrato.getValidado() ? "Si" : "No";
            cellData = rowData.createCell(29);
            cellData.setCellValue(validado);
            cellData.setCellStyle(styleData);

            String revisado = contrato.getRevisado() ? "Si" : "No";
            cellData = rowData.createCell(30);
            cellData.setCellValue(revisado);
            cellData.setCellStyle(styleData);

            rowCount++;
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
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

    @PostMapping(value = "/cargar-comunas", produces = {"application/json"})
    public @ResponseBody List<Comuna> cargarComuna(@RequestBody ObjectNode idServicioSalud) {

        String idString = idServicioSalud.get("idServicioSalud").asText();
        Long id = Long.parseLong(idString);

        return comunaDao.findComunaByIdServicioSalud(id);
    }

    @PostMapping(value = "/cargar-establecimientos", produces = {"application/json"})
    public @ResponseBody List<Establecimiento> cargarEstablecimiento(@RequestBody ObjectNode node) {

        String idString = node.get("idComuna").asText();
        Long id = Long.parseLong(idString);

        return establecimientoDao.findEstablecimientoByIdComuna(id);
    }

    @PostMapping(value = "/cargar-servicio", produces = {"application/json"})
    public @ResponseBody ServicioSalud cargarServicio(@RequestBody ObjectNode node) {

        String idString = node.get("idServicio").asText();
        Long id = Long.parseLong(idString);

        logger.info(id);

        return servicioDao.findServicioByServicioId(id);
    }

    @PostMapping(value = "/cargar-comunas-servicio-role", produces = {"application/json"})
    public @ResponseBody List<Comuna> cargarComunaServicioRole(@RequestBody ObjectNode idServicioSalud) {

        String idString = idServicioSalud.get("idServicio").asText();
        Long id = Long.parseLong(idString);

        return comunaDao.findComunaByIdServicioSalud(id);
    }

    @PostMapping(value = "/cargar-comunas-role-comuna", produces = {"application/json"})
    public @ResponseBody Comuna cargarComunaByIdComunaRoleComuna(@RequestBody ObjectNode node) {

        String idString = node.get("idComuna").asText();
        Long id = Long.parseLong(idString);

        return comunaDao.findComunaByIdComunaRoleComuna(id);
    }

    @PostMapping(value = "/cargar-establecimientos-comuna-servicio-role", produces = {"application/json"})
    public @ResponseBody List<Establecimiento> cargarEstablecimientoServicioRole(@RequestBody ObjectNode node) {

        String idString = node.get("idComuna").asText();
        Long id = Long.parseLong(idString);

        return establecimientoDao.findEstablecimientoByIdComuna(id);
    }

    @PostMapping(value = "/cargar-servicio-la-granja", produces = {"application/json"})
    public @ResponseBody List<ServicioSalud> cargarServiciosLaGranja(@RequestBody ObjectNode node) {

        return servicioDao.findServiciosLaGranja();
    }
}
