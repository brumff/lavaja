package com.br.lavaja.services;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.websocket.SendHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.lavaja.enums.Perfil;
import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.exceptions.DataIntegrityException;
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.repositories.DonoCarroRepository;
import com.br.lavaja.security.UserSS;

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

    public ResponseEntity<DonoCarroModel> updateDonoCarro(Integer id, DonoCarroModel newDonoCarro) {
        UserSS user = UserService.authenticated();
        if(user == null || (!id.equals(user.getId()))) {
            throw new AuthorizationException("Acesso negado");
        }
        Optional<DonoCarroModel> donoCarrOptional = donoCarroRepository.findById(id);
        if(donoCarrOptional.isPresent()) {
            DonoCarroModel donoCarro = donoCarrOptional.get();
            donoCarro.setNome(newDonoCarro.getNome());
            donoCarro.setTelefone(newDonoCarro.getTelefone());
            /*if(!donoCarro.getEmail().equals(newDonoCarro.getEmail())) {
                DonoCarroModel existeDonoCarro = donoCarroRepository.findByEmail(newDonoCarro.getEmail());
                if(existeDonoCarro != null) {
                    throw new DataIntegrityException("E-mail já cadastrado para outro usuário.");
                }
            }*/
            donoCarro.setEmail(newDonoCarro.getEmail());
            donoCarro.setGenero(newDonoCarro.getGenero());
            donoCarro.setSenha(passwordEncoder().encode(newDonoCarro.getSenha()));
            donoCarro.setConfSenha(passwordEncoder().encode(newDonoCarro.getConfSenha()));
            
            DonoCarroModel donoCarroUpdate = donoCarroRepository.save(donoCarro);
            return ResponseEntity.ok().body(donoCarroUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
