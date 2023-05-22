package com.br.lavaja.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import com.br.lavaja.dto.ContratarServicoDTO;
import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.repositories.ContratarServicoRepository;
import com.br.lavaja.repositories.DonoCarroRepository;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.security.UserSS;

@Service
public class ContratarServicoService {

    @Autowired
    ContratarServicoRepository contratarServicoRepository;

    @Autowired
    DonoCarroRepository donoCarroRepository;

    @Autowired
    LavacarRepository lavacarRepository;

    public ContratarServicoModel createContratoServico(ContratarServicoModel contratarServico) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DonoCarroModel donocarro = donoCarroRepository.findByEmail(username);
        contratarServico.setDonoCarro(donocarro);
        ContratarServicoModel createContratoServico = contratarServicoRepository.save(contratarServico);
        return createContratoServico;
    }

    public List<ContratarServicoDTO> listarServicosDonoCarroLogado() {
        UserSS user = UserService.authenticated();
        DonoCarroModel donoCarro = donoCarroRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));

        return contratarServicoRepository.findByDonoCarro(donoCarro).stream().map(ContratarServicoModel::converter)
                .collect(Collectors.toList());
    }

    public List<ContratarServicoDTO> listarServicosLavaCarLogado() {
        UserSS user = UserService.authenticated();
        LavacarModel lavaCar = lavacarRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));

        var list = contratarServicoRepository.findByLavacar(lavaCar).stream().map(ContratarServicoModel::converter)
                .collect(Collectors.toList());
        for (var model : list) {
            model.setTempFila(calcularFila(lavaCar.getId()));
        }
        return list;

    }

    public void softDeleted(ContratarServicoModel contratarServico) {
        UserSS user = UserService.authenticated();
        LavacarModel lavaCar = lavacarRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));
        contratarServico.setDeleted(true);
        contratarServicoRepository.save(contratarServico);
    }

    public ContratarServicoModel findById(Integer id) {
        return contratarServicoRepository.findById(id).orElse(null);
    }

    /*
     * public ResponseEntity<ContratarServicoDTO> updateContratarServivo (Integer
     * id, ContratarServicoModel newContratarServico){
     * Optional<ContratarServicoModel> contratarServicoOptional =
     * contratarServicoRepository.findById(id);
     * if(contratarServicoOptional.isPresent()) {
     * ContratarServicoModel contratarServico = contratarServicoOptional.get();
     * UserSS user = UserService.authenticated();
     * if (user == null || !user.getId().equals(contratarServico.findByLavacar())) {
     * throw new AuthorizationException("Acesso negado");
     * }
     * }
     * 
     * }
     */

    public float calcularFila(Integer lavacarId) {
        UserSS user = UserService.authenticated();
        LavacarModel lavaCar = lavacarRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));
        float tempoTotal = 0;
        // todos os serviços diferentes de finalizado, com id do lavacar logado
        List<ContratarServicoModel> list = contratarServicoRepository.findServicosNaoFinalizados(lavacarId);
        // verifica o tempo de serviço e adiciona na variavel tempo total
        for (var entity : list) {
            var tempo = entity.getServico().getTempServico();
            tempoTotal += tempo;
        }

        return tempoTotal;
    }
}
