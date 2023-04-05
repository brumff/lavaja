package com.br.lavaja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.repositories.DonoCarroRepository;

@Service
public class DonoCarroService {

    @Autowired
    DonoCarroRepository donoCarroRepository;

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DonoCarroModel createDonoCarro(DonoCarroModel donoCarro) {

        DonoCarroModel existeDonoCarro = donoCarroRepository.findByEmail(donoCarro.getEmail());

        if (existeDonoCarro != null) {
            throw new Error("Usuário já existe");
        }

        donoCarro.setSenha(passwordEncoder().encode(donoCarro.getSenha()));
        donoCarro.setConfSenha(passwordEncoder().encode(donoCarro.getConfSenha()));
        DonoCarroModel createDonoCarro = donoCarroRepository.save(donoCarro);

        return createDonoCarro;

    }



}
