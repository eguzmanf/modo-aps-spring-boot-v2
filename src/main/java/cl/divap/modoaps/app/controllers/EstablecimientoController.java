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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    public String listar(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model) {
        Integer numeroRegistros=10;
        Pageable pageRequest = PageRequest.of(page, numeroRegistros);
        model.addAttribute("titulo", "Listado de Establecimientos");
        Page<Establecimiento> establecimientos = establecimientoDao.findAllCriteriaApi(pageRequest);
        PageRender<Establecimiento> pageRender = new PageRender<>("/establecimiento/listar", establecimientos);
        model.addAttribute("page", pageRender);
        model.addAttribute("establecimientos", establecimientos);
        return "establecimiento/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        Establecimiento establecimiento = new Establecimiento();
        model.addAttribute("establecimiento", establecimiento);
        model.addAttribute("crear", true);
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
            } else if(establecimiento.getId() != null && establecimiento.getId() > 0) {
                model.addAttribute("boton", "Editar Establecimiento");
                model.addAttribute("crear", false);
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

}
