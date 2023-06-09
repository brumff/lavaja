package com.br.lavaja.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import com.br.lavaja.dto.ContratarServicoDTO;
import com.br.lavaja.enums.StatusServico;
import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.LavacarModel;
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
        if (donocarro != null) {
            contratarServico.setDonoCarro(donocarro);
        } else {
            DonoCarroModel donoCarroPadrao = new DonoCarroModel();
            donoCarroPadrao.setId(1);
            contratarServico.setDonoCarro(donoCarroPadrao);
        }
        contratarServico.setStatusServico(StatusServico.AGUARDANDO);
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
        // atualizarFila(lavaCar.getId());
    }

    public ContratarServicoModel findById(Integer id) {
        return contratarServicoRepository.findById(id).orElse(null);
    }

    public ResponseEntity<ContratarServicoDTO> updateContratarServico(Integer id,
            ContratarServicoModel newContratarServico) {
        List<ContratarServicoDTO> servicos = listarServicosLavaCarLogado();

        Optional<ContratarServicoModel> contratarServicoOptional = contratarServicoRepository.findById(id);
        // verifica se é o primeiro da lista
        // if (servicos.get(0).getId().equals(id)) {
        // verifica se o primeiro da lista está com status AGUARDANDO
        // }
        // if (servicos.get(0).getStatusServico() == StatusServico.AGUARDANDO) {
        // throw new CustomException(
        // "Não é possível alterar o status do serviço. Existem serviços na frente
        // aguardando.",
        // HttpStatus.BAD_REQUEST);
        // }

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
