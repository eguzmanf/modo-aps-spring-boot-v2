package cl.divap.modoaps.app.validation;

import cl.divap.modoaps.app.models.entity.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //
        Usuario usuario = (Usuario) target;

        if(usuario.getId() == null) {
            //
            if (!usuario.getPassword().equals(usuario.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "Custom.usuario.confirmPassword");
            }
        }
    }
}
