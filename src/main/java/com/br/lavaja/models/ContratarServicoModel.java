package com.br.lavaja.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.br.lavaja.dto.ContratarServicoDTO;
import com.br.lavaja.enums.Origem;
import com.br.lavaja.enums.StatusServico;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "contratarservico")
public class ContratarServicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Origem origem;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_servico")
    private StatusServico statusServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataServico;
    @ManyToOne
    @JoinColumn(name = "donocarro_id", referencedColumnName = "id")
    private DonoCarroModel donoCarro;
    @ManyToOne
    @JoinColumn(name = "servico_id", referencedColumnName = "id")
    private ServicoModel servico;
    private int tempFila;
    private String placaCarro;
    private boolean deleted;

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

    public DonoCarroModel getDonoCarro() {
        return donoCarro;
    }

    public void setDonoCarro(DonoCarroModel donoCarro) {
        this.donoCarro = donoCarro;
    }

    public ServicoModel getServico() {
        return servico;
    }

    public void setServico(ServicoModel servico) {
        this.servico = servico;
    }

    public int getTempFila() {
        return tempFila;
    }

    public void setTempFila(int tempFila) {
        this.tempFila = tempFila;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public void setPlacaCarro(String placaCarro) {
        this.placaCarro = placaCarro;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
     //converte ContratoServicoModel para DTO
    public ContratarServicoDTO converter() {
        return new ContratarServicoDTO(this);
    }
    //salva a data atual local do contrato do serviço
    public ContratarServicoModel() {
        this.dataServico = LocalDateTime.now();
    }

    public ContratarServicoModel(ContratarServicoDTO dto) {
        this.id = dto.getId();
        this.origem = dto.getOrigem();
        this.statusServico = dto.getStatusServico();
        this.dataServico = dto.getDataServico();
        this.donoCarro = dto.getDonoCarro();
        this.servico = dto.getServicoId(); 
        this.tempFila = dto.getTempFila();
    }

}