package cl.divap.modoaps.app.validation.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RunRegexClass implements ConstraintValidator<RunRegexAnnotation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //
        if(value.matches("[0-9]{1,2}[0-9]{3}[0-9]{3}[-][0-9Kk]{1}")) {
            //
            return true;
        }

        return false;
    }
}
