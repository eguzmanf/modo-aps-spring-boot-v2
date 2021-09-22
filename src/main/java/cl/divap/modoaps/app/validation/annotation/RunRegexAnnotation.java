package cl.divap.modoaps.app.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = RunRegexClass.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface RunRegexAnnotation {
    //
    String message() default "Run con formato inválido!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
