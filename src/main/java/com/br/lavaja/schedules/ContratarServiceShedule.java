package com.br.lavaja.schedules;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.br.lavaja.dto.ContratarServicoDTO;
import com.br.lavaja.enums.StatusServico;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.repositories.ContratarServicoRepository;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.services.ContratarServicoService;
import com.google.rpc.Status;

import antlr.collections.List;

@Component
public class ContratarServiceShedule {

    @Autowired
    ContratarServicoRepository contratarServicoRepository;
    @Autowired
    ContratarServicoService contratarServicoService;
    @Autowired
    LavacarRepository lavacarRepository;

    @Async
    @Scheduled(fixedDelay = 15000, initialDelay = 0)
    public void atualizarTempoEspera() {
        var list = lavacarRepository.lavacarAberto();

        list.forEach($ -> {
            var servicosLavaCar = contratarServicoRepository.findByLavacar($);
            contratarServicoService.calcularFila(servicosLavaCar);
        });

    }

}
