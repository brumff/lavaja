package com.br.lavaja.services;

import java.security.SignatureException;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.repositories.ServicoRepository;
import com.br.lavaja.security.UserSS;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    LavacarRepository lavacarRepository;

    public ServicoModel createServico(ServicoModel servico) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LavacarModel lavacar = lavacarRepository.findByEmail(username);
        servico.setLavacarId(lavacar.getId());
        ServicoModel createServico = servicoRepository.save(servico);
        return createServico;
    }

    public ResponseEntity<ServicoModel> updateServico(Integer id, ServicoModel newServico) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LavacarModel lavacar = lavacarRepository.findByEmail(username);
        UserSS user = UserService.authenticated();
        if (user == null || (!id.equals(lavacar.getId()))) {
            throw new AuthorizationException("Acesso negado");
        }
        Optional<ServicoModel> servicoOptional = servicoRepository.findById(id);
        if (servicoOptional.isPresent()) {
            ServicoModel servico = servicoOptional.get();
            servico.setNome(newServico.getNome());
            servico.setValor(newServico.getValor());
            servico.setTamCarro(newServico.getTamCarro());
            servico.setTempServico(newServico.getTempServico());
            servico.setAtivo(newServico.isAtivo());

            ServicoModel servicoUpdate = servicoRepository.save(servico);
            return ResponseEntity.ok().body(servicoUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public Page<ServicoModel> findPage(Integer page, Integer linesPerPage, String direction){
        UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction));
        LavacarModel lavaCar = lavacarRepository.findOne(user.getId());
        return servicoRepository.findByLavaCar(lavaCar, pageRequest);
    }
    

    

}
