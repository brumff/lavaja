package com.br.lavaja.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.br.lavaja.repositories.LavacarRepository;

public class UniqueCNPJValidator implements ConstraintValidator<UniqueCNPJ, String> {

    @Autowired
    private LavacarRepository lavacarRepository;

    @Override
    public void initialize(UniqueCNPJ constraint) {
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        return !lavacarRepository.existsByCnpj(cnpj);
    }
}