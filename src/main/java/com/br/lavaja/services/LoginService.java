package com.br.lavaja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.repositories.DonoCarroRepository;

@Service
public class LoginService {
 
    @Autowired
    private DonoCarroRepository donoCarroRepository;

    public boolean validarLogin(String email, String senha) {
        DonoCarroModel donoCarro = donoCarroRepository.findByEmail(email);
        if (donoCarro == null) {
            return false;
        }

        return BCrypt.checkpw(senha, donoCarro.getSenha());
    }
}
