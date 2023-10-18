package com.br.lavaja.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.repositories.DonoCarroRepository;

public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, String> {

    @Autowired
    private DonoCarroRepository donoCarroRepository;

    @Override
    public void initialize(UniqueCPF constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return !donoCarroRepository.existsByCpf(cpf);
    }
}