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

import lombok.extern.log4j.Log4j2;

@Log4j2
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
                    var atrasado = contratarServico.getAtrasado() != null
                            ? contratarServico.getAtrasado().plusMinutes(minutosAdicionais)
                            : contratarServico.getDataPrevisaoServico().plusMinutes(minutosAdicionais);
                    // seta atraso
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

    // tempo de previsao (AGUARDANDO, EM_ANDAMENTO)

    /*
     * [1] caminho feliz - contrata serviço -> incia -> finaliza dentro da data de
     * previsão
     * [1.1] -> verifica se a data de contratação é depois que a de previsão do
     * ultimo - SE NÃO FOR data de previsão do ultimo + tempo de serviço
     * [1.2] -> verifica se a data de contratação é depois que a de previsão do
     * ultimo - SE FOR data de atual + data de serviço
     * [2] atrasado aguardando - contrata serviço -> data atual maior que data de
     * previsão -> somar tempo de serviço na data atual
     * [2.1] -> contratar serviço com o anterior atrasado -> data de atraso do
     * anterior somar o tempo de serviço
     * [3] atrasado em_lavagem - contrata serviço -> iniciar lavagem -> ao data
     * atual = data de previsão -> adicionar minutos nessários para finalizar o
     * serviço
     * [3.1] -> contratar serviço com o anterior atrasado -> data de atraso do
     * anterior somar o tempo de serviço
     * 
     * 
     * CARRO 1: 2 MIN 23:54 -> 23:56
     * CARRO 2: 2 MIN 23:54 -> 23:58
     */

    public void calcularFila(List<ContratarServicoModel> list) {
        var tempoDeContratosAnteriores = 0;
        LocalDateTime dataDePrevisaoAnterior = null; // AGUARDANDO, EM_ANDAMENTO
        LocalDateTime dataDeAtrasoAnterior = null; // AGUARDANDO, EM_ANDAMENTO
        LocalDateTime dataDeFinalizadoAnterior = null; // FINALIZADO
        StatusServico statusAnterior = null;

        for (int i = 0; i < list.size(); i++) {
            var now = LocalDateTime.now();
            var contrato = list.get(i);
            var servico = contrato.getServico();

            var tempoDeServico = Optional.ofNullable(servico.getTempServico()).orElse(0);

            var dataInicial = contrato.getDataContratacaoServico();
            LocalDateTime dataPrevisao = contrato.getDataPrevisaoServico();

            if (contrato.getAtrasado() != null) {
                contrato.setAtrasado(contrato.getAtrasado().plusMinutes(tempoDeServico));
            }

            if (dataPrevisao == null && contrato.getStatusServico() == StatusServico.AGUARDANDO) {
                // [1.1]
                if (dataDePrevisaoAnterior != null && contrato.getAtrasado() == null
                        && dataInicial.isBefore(dataDePrevisaoAnterior)) {
                    dataPrevisao = dataDePrevisaoAnterior.plusMinutes(tempoDeServico);
                } /* [2.1] */ else if (dataDeAtrasoAnterior != null && dataPrevisao == null) {
                    dataPrevisao = dataDeAtrasoAnterior.plusMinutes(tempoDeServico);
                    contrato.setDataPrevisaoServico(dataPrevisao);
                } 
                // [1.2]
                else {
                    dataPrevisao = dataInicial.plusMinutes(tempoDeServico);
                }

                contrato.setDataPrevisaoServico(dataPrevisao);
            }

            if (contrato.getStatusServico() == StatusServico.AGUARDANDO) {
                /* [2] */
                if(dataDeAtrasoAnterior != null && dataDeAtrasoAnterior.plusMinutes(tempoDeServico).isAfter(contrato.getDataPrevisaoServico()) ) {
                    contrato.setAtrasado(dataDeAtrasoAnterior.plusMinutes(tempoDeServico));
                } else  if (now.isAfter(dataPrevisao)) {
                    contrato.setAtrasado(now.plusMinutes(tempoDeServico));
                }
            }

            if (dataPrevisao == null) {
                dataPrevisao = dataInicial.plusMinutes(tempoDeServico);
                contrato.setDataPrevisaoServico(dataPrevisao);
                log.info("FALLBACK case {}", contrato.getId());
            }

            statusAnterior = contrato.getStatusServico();
            if (statusAnterior != StatusServico.FINALIZADO)
                dataDeAtrasoAnterior = contrato.getAtrasado();

            dataDePrevisaoAnterior = contrato.getDataPrevisaoServico();
            if (contrato.getStatusServico() == StatusServico.FINALIZADO)
                dataDeFinalizadoAnterior = contrato.getDataFinalServico();

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
