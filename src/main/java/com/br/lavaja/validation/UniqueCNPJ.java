package com.br.lavaja.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import com.auth0.jwt.interfaces.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCNPJValidator.class)
@Documented
public @interface UniqueCNPJ {
    String message() default "CNPJ já cadastrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}