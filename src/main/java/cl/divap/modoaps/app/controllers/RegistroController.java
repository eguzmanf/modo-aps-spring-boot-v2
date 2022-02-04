package cl.divap.modoaps.app.controllers;

import cl.divap.modoaps.app.models.entity.Contrato;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @GetMapping("/listar")
    public String listar(Model model) {

        List<Contrato> contratos = funcionarioService.findAllContratos();

        model.addAttribute("titulo", "Actualiza contratos");
        model.addAttribute("contratos", contratos);

        return "registro/listar";
    }

    @PostMapping("/acciones")
    public String accionesRegistros(@RequestParam(defaultValue = "0") String checkAllTextArray, Model model, HttpServletRequest request, RedirectAttributes flash) {

        String btnRevisarRegistros = request.getParameter("btnRevisarRegistros");
        String btnEliminarRegistros = request.getParameter("btnEliminarRegistros");

        logger.info("Acción Revisar Registros: " + btnRevisarRegistros);
        logger.info("Acción Eliminar Registros: " + btnEliminarRegistros);

        if(request.getParameterMap().containsKey("btnRevisarRegistros")) {

            logger.info("CheckBoxes Text Array: " + checkAllTextArray);

            String[] checkAllArraySplit = checkAllTextArray.split(",");
            List<String> checkAllArray = Arrays.asList(checkAllArraySplit);
            ArrayList<String> checkAllArrayFinal = new ArrayList<String>(checkAllArray);

            logger.info("CheckBoxes Array: " + checkAllArray);
            logger.info("CheckBoxes Array Final: " + checkAllArrayFinal);
            logger.info("Size of CheckBoxes Array Final: " + checkAllArrayFinal.size());

            if (!checkAllTextArray.equals("0")) {
                flash.addFlashAttribute("success", "Registro revisado con éxito!");
            }
        }

        if(request.getParameterMap().containsKey("btnEliminarRegistros")) {

            if (!checkAllTextArray.equals("0")) {
                flash.addFlashAttribute("error", "Registro eliminado con éxito!");
            }
        }

        return "redirect:/registro/listar";
    }
}
