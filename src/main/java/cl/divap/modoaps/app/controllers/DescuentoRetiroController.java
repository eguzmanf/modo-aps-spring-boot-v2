package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.models.entity.DescuentoRetiro;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@RequestMapping("/descuento-retiro")
public class DescuentoRetiroController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @GetMapping("/listar")
    public String listar(Model model, Authentication authentication) {

        List<DescuentoRetiro> descuentoRetiroList = funcionarioService.findAllDescuentoRetiro();

        model.addAttribute("titulo", "Listado de Descuento Retiro");
        model.addAttribute("descuentoRetiroList", descuentoRetiroList);

        return "descuento-retiro/listar";
    }

}
