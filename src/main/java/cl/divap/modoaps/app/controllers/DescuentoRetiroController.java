package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.models.dao.comuna.IComunaDao;
import cl.divap.modoaps.app.models.dao.servicioSalud.IServicioSaludDao;
import cl.divap.modoaps.app.models.entity.Comuna;
import cl.divap.modoaps.app.models.entity.DescuentoRetiro;
import cl.divap.modoaps.app.models.entity.ServicioSalud;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/descuento-retiro")
public class DescuentoRetiroController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Autowired
    private IServicioSaludDao servicioDao;

    @Autowired
    private IComunaDao comunaDao;

    @GetMapping("/listar")
    public String listar(Model model, Authentication authentication) {

        List<DescuentoRetiro> descuentoRetiroList = funcionarioService.findAllDescuentoRetiro();

        model.addAttribute("titulo", "Listado de Descuento Retiro");
        model.addAttribute("descuentoRetiroList", descuentoRetiroList);

        return "descuento-retiro/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        //
        DescuentoRetiro descuentoRetiro = new DescuentoRetiro();

        model.addAttribute("editar", false);
        model.addAttribute("hasErrorsScript", false);
        model.addAttribute("descuentoRetiro", descuentoRetiro);
        model.addAttribute("titulo", "Crear Descuento al Retiro");
        model.addAttribute("boton", "Crear Descuento al Retiro");

        return "descuento-retiro/form";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {

        DescuentoRetiro descuentoRetiro = null;

        if(id != null && id > 0) {
            //
            descuentoRetiro = funcionarioService.findDescuentoRetiroById(id);

            if(descuentoRetiro == null) {
                //
                flash.addFlashAttribute("error", "El Id del Descuento al Retiro no existe en la base de datos!");
                return "redirect:/descuento-retiro/listar";
            }

        } else {
            flash.addFlashAttribute("error", "El Id del Descuento al Retiro no puede ser cero!");
            return "redirect:/descuento-retiro/listar";
        }

        model.addAttribute("editar", true);
        model.addAttribute("hasErrorsScript", false);
        model.addAttribute("titulo", "Editar Descuento al Retiro");
        model.addAttribute("descuentoRetiro", descuentoRetiro);
        model.addAttribute("boton", "Editar Descuento al Retiro");

        return "descuento-retiro/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid @ModelAttribute("descuentoRetiro") DescuentoRetiro descuentoRetiro, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {

        if(result.hasErrors()) {

            if(descuentoRetiro.getId() == null) {
                model.addAttribute("boton", "Crear Descuento al Retiro");
            } else if(descuentoRetiro.getId() != null && descuentoRetiro.getId() > 0) {
                model.addAttribute("boton", "Editar Descuento al Retiro");
            }

            model.addAttribute("editar", false);
            model.addAttribute("hasErrorsScript", true);
            model.addAttribute("titulo", "Formulario Descuento al Retiro con errores");
            return "descuento-retiro/form";
        }

        Long codigoComuna = descuentoRetiro.getComuna().getCodigoComuna();
        System.out.println("Id Comuna en Descuento al Retiro: " + codigoComuna);
        Comuna comuna = comunaDao.findByCodigoComuna(codigoComuna);
        descuentoRetiro.setComuna(comuna);

        if(descuentoRetiro.getId() != null && descuentoRetiro.getId() > 0) {    // Editar

            DescuentoRetiro descuentoRetiroDb = funcionarioService.findDescuentoRetiroById(descuentoRetiro.getId());
            descuentoRetiro.setCreateAt(descuentoRetiroDb.getCreateAt());

            logger.info("DescuentoRetiro Id: " + descuentoRetiro.getId());
            logger.info("DescuentoRetiro CreateAt: " + descuentoRetiroDb.getCreateAt());

        } else { // Crear
        }

        String mensajeFlash = (descuentoRetiro.getId() != null) ? "Descuento al Retiro editado con éxito!" : "Descuento al Retiro creado con éxito!";

        funcionarioService.saveDescuentoRetiro(descuentoRetiro);

        flash.addFlashAttribute("success", mensajeFlash);

        return "redirect:/descuento-retiro/listar";
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

    /* ######################################### METODOS API FETCH ######################################### */

    @PostMapping(value = "/cargar-comunas", produces = {"application/json"})
    public @ResponseBody List<Comuna> cargarComuna(@RequestBody ObjectNode idServicioSalud) {

        String idString = idServicioSalud.get("idServicioSalud").asText();
        logger.info("El string idServicioSalud: " + idString);

        Long id = Long.parseLong(idString);
        logger.info("El Long idServicioSalud: " + id);

        return comunaDao.findComunaByIdServicioSalud(id);
    }

}
