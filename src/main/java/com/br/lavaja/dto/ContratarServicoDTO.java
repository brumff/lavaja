package com.br.lavaja.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Enumerated;

import com.br.lavaja.enums.Origem;
import com.br.lavaja.enums.StatusServico;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.ServicoModel;
import com.fasterxml.jackson.annotation.JsonFormat;



public class ContratarServicoDTO {

    private Integer id;
    private Origem origem;
    private StatusServico statusServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataServico;
    private DonoCarroModel donoCarroId;
    private ServicoModel servicoId;
    private float tempFila;

    public ContratarServicoDTO(ContratarServicoModel contratarServico) {
        this.id = contratarServico.getId();
        this.origem = contratarServico.getOrigem();
        this.statusServico = contratarServico.getStatusServico();
        this.dataServico = contratarServico.getDataServico();
        this.donoCarroId = contratarServico.getDonoCarro();
        this.servicoId = contratarServico.getServico();
        this.tempFila = contratarServico.getTempFila();
    }

    public ContratarServicoDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public StatusServico getStatusServico() {
        return statusServico;
    }

    public void setStatusServico(StatusServico statusServico) {
        this.statusServico = statusServico;
    }

    public LocalDateTime getDataServico() {
        return dataServico;
    }

    public void setDataServico(LocalDateTime dataServico) {
        this.dataServico = dataServico;
    }

    public DonoCarroModel getDonoCarroId() {
        return donoCarroId;
    }

    public void setDonoCarroId(DonoCarroModel donoCarroId) {
        this.donoCarroId = donoCarroId;
    }

    public ServicoModel getServicoId() {
        return servicoId;
    }

    public void setServicoId(ServicoModel servicoId) {
        this.servicoId = servicoId;
    }

    public float getTempFila() {
        return tempFila;
    }

    public void setTempFila(float tempFila) {
        this.tempFila = tempFila;
    }

    public ContratarServicoModel converter() {
        return new ContratarServicoModel(this);
    }
}

