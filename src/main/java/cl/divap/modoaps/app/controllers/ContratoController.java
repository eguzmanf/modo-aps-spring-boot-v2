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
import cl.divap.modoaps.app.models.entity.*;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Secured({"ROLE_ADMIN", "ROLE_MINSAL"})
@Controller
@RequestMapping("/contrato")
@SessionAttributes("contrato")
public class ContratoController {

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
    public String crear(@PathVariable(value="funcionarioId") Long funcionarioId, Map<String, Object> model, RedirectAttributes flash) {
        //
        Funcionario funcionario = funcionarioService.findOne(funcionarioId);

        if(funcionario == null) {
            //
            flash.addFlashAttribute("error", "El Funcionario no existe en la base de datos");
            return "redirect:/funcionario/listar";
        }

        Contrato contrato = new Contrato();
        contrato.setFuncionario(funcionario);

        model.put("contrato", contrato);
        model.put("titulo", "Crear Contrato");
        model.put("boton", "Crear Contrato");

        return "funcionario/contrato/form";
    }

    @GetMapping(value = "/form-editar/{id}")
    public String editarForm(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
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

        model.put("contrato", contrato);
        model.put("titulo", "Editar Contrato");
        model.put("boton", "Editar Contrato");
        return "funcionario/contrato/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Contrato contrato, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash) {
        //
        System.out.println("Dervicio salud: " + contrato.getServicioSalud().getId());

        if(result.hasErrors()) {
            //
            model.addAttribute("titulo", "Formulario Crear Contrato con errores");
            model.addAttribute("boton", "Crear Contrato");
            return "funcionario/contrato/form";
        }

        Long idFuncionario = contrato.getFuncionario().getId();
        Long codigoComuna = contrato.getComuna().getCodigoComuna();

        System.out.println("Id Funcionario: " + idFuncionario);
        System.out.println("Id Comuna: " + codigoComuna);

        Comuna comuna = comunaDao.findByCodigoComuna(codigoComuna);
        contrato.setComuna(comuna);
        contrato.setEnabled(true);
        contrato.setRevisado(false);
        contrato.setValidado(false);

        funcionarioService.saveContratoCustom(contrato);

        String mensajeFlash = (contrato.getId() != null) ? "Contrato editado con éxito!" : "Contrato creado con éxito!";

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

}
