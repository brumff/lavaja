package com.br.lavaja.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        var list = contratarServicoRepository.findByLavacar(lavaCar);
        var modelList = new ArrayList<ContratarServicoDTO>();
        for (var entity : list) {
            if (entity.getServico().getLavacarId().equals(lavaCar.getId())) {
                var model = entity.converter();
                model.setTempFila(calcularFila(modelList.size(), list));
                modelList.add(model);
            }
        }
        Collections.sort(modelList, Comparator.comparingInt(ContratarServicoDTO::getTempFila));

        return modelList;
    }

    public void softDeleted(ContratarServicoModel contratarServico) {
        UserSS user = UserService.authenticated();
        LavacarModel lavaCar = lavacarRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));
        contratarServico.setDeleted(true);
        contratarServicoRepository.save(contratarServico);
        atualizarFila(lavaCar.getId());
    }

    public void atualizarFila(Integer lavaCarId) {
        List<ContratarServicoModel> fila = contratarServicoRepository.findByLavacarIdOrderByDeletedAsc(lavaCarId);
        
      
        int tempo = 0;
        for (ContratarServicoModel model : fila) {
            if (!model.isDeleted()) {
                model.setTempFila(tempo);
                contratarServicoRepository.save(model);
                tempo += model.getServico().getTempServico();
            }
        }
    }

    public ContratarServicoModel findById(Integer id) {
        return contratarServicoRepository.findById(id).orElse(null);
    }

    public ResponseEntity<ContratarServicoDTO> updateContratarServico(Integer id,
            ContratarServicoModel newContratarServico) {
        Optional<ContratarServicoModel> contratarServicoOptional = contratarServicoRepository.findById(id);
        if (contratarServicoOptional.isPresent()) {
            ContratarServicoModel contratarServico = contratarServicoOptional.get();
            contratarServico.setStatusServico(newContratarServico.getStatusServico());

            ContratarServicoModel contratarServicoUpdate = contratarServicoRepository.save(contratarServico);
            ContratarServicoDTO contratarServicoDTO = ContratarServicoDTO.toDTO(contratarServicoUpdate);
            return ResponseEntity.ok().body(contratarServicoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public int calcularFila(int index, List<ContratarServicoModel> list) {
        UserSS user = UserService.authenticated();
        LavacarModel lavaCar = lavacarRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));
        int tempoTotal = 0;
    
        var objetosNaFrente = list.subList(index + 1, list.size());
        for (var model : objetosNaFrente) {
            if (model.getServico().getLavacarId().equals(lavaCar.getId()) && !model.isDeleted()) {
                tempoTotal += model.getServico().getTempServico();
            }
        }
        return tempoTotal;
}
}