package cl.divap.modoaps.app.validation;

import cl.divap.modoaps.app.models.entity.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordSizeValidator implements Validator {

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
            if(usuario.getPassword().length() < 8 || usuario.getPassword().length() > 20) {
                //
                errors.rejectValue("password", "PasswordValidator.size.usuario.password");
            }
        }
    }
}
