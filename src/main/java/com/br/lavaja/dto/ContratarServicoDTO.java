package com.br.lavaja.dto;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Enumerated;

import com.br.lavaja.enums.Origem;
import com.br.lavaja.enums.StatusServico;
import com.br.lavaja.models.ContratarServicoModel;

public class ContratarServicoDTO {

    private Integer id;
    private Origem origem;
    private StatusServico statusServico;
    private Date dataServico;
    private Integer donoCarroId;
    private Integer servicoId;

    public ContratarServicoDTO(ContratarServicoModel contratarServico) {
        this.id = contratarServico.getId();
        this.origem = contratarServico.getOrigem();
        this.statusServico = contratarServico.getStatusServico();
        this.dataServico = contratarServico.getDataServico();
        this.donoCarroId = contratarServico.getDonoCarro().getId();
        this.servicoId = contratarServico.getServico().getId();
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

    public Date getDataServico() {
        return dataServico;
    }

    public void setDataServico(Date dataServico) {
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
}
