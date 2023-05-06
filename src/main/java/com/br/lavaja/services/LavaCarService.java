package com.br.lavaja.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.lavaja.enums.Perfil;
import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.exceptions.DataIntegrityException;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.security.UserSS;

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

    public ResponseEntity<LavacarModel> updateLavacar(LavacarModel newLavacar) {
        UserSS user = UserService.authenticated();
        /*
         * if (user == null || (!id.equals(user.getId()))) {
         * throw new AuthorizationException("Acesso negado");
         * }
         */
        Integer id = user.getId();
        Optional<LavacarModel> lavacarOptional = lavacarRepository.findById(id);
        if (lavacarOptional.isPresent()) {
            LavacarModel lavacar = lavacarOptional.get();
            lavacar.setCnpj(newLavacar.getCnpj());
            lavacar.setNome(newLavacar.getNome());
            lavacar.setLogradouro(newLavacar.getLogradouro());
            lavacar.setNumero(newLavacar.getNumero());
            lavacar.setComplemento(newLavacar.getComplemento());
            lavacar.setBairro(newLavacar.getBairro());
            lavacar.setCidade(newLavacar.getCidade());
            lavacar.setCep(newLavacar.getCep());
            lavacar.setTelefone1(newLavacar.getTelefone1());
            lavacar.setTelefone2(newLavacar.getTelefone2());
            /*if (!lavacar.getEmail().equals(newLavacar.getEmail())) {
                LavacarModel existeLavacar = lavacarRepository.findByEmail(newLavacar.getEmail());
                if (existeLavacar != null) {
                    throw new DataIntegrityException("E-mail já cadastrado para outro usuário.");
                }
            }*/
            lavacar.setEmail(newLavacar.getEmail());
            /*
             * lavacar.setSenha(passwordEncoder().encode(newLavacar.getSenha()));
             * lavacar.setConfSenha(passwordEncoder().encode(newLavacar.getConfSenha()));
             */

            LavacarModel lavacarUpdate = lavacarRepository.save(lavacar);

            return ResponseEntity.ok().body(lavacarUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public LavacarModel find(Integer id) {

        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.LAVACAR) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<LavacarModel> obj = lavacarRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(id, null));
    }

}
