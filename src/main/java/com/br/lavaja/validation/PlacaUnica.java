package com.br.lavaja.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlacaUnicaValidator.class)
public @interface PlacaUnica {

    String message() default "Placa já cadastrada";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
