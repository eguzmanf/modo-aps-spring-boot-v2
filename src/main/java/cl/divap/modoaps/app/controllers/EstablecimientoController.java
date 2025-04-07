package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.helpers.string.HelperString;
import cl.divap.modoaps.app.models.dao.comuna.IComunaDao;
import cl.divap.modoaps.app.models.dao.establecimiento.IEstablecimientoDao;
import cl.divap.modoaps.app.models.dao.establecimiento.IJpaRepositoryEstablecimientoDao;
import cl.divap.modoaps.app.models.dao.region.IRegionDao;
import cl.divap.modoaps.app.models.dao.servicioSalud.IServicioSaludDao;
import cl.divap.modoaps.app.models.entity.*;
import cl.divap.modoaps.app.util.paginator.PageRender;
import cl.divap.modoaps.app.validation.ExisteEstablecimientoValidator;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Secured({"ROLE_MINSAL"})
@Controller
@RequestMapping("/establecimiento")
@SessionAttributes("establecimiento")
public class   EstablecimientoController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IEstablecimientoDao establecimientoDao;

    @Autowired
    private IJpaRepositoryEstablecimientoDao establecimientoDaoJpaRepo;

    @Autowired
    private IRegionDao regionDao;

    @Autowired
    private IServicioSaludDao servicioDao;

    @Autowired
    private IComunaDao comunaDao;

    @Autowired
    private ExisteEstablecimientoValidator establecimientoValidator;

    @GetMapping("/listar")
    public String listar(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model, HttpServletRequest request, HttpSession session) {

        if(!request.getParameterMap().containsKey("page")) {
            if(session.getAttribute("params") != null) {
                session.setAttribute("params", null);
            }
        }

        Integer numeroRegistros=10;
        Pageable pageRequest = PageRequest.of(page, numeroRegistros);

        if(session.getAttribute("params") != null) {
            model.addAttribute("titulo", "Encontrados: Listado de Establecimientos");
            Page<Establecimiento> establecimientos = establecimientoDao.findAllEstablecimientoCriteriaApi(pageRequest, session);
            PageRender<Establecimiento> pageRender = new PageRender<>("/establecimiento/listar", establecimientos);
            model.addAttribute("page", pageRender);
            model.addAttribute("establecimientos", establecimientos);
        } else {
            model.addAttribute("titulo", "Listado de Establecimientos");
            Page<Establecimiento> establecimientos = establecimientoDao.findAllCriteriaApi(pageRequest);
            PageRender<Establecimiento> pageRender = new PageRender<>("/establecimiento/listar", establecimientos);
            model.addAttribute("page", pageRender);
            model.addAttribute("establecimientos", establecimientos);
        }

        return "establecimiento/listar";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model, HttpServletRequest request, HttpSession session) {
        Map<String, String> params = new HashMap<>();
        if (request.getParameterMap().containsKey("region") || request.getParameterMap().containsKey("servicio") || request.getParameterMap().containsKey("comuna") || request.getParameterMap().containsKey("codigoNuevo") ||
                request.getParameterMap().containsKey("establecimientoNombre") || request.getParameterMap().containsKey("tipoEstablecimiento") || request.getParameterMap().containsKey("enabled") ||
                request.getParameterMap().containsKey("tipoEstablecimientoText") || request.getParameterMap().containsKey("regionText") || request.getParameterMap().containsKey("servicioText") ||
                request.getParameterMap().containsKey("comunaText")
        ) {
            String region = request.getParameter("region");
            String servicio = request.getParameter("servicio");
            String comuna = request.getParameter("comuna");
            String codigoNuevo = request.getParameter("codigoNuevo");
            String establecimientoNombre = request.getParameter("establecimientoNombre");
            String tipoEstablecimiento = request.getParameter("tipoEstablecimiento");
            String tipoEstablecimientoText = request.getParameter("tipoEstablecimientoText");
            String enabled = request.getParameter("enabled");
            String regionText = request.getParameter("regionText");
            String servicioText = request.getParameter("servicioText");
            String comunaText = request.getParameter("comunaText");

            region = region.equals("") ? null : region;
            servicio = servicio.equals("") ? null : servicio;
            comuna = comuna.equals("") ? null : comuna;
            codigoNuevo = codigoNuevo.equals("") ? null : codigoNuevo;
            establecimientoNombre = establecimientoNombre.equals("") ? null : establecimientoNombre;
            enabled = enabled.equals("") ? null : enabled;
            tipoEstablecimiento = tipoEstablecimiento.equals("") ? null : tipoEstablecimiento;
            tipoEstablecimientoText = tipoEstablecimientoText.equals("") ? null : tipoEstablecimientoText;
            regionText = regionText.equals("") ? null : regionText;
            servicioText = servicioText.equals("") ? null : servicioText;
            comunaText = comunaText.equals("") ? null : comunaText;

            params.put("region", region);
            params.put("servicio", servicio);
            params.put("comuna", comuna);
            params.put("codigoNuevo", codigoNuevo);
            params.put("establecimientoNombre", establecimientoNombre);
            params.put("tipoEstablecimiento", tipoEstablecimiento);
            params.put("tipoEstablecimientoText", tipoEstablecimientoText);
            params.put("enabled", enabled); // estado
            params.put("regionText", regionText);
            params.put("servicioText", servicioText);
            params.put("comunaText", comunaText);

            logger.info("Región Parameter from Controller: " + region);
            logger.info("Servicio de Salud Parameter from Controller: " + servicio);
            logger.info("Comuna Parameter from Controller: " + comuna);
            logger.info("Código Nuevo o Deis Parameter from Controller: " + codigoNuevo);
            logger.info("Nombre Establecimiento Parameter from Controller: " + establecimientoNombre);
            logger.info("Tipo Establecimiento Parameter from Controller: " + tipoEstablecimiento);
            logger.info("Tipo Establecimiento Text Parameter from Controller: " + tipoEstablecimientoText);
            logger.info("Enabled Parameter from Controller: " + enabled);
            logger.info("Región Text Parameter from Controller: " + regionText);
            logger.info("Servicio Text Parameter from Controller: " + servicioText);
            logger.info("Comuna Text Parameter from Controller: " + comunaText);

            logger.info("Map params from Controller: " + params);

            session.setAttribute("params", params);

            logger.info("Session params from Controller: " + session.getAttribute("params"));
        }

        Integer numeroRegistros=8;
        Pageable pageRequest = PageRequest.of(page, numeroRegistros);
        Page<Establecimiento> establecimientos = establecimientoDao.findAllEstablecimientoCriteriaApi(pageRequest, session);
        PageRender<Establecimiento> pageRender = new PageRender<>("/establecimiento/listar", establecimientos);
        model.addAttribute("page", pageRender);

        model.addAttribute("titulo", "Encontrados: Listado de Establecimientos");
        model.addAttribute("establecimientos", establecimientos);

        return "establecimiento/listar";
    }

    @GetMapping("/limpiar")
    public String limpiarBuscador(HttpServletRequest request, HttpSession session) {

        if(session.getAttribute("params") != null) {
            session.setAttribute("params", null);
        }

        logger.info("Limpiando Session params en vista listar");
        return "redirect:/establecimiento/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        Establecimiento establecimiento = new Establecimiento();
        model.addAttribute("establecimiento", establecimiento);
        model.addAttribute("crear", true);
        model.addAttribute("resultHasErrors", false);
        model.addAttribute("elimIDInteger", 0);
        model.addAttribute("idTipoEstablecimiento", "N/A");
        model.addAttribute("tipoEstablecimientoValue", "N/A");
        model.addAttribute("titulo", "Crear Establecimiento");
        model.addAttribute("boton", "Crear Establecimiento");
        return "establecimiento/form";
    }

    @GetMapping("/form/{codigoDeis}")
    public String editar(@PathVariable("codigoDeis") Integer codigoDeis, Model model, RedirectAttributes flash) {
        Establecimiento establecimiento;
        if(codigoDeis != null && codigoDeis > 0) {
            establecimiento = establecimientoDao.findByCodigoNuevo(codigoDeis);
            if(establecimiento == null) {
                flash.addFlashAttribute("error", "El Código Deis del Establecimiento no existe en la base de datos!");
                return "redirect:/establecimiento/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El Código Deis del Establecimiento no puede ser cero!");
            return "redirect:/establecimiento/listar";
        }
        logger.info("Establecimiento está habilitado?: " + establecimiento.getElimID());
        model.addAttribute("establecimiento", establecimiento);
        model.addAttribute("crear", false);
        model.addAttribute("resultHasErrors", false);
        model.addAttribute("elimIDInteger", establecimiento.getElimID());
        model.addAttribute("idTipoEstablecimiento", establecimiento.getIdTipo());
        model.addAttribute("tipoEstablecimientoValue", establecimiento.getTipoEstablecimiento());
        model.addAttribute("titulo", "Editar Establecimiento");
        model.addAttribute("boton", "Editar Establecimiento");
        return "establecimiento/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid @ModelAttribute("establecimiento") Establecimiento establecimiento, BindingResult result, Model model, RedirectAttributes flash, HttpServletRequest request, SessionStatus status) {

        establecimientoValidator.validate(establecimiento, result);

        if(result.hasErrors()) {
            if(establecimiento.getId() == null) {
                model.addAttribute("boton", "Crear Establecimiento");
                model.addAttribute("crear", true);
                model.addAttribute("resultHasErrors", true);
            } else if(establecimiento.getId() != null && establecimiento.getId() > 0) {
                model.addAttribute("boton", "Editar Establecimiento");
                model.addAttribute("crear", false);
                model.addAttribute("resultHasErrors", true);
            }
            model.addAttribute("titulo", "Formulario Establecimiento con errores");
            model.addAttribute("hasErrorsScript", true);
            return "establecimiento/form";
        }

        establecimiento.setIdMacroZona(null);
        establecimiento.setMacroZona(null);
        establecimiento.setRegionAlias(null);
        establecimiento.setRegionOrden(null);
        establecimiento.setRegionCodigo(null);
        establecimiento.setIdOrdenServicio(null);

        Long codigoComuna = establecimiento.getComuna().getCodigoComuna();
        Comuna comuna = comunaDao.findByCodigoComuna(codigoComuna);
        establecimiento.setComuna(comuna);

        establecimiento.setIdComunaTexto(comuna.getCodigoComuna().toString());
        establecimiento.setComunaNombre(comuna.getComuna().toUpperCase());
        establecimiento.setComunaMayusc(comuna.getComuna().toUpperCase());
        establecimiento.setIdDependencia(null);
        establecimiento.setDependencia(null);
        establecimiento.setCodigoAntiguo(null);
        establecimiento.setEstablecimientoNombre(HelperString.toTitleCase(establecimiento.getEstablecimientoNombre().replaceAll("\\s{2,}", " ").trim()));

        String tipoEstablecimientoValue = request.getParameter("tipoEstablecimiento").replaceAll("\\s{2,}", " ").trim().toUpperCase();
        String tipoEstablecimientoText = request.getParameter("tipoEstablecimientoValue").replaceAll("\\s{2,}", " ").trim();
        establecimiento.setIdTipo(tipoEstablecimientoValue);
        establecimiento.setIdTipoEst(tipoEstablecimientoValue);
        establecimiento.setTipoEstablecimiento(tipoEstablecimientoText);
        logger.info("IdTipo:: " + establecimiento.getIdTipo());
        logger.info("IdTipoEst:: " + establecimiento.getIdTipoEst());
        logger.info("TipoEstablecimiento:: " + establecimiento.getTipoEstablecimiento());

        // ElimID es el Estado [Habilitado:0, Inhabilitado:1]
        logger.info("ElimID::getParameter:: " + request.getParameter("elimIDInteger"));
        String elimIDGetParameter = request.getParameter("elimIDInteger");
        Integer elimIDInteger = Integer.parseInt(elimIDGetParameter);
        establecimiento.setElimID(elimIDInteger);
        logger.info("ElimID:: " + establecimiento.getElimID());

        establecimiento.setNivel_Atencion(null);
        establecimiento.setDIR(null);
        establecimiento.setAcompanamiento(null);
        establecimiento.setIaaps(null);

        String idServicioString = establecimiento.getServicio().getId().toString();
        String idComunaString = establecimiento.getComuna().getCodigoComuna().toString();
        String idServicioIdComunaString = idServicioString.concat(idComunaString);
        Integer idServicioIdComunaInteger = Integer.parseInt(idServicioIdComunaString);
        establecimiento.setIdServicioIdComuna(idServicioIdComunaInteger);

        if(establecimiento.getId() != null && establecimiento.getId() > 0) {    // Editar
            logger.info("Establecimiento en proceso de edición o update: " + establecimiento.toString());
        } else {    // Crear
            logger.info("Establecimiento en proceso de creación o create: " + establecimiento.toString());
        }

        establecimientoDaoJpaRepo.save(establecimiento);
        String mensajeFlash = (establecimiento.getId() != null) ? "Establecimiento editado con éxito!" : "Establecimiento creado con éxito!";
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/establecimiento/listar";
    }

    /* ######################################### METODOS SELECT LIST ######################################### */

    @ModelAttribute("listaRegiones")
    public List<Region> regiones() {
        List<Region> regiones = regionDao.findAll();
        return regiones;
    }

    @ModelAttribute("listaServiciosSalud")
    public List<ServicioSalud> servicios() {
        List<ServicioSalud> serviciosSalud = servicioDao.findAll();
        return serviciosSalud;
    }

    @ModelAttribute("listaComunas")
    public List<Comuna> comunas() {
        List<Comuna> comunas = comunaDao.findAll();
        return comunas;
    }

    @ModelAttribute("listaTiposEstablecimientos")
    public List<Object> tiposEstablecimiento() {
        List<Object> tiposEstablecimiento = establecimientoDaoJpaRepo.findDistinctTipoEstablecimiento();
        /*
            for (Object object : tiposEstablecimiento) {
                Object[] objArray = (Object[]) object;
                System.out.println((String) objArray[0]);
                System.out.println((String) objArray[2]);
                if (objArray[0] == null && objArray[2] == null) {
                    System.out.println("Cambiar nombre");
                }
            }
        */
        return tiposEstablecimiento;
    }

    /* ######################################### METODOS API FETCH ######################################### */

    @PostMapping(value = "/cargar-servicios", produces = {"application/json"})
    public @ResponseBody List<ServicioSalud> cargarServicios(@RequestBody ObjectNode idRegion) {
        String idString = idRegion.get("idRegion").asText();
        Long id = Long.parseLong(idString);
        return servicioDao.findServicioSaludByIdRegion(id);
    }

    @PostMapping(value = "/cargar-comunas", produces = {"application/json"})
    public @ResponseBody List<Comuna> cargarComuna(@RequestBody ObjectNode idServicioSalud) {
        String idString = idServicioSalud.get("idServicioSalud").asText();
        Long id = Long.parseLong(idString);
        return comunaDao.findComunaByIdServicioSalud(id);
    }

    /* ######################################### EXPORTAR EXCEL ######################################### */

    @GetMapping("/export-excel")
    public void exportExcel(@RequestParam(name = "page", defaultValue = "0") Integer page, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = "establecimientos_" + currentDateTime + ".xlsx";

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachement; filename=" + fileName);

        logger.info("Session Get Attribute from Export : " + session.getAttribute("params"));
        if(session.getAttribute("params") == null) {
            Map<String, String> params = new HashMap<>();
            String region = null;
            String servicio = null;
            String comuna = null;
            String codigoNuevo = null;
            String establecimientoNombre = null;
            String tipoEstablecimiento = null;
            String tipoEstablecimientoText = null;
            String enabled = null;
            String regionText = null;
            String servicioText = null;
            String comunaText = null;

            params.put("region", region);
            params.put("servicio", servicio);
            params.put("comuna", comuna);
            params.put("codigoNuevo", codigoNuevo);
            params.put("establecimientoNombre", establecimientoNombre);
            params.put("tipoEstablecimiento", tipoEstablecimiento);
            params.put("tipoEstablecimientoText", tipoEstablecimientoText);
            params.put("enabled", enabled); // estado
            params.put("regionText", regionText);
            params.put("servicioText", servicioText);
            params.put("comunaText", comunaText);

            session.setAttribute("params", params);
        }

        int countEstablecimientos = establecimientoDao.findAll().size();
        logger.info("Count Establecimientos : " + countEstablecimientos);
        Pageable pageRequest = PageRequest.of(page, countEstablecimientos);
        Page<Establecimiento> establecimientos = establecimientoDao.findAllEstablecimientoCriteriaApi(pageRequest, session);

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
        cell.setCellValue("#    ");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Id    ");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Región    ");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Servicio de Salud    ");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Comuna    ");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Código Deis    ");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Establecimiento    ");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("Id Tipo    ");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("Tipo Establecimiento    ");
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue("Estado    ");
        cell.setCellStyle(style);

        int rowCount = 1;

        CellStyle styleData = workbook.createCellStyle();
        styleData.setBorderTop(BorderStyle.THIN);
        styleData.setBorderBottom(BorderStyle.THIN);
        styleData.setBorderLeft(BorderStyle.THIN);
        styleData.setBorderRight(BorderStyle.THIN);

        for(Establecimiento e : establecimientos) {
            Row rowData = sheet.createRow(rowCount);

            Cell cellData = rowData.createCell(0);
            cellData.setCellValue(rowCount);
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(1);
            cellData.setCellValue(e.getId());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(2);
            cellData.setCellValue(e.getRegion().getRegion());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(3);
            cellData.setCellValue(e.getServicio().getServicioSalud());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(4);
            cellData.setCellValue(e.getComuna().getComuna());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(5);
            cellData.setCellValue(e.getCodigoNuevo());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(6);
            cellData.setCellValue(e.getEstablecimientoNombre());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(7);
            cellData.setCellValue(e.getIdTipo());
            cellData.setCellStyle(styleData);

            cellData = rowData.createCell(8);
            cellData.setCellValue(e.getTipoEstablecimiento());
            cellData.setCellStyle(styleData);

            String estado;
            if(e.getElimID() == 0) {
                estado = "Habilitado";
            } else if (e.getElimID() == 1) {
                estado = "Deshabilitado";
            } else {
                estado = "Error";
            }
            cellData = rowData.createCell(9);
            cellData.setCellValue(estado);
            cellData.setCellStyle(styleData);

            rowCount++;
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
