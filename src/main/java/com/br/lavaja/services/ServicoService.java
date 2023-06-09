package com.br.lavaja.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.repositories.ServicoRepository;
import com.br.lavaja.security.UserSS;


@Service
public class ServicoService {

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    LavacarRepository lavacarRepository;

    @Autowired
    LavaCarService lavaCarService;
    
    public ServicoModel createServico(ServicoModel servico) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LavacarModel lavacar = lavacarRepository.findByEmail(username);
        servico.setLavacarId(lavacar.getId());
        ServicoModel createServico = servicoRepository.save(servico);
        return createServico;
    }
    
    public ResponseEntity<ServicoModel> updateServico(Integer id, ServicoModel newServico) {
        Optional<ServicoModel> servicoOptional = servicoRepository.findById(id);
        if (servicoOptional.isPresent()) {
            ServicoModel servico = servicoOptional.get();
            UserSS user = UserService.authenticated();
            System.out.println("id lavacar" + user.getId());
            System.out.println("servico" + id);
            if (user == null || !user.getId().equals(servico.getLavacarId())) {
                throw new AuthorizationException("Acesso negado");
            }
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
    //Lista os serviços do lavacar logado
    public List<ServicoModel> listaServicoLavacar() {
        UserSS user = UserService.authenticated();
        LavacarModel lavacar = lavacarRepository.findById(user.getId()).orElseThrow(() -> new AuthorizationException("Acesso negado"));
    
        return servicoRepository.findByLavacarId(lavacar.getId());
    }

}
