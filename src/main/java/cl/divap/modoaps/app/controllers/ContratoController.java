package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.models.dao.administracionSalud.IAdministracionSaludDao;
import cl.divap.modoaps.app.models.dao.asignacionChofer.IAsignacionChoferDao;
import cl.divap.modoaps.app.models.dao.bienios.IBieniosDao;
import cl.divap.modoaps.app.models.dao.cargo.ICargoDao;
import cl.divap.modoaps.app.models.dao.categoriaProfesion.ICategoriaProfesionDao;
import cl.divap.modoaps.app.models.dao.comuna.IComunaDao;
import cl.divap.modoaps.app.models.dao.especialidad.IEspecialidadDao;
import cl.divap.modoaps.app.models.dao.establecimiento.IEstablecimientoDao;
import cl.divap.modoaps.app.models.dao.isapre.IIsapreDao;
import cl.divap.modoaps.app.models.dao.ley.ILeyDao;
import cl.divap.modoaps.app.models.dao.nivelCarrera.INivelCarreraDao;
import cl.divap.modoaps.app.models.dao.prevision.IPrevisionDao;
import cl.divap.modoaps.app.models.dao.profesion.IProfesionDao;
import cl.divap.modoaps.app.models.dao.servicioSalud.IServicioSaludDao;
import cl.divap.modoaps.app.models.dao.tipoContrato.ITipoContratoDao;
import cl.divap.modoaps.app.models.dao.usuario.ICustomUsuarioDao;
import cl.divap.modoaps.app.models.entity.*;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import cl.divap.modoaps.app.validation.JornadaLaboralLey44Horas;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Secured({"ROLE_ADMIN", "ROLE_MINSAL", "ROLE_SERVICIO", "ROLE_COMUNA", "ROLE_LA_GRANJA"})
@Controller
@RequestMapping("/contrato")
@SessionAttributes("contrato")
public class ContratoController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Autowired
    private IServicioSaludDao servicioDao;

    @Autowired
    private IComunaDao comunaDao;

    @Autowired
    private IEstablecimientoDao establecimientoDao;

    @Autowired
    private IAdministracionSaludDao administracionSaludDao;

    @Autowired
    private ILeyDao leyDao;

    @Autowired
    private ITipoContratoDao tipoContratoDao;

    @Autowired
    private ICategoriaProfesionDao categoriaProfesionDao;

    @Autowired
    private INivelCarreraDao nivelCarreraDao;

    @Autowired
    private IProfesionDao profesionDao;

    @Autowired
    private IEspecialidadDao especialidadDao;

    @Autowired
    private ICargoDao cargoDao;

    @Autowired
    private IAsignacionChoferDao asignacionChoferDao;

    @Autowired
    private IBieniosDao bieniosDao;

    @Autowired
    private IPrevisionDao previsionDao;

    @Autowired
    private IIsapreDao isapreDao;

    @Autowired
    private ICustomUsuarioDao usuarioDao;

    @Autowired
    private JornadaLaboralLey44Horas jornadaLaboralLey44Horas;

    @GetMapping("/ver/{id}")
    public String detalle(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        //
        // Contrato contrato = funcionarioService.findContratoById(id);
        Contrato contrato = funcionarioService.fetchByIdWithFuncionario(id);

        if(contrato == null) {
            //
            flash.addFlashAttribute("error", "El Contrato no existe en la base de datos");
            return "redirect:/funcionario/listar";
        }

        model.addAttribute("contrato", contrato);
        model.addAttribute("titulo", "Contrato: ".concat(contrato.getFuncionario().getNombres()).concat(" ").concat(contrato.getFuncionario().getApellidoPaterno()).concat(" ").concat(contrato.getFuncionario().getApellidoMaterno()));

        return "funcionario/contrato/detalle";
    }

    @GetMapping("/form")
    public String returnForm(Model model) {
        //
        // Contrato contrato = new Contrato();

        // model.addAttribute("contrato", contrato);
        model.addAttribute("titulo", "Crear Contrato");
        model.addAttribute("boton", "Crear Contrato");
        return "funcionario/contrato/form";
    }

    @GetMapping("/form/{funcionarioId}")
    public String crear(@PathVariable(value="funcionarioId") Long funcionarioId, Map<String, Object> model, RedirectAttributes flash, Authentication authentication) {
        //
        Funcionario funcionario = funcionarioService.findOne(funcionarioId);

        if(funcionario == null) {
            //
            flash.addFlashAttribute("error", "El Funcionario no existe en la base de datos");
            return "redirect:/funcionario/listar";
        }

        Contrato contrato = new Contrato();
        contrato.setFuncionario(funcionario);

        Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
        logger.info("Su Rol es: ".concat(String.valueOf(role)));

        String userName = authentication.getName();
        logger.info("Su User Name es: ".concat(userName));

        Object idServicioUsuarioObject = usuarioDao.findServicioIdByUserName(userName);
        Long idServicioUsuarioLong = Long.parseLong(idServicioUsuarioObject.toString());
        String idServicioUsuarioString = Long.toString(idServicioUsuarioLong);
        logger.info("Su Id de Servicio de Salud es: ".concat(idServicioUsuarioString));

        Object idComunaUsuarioObject = usuarioDao.findComunaIdByUserName(userName);
        Long idComunaUsuarioLong = Long.parseLong(idComunaUsuarioObject.toString());
        String idComunaUsuarioString = Long.toString(idComunaUsuarioLong);
        logger.info("Su Id de Comuna es: ".concat(idComunaUsuarioString));

        model.put("contrato", contrato);
        model.put("roleUser", role);
        model.put("idServicioUsuario", idServicioUsuarioString);
        model.put("idComunaUsuario", idComunaUsuarioString);
        model.put("titulo", "Crear Contrato");
        model.put("boton", "Crear Contrato");
        model.put("accion", "crear");

        return "funcionario/contrato/form";
    }

    @GetMapping(value = "/form-editar/{id}")
    public String editarForm(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Authentication authentication) {
        //
        Contrato contrato = null;

        contrato = funcionarioService.findContratoById(id);

        Long idFuncionario = contrato.getFuncionario().getId();

        if(id > 0) {
            //
            if(contrato == null) {
                //
                flash.addFlashAttribute("error", "El Id del Contrato no existe en la base de datos!");
                return "redirect:/funcionario/ver/" + idFuncionario;
            }

        } else {
            //
            flash.addFlashAttribute("error", "El Id del Contrato no puede ser cero!");
            return "redirect:/funcionario/ver/" + idFuncionario;
        }


        Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
        logger.info("Su Rol es: ".concat(String.valueOf(role)));

        String userName = authentication.getName();
        logger.info("Su User Name es: ".concat(userName));

        Object idServicioUsuarioObject = usuarioDao.findServicioIdByUserName(userName);
        Long idServicioUsuarioLong = Long.parseLong(idServicioUsuarioObject.toString());
        String idServicioUsuarioString = Long.toString(idServicioUsuarioLong);
        logger.info("Su Id de Servicio de Salud es: ".concat(idServicioUsuarioString));

        Object idComunaUsuarioObject = usuarioDao.findComunaIdByUserName(userName);
        Long idComunaUsuarioLong = Long.parseLong(idComunaUsuarioObject.toString());
        String idComunaUsuarioString = Long.toString(idComunaUsuarioLong);
        logger.info("Su Id de Comuna es: ".concat(idComunaUsuarioString));

        model.put("contrato", contrato);
        model.put("roleUser", role);
        model.put("idServicioUsuario", idServicioUsuarioString);
        model.put("idComunaUsuario", idComunaUsuarioString);
        model.put("titulo", "Editar Contrato");
        model.put("boton", "Editar Contrato");
        model.put("accion", "editar");

        return "funcionario/contrato/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid @ModelAttribute("contrato") Contrato contrato, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash, Authentication auth) {
        //
        jornadaLaboralLey44Horas.validate(contrato, result);

        System.out.println("Servicio de Salud: " + contrato.getServicioSalud().getId());

        if(result.hasErrors()) {
            //
            String accion = null;
            if(contrato.getId() == null) {
                accion = "crear";

            } else if(contrato.getId() != null && contrato.getId() > 0) {
                accion = "editar";
            }

            // String nivel = contrato.getNivelCarrera().getNivelCarrera();
            // Long nivelId = contrato.getNivelCarrera().getId();
            // logger.info("NivelCarrera y su Id: " + "nivel" + " - " + nivelId);

            Collection<? extends GrantedAuthority> role = auth.getAuthorities();
            logger.info("Su Rol es: ".concat(String.valueOf(role)));

            String userName = auth.getName();
            logger.info("Su User Name es: ".concat(userName));

            Object idServicioUsuarioObject = usuarioDao.findServicioIdByUserName(userName);
            Long idServicioUsuarioLong = Long.parseLong(idServicioUsuarioObject.toString());
            String idServicioUsuarioString = Long.toString(idServicioUsuarioLong);
            logger.info("Su Id de Servicio de Salud es: ".concat(idServicioUsuarioString));

            Object idComunaUsuarioObject = usuarioDao.findComunaIdByUserName(userName);
            Long idComunaUsuarioLong = Long.parseLong(idComunaUsuarioObject.toString());
            String idComunaUsuarioString = Long.toString(idComunaUsuarioLong);
            logger.info("Su Id de Comuna es: ".concat(idComunaUsuarioString));

            model.addAttribute("roleUser", role);
            model.addAttribute("idServicioUsuario", idServicioUsuarioString);
            model.addAttribute("idComunaUsuario", idComunaUsuarioString);
            model.addAttribute("titulo", "Formulario Crear Contrato con errores");
            model.addAttribute("boton", "Crear Contrato");
            model.addAttribute("accion", accion);
            return "funcionario/contrato/form";
        }

        String nivel = contrato.getNivelCarrera().getNivelCarrera();
        Long nivelId = contrato.getNivelCarrera().getId();
        logger.info("NivelCarrera y su Id: " + nivel + " - " + nivelId);

        Long idFuncionario = contrato.getFuncionario().getId();
        Long codigoComuna = contrato.getComuna().getCodigoComuna();
        Integer codigoNuevo = contrato.getEstablecimiento().getCodigoNuevo();

        System.out.println("Id Funcionario: " + idFuncionario);
        System.out.println("Id Comuna: " + codigoComuna);

        Comuna comuna = comunaDao.findByCodigoComuna(codigoComuna);
        contrato.setComuna(comuna);
        Establecimiento establecimiento = establecimientoDao.findByCodigoNuevo(codigoNuevo);
        contrato.setEstablecimiento(establecimiento);
        contrato.setEnabled(true);
        contrato.setRevisado(false);
        contrato.setValidado(false);

        String mensajeFlash = "";
        if(contrato.getId() != null && contrato.getId() > 0) {
            // Editar
            contrato.setUsuarioEditor(auth.getName());
            contrato.setFechaEdicion(new Date());
            mensajeFlash = "Contrato editado con éxito!";
        } else {
            // Crear
            contrato.setUsuarioCreador(auth.getName());
            contrato.setFechaCarga(new Date());
            contrato.setTipoIngresoRegistro("Carga Simple");
            mensajeFlash = "Contrato creado con éxito!";
        }

        funcionarioService.saveContratoCustom(contrato);

        // String mensajeFlash = (contrato.getId() != null) ? "Contrato editado con éxito!" : "Contrato creado con éxito!";

        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/funcionario/ver/" + idFuncionario;
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        //
        Contrato contrato = funcionarioService.findContratoById(id);

        Long idFuncionario = contrato.getFuncionario().getId();

        if(id > 0 && contrato != null) {
            //
            funcionarioService.deleteContrato(id);
            flash.addFlashAttribute("success", "El Contrato ha sido eliminado con éxito!");
            return "redirect:/funcionario/ver/" + idFuncionario;
        }

        flash.addFlashAttribute("error", "El Contrato no existe en la base de datos, no se pudo eliminar!");
        return "redirect:/funcionario/listar";
    }

    /* ######################################### METODOS SELECT LIST ######################################### */

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

    @ModelAttribute("listaEstablecimientos")
    public List<Establecimiento> establecimientos() {
        //
        List<Establecimiento> establecimientos = establecimientoDao.findAll();
        return establecimientos;
    }

    @ModelAttribute("listaAdminSalud")
    public List<AdministracionSalud> administracionSalud() {
        //
        List<AdministracionSalud> administracionSalud = administracionSaludDao.findAll();
        return administracionSalud;
    }

    @ModelAttribute("listaLeyes")
    public List<Ley> leyes() {
        //
        List<Ley> leyes = leyDao.findAll();
        return leyes;
    }

    @ModelAttribute("listaTiposContratos")
    public List<TipoContrato> tipoContratos() {
        //
        List<TipoContrato> tipoContratos = tipoContratoDao.findAll();
        return tipoContratos;
    }

    @ModelAttribute("listaCategoriasProfesion")
    public List<CategoriaProfesion> categoriasProfesion() {
        //
        List<CategoriaProfesion> categoriasProfesion = categoriaProfesionDao.findAll();
        return categoriasProfesion;
    }

    @ModelAttribute("listaNivelesCarreras")
    public List<NivelCarrera> nivelesCarreras() {
        //
        List<NivelCarrera> nivelesCarreras = nivelCarreraDao.findAll();
        return nivelesCarreras;
    }

    @ModelAttribute("listaProfesiones")
    public List<Profesion> profesiones() {
        //
        List<Profesion> profesiones = profesionDao.findAll();
        return profesiones;
    }

    @ModelAttribute("listaEspecialidades")
    public List<Especialidad> especialidades() {
        //
        List<Especialidad> especialidades = especialidadDao.findAll();
        return especialidades;
    }

    @ModelAttribute("listaCargos")
    public List<Cargo> cargos() {
        //
        List<Cargo> cargos = cargoDao.findAll();
        return cargos;
    }

    @ModelAttribute("listaAsignacionesChofer")
    public List<AsignacionChofer> asignacionesChofer() {
        //
        List<AsignacionChofer> asignacionesChofer = asignacionChoferDao.findAll();
        return asignacionesChofer;
    }

    @ModelAttribute("listaBienios")
    public List<Bienios> bienios() {
        //
        List<Bienios> bienios = bieniosDao.findAll();
        return bienios;
    }

    @ModelAttribute("listaPrevisiones")
    public List<Prevision> previciones() {
        //
        List<Prevision> previciones = previsionDao.findAll();
        return previciones;
    }

    @ModelAttribute("listaIsapres")
    public List<Isapre> isapres() {
        //
        List<Isapre> isapres = isapreDao.findAll();
        return isapres;
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

    @PostMapping(value = "/cargar-establecimientos-comuna-servicio-role", produces = {"application/json"})
    public @ResponseBody List<Establecimiento> cargarEstablecimientoServicioRole(@RequestBody ObjectNode node) {

        String idString = node.get("idComuna").asText();
        Long id = Long.parseLong(idString);

        return establecimientoDao.findEstablecimientoByIdComuna(id);
    }

    @PostMapping(value = "/cargar-comunas-role-comuna", produces = {"application/json"})
    public @ResponseBody Comuna cargarComunaByIdComunaRoleComuna(@RequestBody ObjectNode node) {

        String idString = node.get("idComuna").asText();
        Long id = Long.parseLong(idString);

        return comunaDao.findComunaByIdComunaRoleComuna(id);
    }

    @PostMapping(value = "/cargar-servicio-la-granja", produces = {"application/json"})
    public @ResponseBody List<ServicioSalud> cargarServiciosLaGranja(@RequestBody ObjectNode node) {

        return servicioDao.findServiciosLaGranja();
    }

    @PostMapping(value = "/cargar-tipo-Contrato", produces = {"application/json"})
    public @ResponseBody List<TipoContrato> cargarTipoContrato(@RequestBody ObjectNode node) {

        String idString = node.get("idLey").asText();
        Long id = Long.parseLong(idString);

        return tipoContratoDao.findTipoContratoByLeyId(id);
    }

    @PostMapping(value = "/cargar-categoria-ley-19378", produces = {"application/json"})
    public @ResponseBody List<CategoriaProfesion> cargarCategoriaProfesionLey19378(@RequestBody ObjectNode node) {

        return categoriaProfesionDao.findCategoriaProfesionLey19378();
    }

    @PostMapping(value = "/cargar-nivel-carrera-ley19378", produces = {"application/json"})
    public @ResponseBody List<NivelCarrera> cargarNivelCarreraLey19378(@RequestBody ObjectNode node) {

        return nivelCarreraDao.findNivelCarreraLey19378();
    }

    @PostMapping(value = "/cargar-profesion-ley19378", produces = {"application/json"})
    public @ResponseBody List<Profesion> cargarProfesionLey19378(@RequestBody ObjectNode node) {

        String idString = node.get("categoriaProfesionId").asText();
        Long id = Long.parseLong(idString);

        return profesionDao.findProfesionLey19378(id);
    }

    @PostMapping(value = "/cargar-especialidad", produces = {"application/json"})
    public @ResponseBody List<Especialidad> cargarEspecialidad(@RequestBody ObjectNode node) {

        String idString = node.get("profesionId").asText();
        Long id = Long.parseLong(idString);

        return especialidadDao.findEspecialidadByProfesionId(id);
    }

    @PostMapping(value = "/cargar-asignacion-chofer", produces = {"application/json"})
    public @ResponseBody List<AsignacionChofer> cargarAsignacionChofer(@RequestBody ObjectNode node) {

        String idString = node.get("cargoId").asText();
        Long id = Long.parseLong(idString);

        return asignacionChoferDao.findAsignacionChoferByCargoId(id);
    }

    @PostMapping(value = "/cargar-categoria-ley-honorario-codigo", produces = {"application/json"})
    public @ResponseBody List<CategoriaProfesion> cargarCategoriaProfesionLeyHonorariosCodigo(@RequestBody ObjectNode node) {

        return categoriaProfesionDao.findCategoriaProfesionLeyHonorariosCodigo();
    }

    @PostMapping(value = "/cargar-nivel-carrera-ley-honorario-codigo", produces = {"application/json"})
    public @ResponseBody List<NivelCarrera> cargarNivelCarreraLeyHonorariosCodigo(@RequestBody ObjectNode node) {

        return nivelCarreraDao.findNivelCarreraLeyHonorariosCodigo();
    }

    @PostMapping(value = "/cargar-profesion-ley-honorario-codigo", produces = {"application/json"})
    public @ResponseBody List<Profesion> cargarProfesionLeyHonorariosCodigo(@RequestBody ObjectNode node) {

        return profesionDao.findProfesionLeyHonorariosCodigo();
    }
}
