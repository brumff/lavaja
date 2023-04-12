package com.br.lavaja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.repositories.LavacarRepository;

@Service
public class LavaCarService {

    @Autowired
    LavacarRepository lavacarRepository;

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public LavacarModel createDonoCarro(LavacarModel lavacar) {

        LavacarModel existeLavacar = lavacarRepository.findByEmail(lavacar.getEmail());

        if (existeLavacar != null) {
            throw new Error("Usuário já existe");
        }

        lavacar.setSenha(passwordEncoder().encode(lavacar.getSenha()));
        lavacar.setConfSenha(passwordEncoder().encode(lavacar.getConfSenha()));
        LavacarModel createLavacar = lavacarRepository.save(lavacar);

        return createLavacar;

    }

    
}
