package com.br.lavaja.schedules;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.br.lavaja.dto.ContratarServicoDTO;
import com.br.lavaja.enums.StatusServico;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.repositories.ContratarServicoRepository;
import com.br.lavaja.services.ContratarServicoService;

@Service
public class ContagemRegressiva {
    private Semaphore semaforo = new Semaphore(2);

    @Autowired
    private ContratarServicoService contratarServicoService;

    public void regressivaLavagem(ContratarServicoModel contratarServicoModel) {
        try {
            semaforo.acquire();
            System.out.println("inicia o carro");
            Thread.sleep(contratarServicoModel.getServico().getTempServico() * 6000);
            contratarServicoModel.setStatusServico(StatusServico.FINALIZADO);
            contratarServicoService.updateContratarServico(contratarServicoModel.getId(), contratarServicoModel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release();
        }
    }

    public List<ContratarServicoDTO> filaLavagem() {
        List<ContratarServicoDTO> carrosEmLavagem = contratarServicoService.listarServicosLavaCarLogado().stream()
                .filter(f -> f.getStatusServico().equals(StatusServico.EM_LAVAGEM)).collect(Collectors.toList());

        for (ContratarServicoDTO contratarServicoDTO : carrosEmLavagem) {
            Thread thread = new Thread(
                    () -> regressivaLavagem(contratarServicoDTO.converter()));
            thread.start();
        }
        return carrosEmLavagem; 
    }

}
