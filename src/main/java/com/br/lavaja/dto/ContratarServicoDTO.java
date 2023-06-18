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
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.modelmapper.ModelMapper;

public class ContratarServicoDTO {

    private Integer id;
    private Origem origem;
    private StatusServico statusServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataServico;
    @JsonIgnore
    private DonoCarroModel donoCarro;
    private Integer donoCarroId;
    private String donoCarroNome;
    private ServicoModel servicoId;
    private int tempFila;
    private String placaCarro;
    

    private static ModelMapper modelMapper = new ModelMapper();

    public ContratarServicoDTO(ContratarServicoModel contratarServico) {
        this.id = contratarServico.getId();
        this.origem = contratarServico.getOrigem();
        this.statusServico = contratarServico.getStatusServico();
        this.dataServico = contratarServico.getDataServico();
        this.donoCarro = contratarServico.getDonoCarro();
        this.donoCarroId = contratarServico.getDonoCarro().getId();
        this.donoCarroNome = contratarServico.getDonoCarro().getNome();
        this.servicoId = contratarServico.getServico();
        this.tempFila = contratarServico.getTempFila();
        this.placaCarro = contratarServico.getPlacaCarro();
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

    public DonoCarroModel getDonoCarro() {
        return donoCarro;
    }

    public void setDonoCarro(DonoCarroModel donoCarro) {
        this.donoCarro = donoCarro;
    }

    public Integer getDonoCarroId() {
        return donoCarroId;
    }

    public void setDonoCarroId(Integer donoCarroId) {
        this.donoCarroId = donoCarroId;
    }

    public String getDonoCarroNome() {
        return donoCarroNome;
    }

    public void setDonoCarroNome(String donoCarroNome) {
        this.donoCarroNome = donoCarroNome;
    }

    public ServicoModel getServicoId() {
        return servicoId;
    }

    public void setServicoId(ServicoModel servicoId) {
        this.servicoId = servicoId;
    }

    public int getTempFila() {
        return tempFila;
    }

    public void setTempFila(int tempFila) {
        this.tempFila = tempFila;
    }

    public ContratarServicoModel converter() {
        return new ContratarServicoModel(this);
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public void setPlacaCarro(String placaCarro) {
        this.placaCarro = placaCarro;
    }

    public static ContratarServicoDTO toDTO(ContratarServicoModel contratarServicoModel) {
        return modelMapper.map(contratarServicoModel, ContratarServicoDTO.class);
    }

      @Override
    public String toString() {
        return "ContratarServicoDTO{" +
                "id=" + id +
                ", origem=" + origem +
                ", statusServico=" + statusServico +
                ", dataServico=" + dataServico +
                ", donoCarroId=" + donoCarroId +
                ", donoCarroNome='" + donoCarroNome + '\'' +
                ", servicoId=" + servicoId +
                ", tempFila=" + tempFila +
                ", placaCarro='" + placaCarro + '\'' +
                '}';
    }
}
