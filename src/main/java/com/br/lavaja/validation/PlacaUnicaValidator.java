package com.br.lavaja.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.br.lavaja.models.VeiculoModel;
import com.br.lavaja.repositories.VeiculoRepository;

public class PlacaUnicaValidator implements ConstraintValidator<PlacaUnica, String> {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Override
    public boolean isValid(String placa, ConstraintValidatorContext context) {
        if (placa == null) {
            return false;
        }
        
        Optional<VeiculoModel> existingVeiculo = veiculoRepository.findByPlaca(placa);
        return !existingVeiculo.isPresent();
    }
}
