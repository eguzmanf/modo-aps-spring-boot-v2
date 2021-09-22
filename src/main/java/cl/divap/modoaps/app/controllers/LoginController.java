package cl.divap.modoaps.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal, RedirectAttributes flash) {

        if(principal != null) {	// evalua si el usuario ya inició sesión, si principal es distinto a null significa que el usuario ya inició sesión y redirije a pagina de inicio (listar)
            //
            flash.addFlashAttribute("info", "Ya ha iniciado sesión anteriormente");
            return "redirect:/";
        }

        if(error != null) {
            model.addAttribute("error", "Error en el login: Usuario y/o Contraseña incorrectos, por favor vuelva a intentarlo nuevamente!");
        }

        if(logout != null) {
            model.addAttribute("success", "Has cerrado sesión con exito!!!");
        }

        model.addAttribute("titulo", "Por favor inicie sesión");

        return "auth/login";
    }
}
