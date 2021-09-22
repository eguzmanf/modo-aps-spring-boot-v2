package cl.divap.modoaps.app.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidaRutClass.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ValidaRutAnnotation {
    //
    String message() default "El Run es inválido!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
