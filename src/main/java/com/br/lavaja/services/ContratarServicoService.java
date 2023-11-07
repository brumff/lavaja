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
    ServicoRepository servicoRepository;

    @Autowired
    LavacarRepository lavacarRepository;

    @Autowired
    private FCMService fcmService;

    public ContratarServicoModel contratarServicoLavacar(ContratarServicoModel contratarServico) {
        var servico = servicoRepository.findById(contratarServico.getServico().getId());
        contratarServico.setServico(servico.get());
        var lavacarId = servico.get().getLavacarId();
        var optional = lavacarRepository.findById(lavacarId);
        if (optional.isEmpty()) {

        }

        DonoCarroModel donoCarroPadrao = new DonoCarroModel();

        donoCarroPadrao.setId(1);
        contratarServico.setDonoCarro(donoCarroPadrao);
        contratarServico.setTempFila(0);
        contratarServico.setStatusServico(StatusServico.AGUARDANDO);

        ContratarServicoModel contratoServicoLavacar = contratarServicoRepository.save(contratarServico);
        atualizarTempoDeEspera(optional.get());

        return contratoServicoLavacar;

    }

    public ResponseEntity<ContratarServicoDTO> updateContratarServicoLavacar(Integer id,
                                                                             ContratarServicoModel newContratarServico) {

        Optional<ContratarServicoModel> contratarServicoOptional = contratarServicoRepository.findById(id);
        if (contratarServicoOptional.isPresent()) {
            ContratarServicoModel contratarServico = contratarServicoOptional.get();
            contratarServico.setStatusServico(newContratarServico.getStatusServico());
            var lavacarId = contratarServico.getServico().getLavacarId();
            var optional = lavacarRepository.findById(lavacarId);
            String donocarroTokenFirebase = contratarServico.getDonoCarro().getTokenFirebase();
            System.out.println(donocarroTokenFirebase);

            if (optional.isEmpty()) {

            }
            var lavacar = optional.get();

            if (newContratarServico.getStatusServico() == StatusServico.FINALIZADO) {
                contratarServico.setDataFinalServico(LocalDateTime.now());
                var tempoFila = lavacar.getTempoFila() - contratarServico.getServico().getTempServico();
                lavacar.setTempoFila(tempoFila);
                String donoDoCarroToken = donocarroTokenFirebase;
                String mensagem = "Seu carro está limpo e pronto para ser retirado. Sinta o frescor e o brilho da limpeza enquanto você volta para a estrada.";
                // fcmService.enviarNotServFinalizado(donoDoCarroToken, mensagem);
                contratarServico.setTempFila(0);
                lavacarRepository.save(lavacar);
            } else if (newContratarServico.getStatusServico() == StatusServico.EM_LAVAGEM) {
                int minutosAdicionais = newContratarServico.getMinutosAdicionais();

                if (minutosAdicionais > 0) {
                    var atrasado = contratarServico.getAtrasado() != null ? contratarServico.getAtrasado().plusMinutes(minutosAdicionais) :
                            contratarServico.getDataPrevisaoServico().plusMinutes(minutosAdicionais);
                    contratarServico.setAtrasado(atrasado);
                    System.out.println(atrasado);
                    contratarServico.setTempFila(0);
                }
            }
            // contratarServico.setDataUpdateServico(LocalDateTime.now());
            ContratarServicoModel contratarServicoUpdate = contratarServicoRepository.save(contratarServico);
            ContratarServicoDTO contratarServicoDTO = ContratarServicoDTO.toDTO(contratarServicoUpdate);

            return ResponseEntity.ok().body(contratarServicoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public void calcularFila(List<ContratarServicoModel> list) {
        var tempoDeContratosAnteriores = 0;
        LocalDateTime dataDePrevisaoAnterior =  null;
        LocalDateTime dataAtraso = null;
        LocalDateTime dataFinalizado = null;

        for (int i = 0; i < list.size(); i++) {
            var now = LocalDateTime.now();
            var contrato = list.get(i);
            var dataFinal = contrato.getDataContratacaoServico();
            var tempoDeServico = contrato.getServico().getTempServico() == null ? 0
                    : contrato.getServico().getTempServico();

            if (i == 0) {
                dataFinal = dataFinal.plusMinutes(tempoDeServico);
                contrato.setDataPrevisaoServico(dataFinal);
            } else {
                if(dataDePrevisaoAnterior != null && contrato.getAtrasado() == null && contrato.getDataContratacaoServico().isBefore(dataDePrevisaoAnterior)){
                    dataFinal = dataDePrevisaoAnterior.plusMinutes(tempoDeServico);
                }else{
                    dataFinal = dataFinal.plusMinutes(tempoDeServico + tempoDeContratosAnteriores);
                }
                contrato.setDataPrevisaoServico(dataFinal);
            }

            if (dataFinal.isBefore(now) && contrato.getDataFinalServico() == null) {
                if (contrato.getStatusServico() != StatusServico.EM_LAVAGEM && dataFinalizado == null) {
                    contrato.setAtrasado(now);
                }
            }

            if(contrato.getStatusServico() != StatusServico.EM_LAVAGEM){
                if(dataFinalizado != null && dataFinalizado.plusMinutes(tempoDeServico).isAfter(now) && contrato.getDataFinalServico() == null){
                    dataAtraso = dataFinalizado;
                }else if(dataFinalizado != null && !dataFinalizado.plusMinutes(tempoDeServico).isAfter(now) && contrato.getDataFinalServico() == null){
                    dataAtraso = now;
                }

                if(dataAtraso != null && contrato.getDataPrevisaoServico().isBefore(dataAtraso.plusMinutes(tempoDeServico))){
                    contrato.setAtrasado(dataAtraso.plusMinutes(tempoDeServico));
                }

                if(dataFinalizado != null && contrato.getAtrasado() == null){
                    dataFinal = dataFinalizado.plusMinutes(tempoDeServico);
                    contrato.setDataPrevisaoServico(dataFinal);
                }
            }

            if (contrato.getAtrasado() != null) {
                dataAtraso = contrato.getAtrasado();

                if (i != 0) {
                    contrato.getAtrasado().plusMinutes(tempoDeContratosAnteriores + tempoDeServico);
                } else {

                    contrato.getAtrasado().plusMinutes(tempoDeServico);
                }
            }else {
                dataAtraso = null;
            }

            if(contrato.getDataPrevisaoServico() != null){
                dataDePrevisaoAnterior = contrato.getDataPrevisaoServico();
            }

            if(contrato.getDataFinalServico() != null)
                dataFinalizado = contrato.getDataFinalServico();



            tempoDeContratosAnteriores += tempoDeServico;

            contratarServicoRepository.save(contrato);
        }


    }

    /*
     * public int calcularFilaAtraso(int index, List<ContratarServicoModel> list) {
     * var tempoDeEspera = 0;
     *
     * for (var contrato : list) {
     * if (!contrato.isDeleted()) {
     * var tempoDeServico = contrato.getServico().getTempServico() == null ? 0
     * : contrato.getServico().getTempServico();
     *
     * if (contrato.getStatusServico() == StatusServico.AGUARDANDO) {
     * var tempoDesdeCriação = ChronoUnit.MINUTES.between(contrato.getDataServico(),
     * LocalDateTime.now());
     * if (list.size() == 1) {
     * tempoDeEspera += tempoDesdeCriação;
     * } else {
     * tempoDeEspera += tempoDesdeCriação + tempoDeServico;
     * break;
     * }
     *
     * } else if (contrato.getStatusServico() == StatusServico.EM_LAVAGEM) {
     * var tempoLavagem =
     * ChronoUnit.MINUTES.between(contrato.getDataUpdateServico(),
     * LocalDateTime.now());
     * tempoDeEspera += tempoLavagem;
     * }
     * }
     * }
     *
     * return tempoDeEspera;
     * }
     */

    private void atualizarTempoDeEspera(LavacarModel lavacar) {
        var listadeContratosAtivos = contratarServicoRepository.findByLavacar(lavacar);
        calcularFila(listadeContratosAtivos);
    }

    public List<ContratarServicoDTO> listarServicosLavaCarLogado() {
        UserSS user = UserService.authenticated();
        LavacarModel lavaCar = lavacarRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));
        var list = contratarServicoRepository.findByLavacar(lavaCar);

        return list.stream().map(ContratarServicoModel::converter).collect(Collectors.toList());
    }

    public String getTokenFirebaseByDonoCarroId(Integer donoCarroId) {
        DonoCarroModel donoCarro = donoCarroRepository.findById(donoCarroId).orElse(null);
        if (donoCarro != null) {
            return donoCarro.getTokenFirebase();
        } else {
            return null;
        }
    }
}
