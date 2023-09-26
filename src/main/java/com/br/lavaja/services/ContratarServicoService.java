package com.br.lavaja.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.br.lavaja.dto.ContratarServicoDTO;
import com.br.lavaja.enums.StatusServico;
import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.exceptions.ObjectNotFoundException;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.repositories.ContratarServicoRepository;
import com.br.lavaja.repositories.DonoCarroRepository;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.repositories.ServicoRepository;
import com.br.lavaja.repositories.VeiculoRepository;
import com.br.lavaja.security.UserSS;

@Service
public class ContratarServicoService {

    @Autowired
    ContratarServicoRepository contratarServicoRepository;

    @Autowired
    DonoCarroRepository donoCarroRepository;

    @Autowired
    LavacarRepository lavacarRepository;

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    VeiculoRepository veiculoRepository;

    public ContratarServicoModel createContratoServico(ContratarServicoModel contratarServico) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var servico = servicoRepository.findById(contratarServico.getServico().getId());
        contratarServico.setServico(servico.get());
        var lavacarId = servico.get().getLavacarId();
        System.out.println(contratarServico.getServico().getId());
        System.out.println(lavacarId);
        var optional = lavacarRepository.findById(lavacarId);

        if (optional.isEmpty()) {

        }
        var lavacar = optional.get();
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
        var tempoFila = listaUltimo(lavacarId);
        lavacar.setTempoFila((float) tempoFila);
        System.out.println(tempoFila);
        lavacarRepository.save(lavacar);
        return createContratoServico;
    }

        public ContratarServicoModel createContratoServicoDonoCarro(ContratarServicoModel contratarServico) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var servico = servicoRepository.findById(contratarServico.getServico().getId());
        contratarServico.setServico(servico.get());
        var lavacarId = servico.get().getLavacarId();
        var optional = lavacarRepository.findById(lavacarId);

        if (optional.isEmpty()) {

        }
        var lavacar = optional.get();
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
        var tempoFila = listaUltimo(lavacarId);
        lavacar.setTempoFila((float) tempoFila);
        lavacarRepository.save(lavacar);
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
                if (!entity.getStatusServico().equals("finalizado")) { // Adicionando condição para verificar o status
                    var model = entity.converter();
                    model.setTempFila(calcularFila(modelList.size(), list));
                    modelList.add(model);
                }
            }
        }
        Collections.sort(modelList, Comparator.comparingInt(ContratarServicoDTO::getTempFila));
        return modelList;
    }

    public int listaUltimo(int lavacarId) {
        LavacarModel lavaCar = lavacarRepository.findById(lavacarId)
                .orElseThrow(() -> new ObjectNotFoundException("Lavacar não encontrado"));
        var servicos = contratarServicoRepository.findByLavacar(lavaCar);

        var tempoTotal = 0;

        for (var servico : servicos) {
            if (servico != null && servico.getServico() != null) {

                var tempoServico = servico.getServico().getTempServico() == null ? 0
                        : servico.getServico().getTempServico();

                var tempoDeServico = ChronoUnit.MINUTES.between(servico.getDataServico(), LocalDateTime.now());
                if (tempoDeServico > servico.getServico().getTempServico()) {
                    tempoTotal += tempoDeServico;
                    continue;
                }
                tempoTotal += tempoServico;
            } 
        }
        System.out.println(tempoTotal);
        return tempoTotal;

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
        if (contratarServicoOptional.isPresent()) {

            ContratarServicoModel contratarServico = contratarServicoOptional.get();
            contratarServico.setStatusServico(newContratarServico.getStatusServico());
            var lavacarId = contratarServico.getServico().getLavacarId();
            var optional = lavacarRepository.findById(lavacarId);
            if (optional.isEmpty()) {

            }
            var lavacar = optional.get();
            if (newContratarServico.getStatusServico() == StatusServico.FINALIZADO) {
                contratarServico.setDataFinalServico(LocalDateTime.now());
                var tempoFila = lavacar.getTempoFila() - (float) contratarServico.getServico().getTempServico();
                lavacar.setTempoFila(tempoFila);
            }
            ContratarServicoModel contratarServicoUpdate = contratarServicoRepository.save(contratarServico);
            ContratarServicoDTO contratarServicoDTO = ContratarServicoDTO.toDTO(contratarServicoUpdate);
            lavacarRepository.save(lavacar);
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
