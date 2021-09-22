package cl.divap.modoaps.app.validation.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordRegexClass implements ConstraintValidator<PasswordRegexAnnotation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //
        if(value.matches("^[a-zA-Z0-9]+$")) {
            //
            return true;
        }

        return false;
    }
}
