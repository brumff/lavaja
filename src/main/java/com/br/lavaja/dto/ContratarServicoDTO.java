package com.br.lavaja.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Enumerated;

import com.br.lavaja.enums.Origem;
import com.br.lavaja.enums.StatusServico;
import com.br.lavaja.models.ContratarServicoModel;
import com.fasterxml.jackson.annotation.JsonFormat;



public class ContratarServicoDTO {

    private Integer id;
    private Origem origem;
    private StatusServico statusServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataServico;
    private Integer donoCarroId;
    private Integer servicoId;
    private Duration tempFila;

    public ContratarServicoDTO(ContratarServicoModel contratarServico) {
        this.id = contratarServico.getId();
        this.origem = contratarServico.getOrigem();
        this.statusServico = contratarServico.getStatusServico();
        this.dataServico = contratarServico.getDataServico();
        this.donoCarroId = contratarServico.getDonoCarro().getId();
        this.servicoId = contratarServico.getServico().getId();
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

    public Integer getDonoCarroId() {
        return donoCarroId;
    }

    public void setDonoCarroId(Integer donoCarroId) {
        this.donoCarroId = donoCarroId;
    }

    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }

    public Duration getTempFila() {
        return tempFila;
    }

    public void setTempFila(Duration tempFila) {
        this.tempFila = tempFila;
    }

}
