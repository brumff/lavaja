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
import javax.validation.constraints.Size;

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
    private LocalDateTime dataContratacaoServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataPrevisaoServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime atrasado;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataFinalServico;
    @ManyToOne
    @JoinColumn(name = "donocarro_id", referencedColumnName = "id", nullable = true)
    private DonoCarroModel donoCarro;
    @ManyToOne
    @JoinColumn(name = "servico_id", referencedColumnName = "id")
    private ServicoModel servico;
    @ManyToOne
    @JoinColumn(name = "veiculo_id", referencedColumnName = "id")
    private VeiculoModel veiculo;
    private String placaCarro;
    private boolean deleted;
    @Size(max = 15)
    private String telefone;
    private float tempFila;
    private Integer minutosAdicionais;
    
 


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

    public LocalDateTime getDataContratacaoServico() {
        return dataContratacaoServico;
    }

    public void setDataContratacaoServico(LocalDateTime dataContratacaoServico) {
        this.dataContratacaoServico = dataContratacaoServico;
    }

    public LocalDateTime getDataPrevisaoServico() {
        return dataPrevisaoServico;
    }

    public void setDataPrevisaoServico(LocalDateTime dataPrevisaoServico) {
        this.dataPrevisaoServico = dataPrevisaoServico;
    }

    public LocalDateTime getAtrasado() {
        return atrasado;
    }

    public void setAtrasado(LocalDateTime atrasado) {
        this.atrasado = atrasado;
    }

    public LocalDateTime getDataFinalServico() {
        return dataFinalServico;
    }

    public void setDataFinalServico(LocalDateTime dataFinalServico) {
        this.dataFinalServico = dataFinalServico;
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

    public VeiculoModel getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoModel veiculo) {
        this.veiculo = veiculo;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public float getTempFila() {
        return tempFila;
    }

    public void setTempFila(float tempFila) {
        this.tempFila = tempFila;
    }

    public Integer getMinutosAdicionais() {
        return minutosAdicionais;
    }

    public void setMinutosAdicionais(Integer minutosAdicionais) {
        this.minutosAdicionais = minutosAdicionais;
    }

    // converte ContratoServicoModel para DTO
    public ContratarServicoDTO converter() {
        return new ContratarServicoDTO(this);
    }

    // salva a data atual local do contrato do serviço
    public ContratarServicoModel() {
        this.dataContratacaoServico = LocalDateTime.now();
    }

    public ContratarServicoModel(ContratarServicoDTO dto) {
        this.id = dto.getId();
        this.origem = dto.getOrigem();
        this.statusServico = dto.getStatusServico();
        this.dataContratacaoServico = dto.getDataContratacaoServico();
        this.dataFinalServico = dto.getDataFinalServico();
        this.dataPrevisaoServico = dto.getDataPrevisaoServico();
        this.atrasado = dto.getAtrasado();
        this.donoCarro = dto.getDonoCarro();
        this.servico = dto.getServicoId();
        this.tempFila = dto.getTempFila();
    }

}