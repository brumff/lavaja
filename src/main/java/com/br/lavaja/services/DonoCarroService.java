package com.br.lavaja.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.repositories.DonoCarroRepository;
import com.br.lavaja.security.UserSS;
import com.br.lavaja.validation.UniqueCPFValidator;

@Service
public class DonoCarroService {

    @Autowired
    DonoCarroRepository donoCarroRepository;

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DonoCarroModel createDonoCarro(@Valid DonoCarroModel donoCarro) {

        DonoCarroModel existeDonoCarro = donoCarroRepository.findByEmail(donoCarro.getEmail());

        boolean existCPF = donoCarroRepository.existsByCpf(donoCarro.getCpf());

        if (existeDonoCarro != null) {
            throw new Error("Usuário já existe");
        }
        if (existCPF) {
            throw new Error("CPF já cadastrado");
        }

        if (donoCarro.getSenha().length() < 6 || donoCarro.getConfSenha().length() < 6) {
            throw new Error("Senha deve conter pelo menos 6 caracteres");
        }

        donoCarro.setSenha(passwordEncoder().encode(donoCarro.getSenha()));
        donoCarro.setConfSenha(passwordEncoder().encode(donoCarro.getConfSenha()));
        DonoCarroModel createDonoCarro = donoCarroRepository.save(donoCarro);

        return createDonoCarro;

    }

    public ResponseEntity<DonoCarroModel> updateDonoCarro(DonoCarroModel newDonoCarro) {
        UserSS user = UserService.authenticated();

        Integer id = user.getId();
        Optional<DonoCarroModel> donoCarrOptional = donoCarroRepository.findById(id);
        if (donoCarrOptional.isPresent()) {
            DonoCarroModel donoCarro = donoCarrOptional.get();
            donoCarro.setNome(newDonoCarro.getNome());
            donoCarro.setTelefone(newDonoCarro.getTelefone());
            donoCarro.setGenero(newDonoCarro.getGenero());

            DonoCarroModel donoCarroUpdate = donoCarroRepository.save(donoCarro);
            return ResponseEntity.ok().body(donoCarroUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<DonoCarroModel> saveTokenFirebase(DonoCarroModel newDonoCarro) {
        UserSS user = UserService.authenticated();
        Integer id = user.getId();
        Optional<DonoCarroModel> donoCarrOptional = donoCarroRepository.findById(id);
        if (donoCarrOptional.isPresent()) {
            DonoCarroModel donoCarro = donoCarrOptional.get();
            donoCarro.setTokenFirebase(newDonoCarro.getTokenFirebase());
             DonoCarroModel donoCarroUpdate = donoCarroRepository.save(donoCarro);
             return ResponseEntity.ok().body(donoCarroUpdate);
        } else {
             return ResponseEntity.notFound().build();
        }
    }

}
