package com.br.lavaja.dto;

import java.time.LocalDateTime;

import com.br.lavaja.enums.Origem;
import com.br.lavaja.enums.StatusServico;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.models.VeiculoModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.modelmapper.ModelMapper;

public class ContratarServicoDTO {

    private Integer id;
    private Origem origem;
    private StatusServico statusServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataContratacaoServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataPrevisaoServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataFinalServico;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime atrasado;
    @JsonIgnore
    private DonoCarroModel donoCarro;
    private Integer donoCarroId;
    private String donoCarroNome;
    private ServicoModel servicoId;
    private VeiculoModel veiculo;
    private String placaCarro;
    private float tempFila;
    private String nomeLavacar;

    private static ModelMapper modelMapper = new ModelMapper();

    public ContratarServicoDTO(ContratarServicoModel contratarServico) {
        this.id = contratarServico.getId();
        this.origem = contratarServico.getOrigem();
        this.statusServico = contratarServico.getStatusServico();
        this.dataContratacaoServico = contratarServico.getDataContratacaoServico();
        this.dataFinalServico = contratarServico.getDataFinalServico();
        this.dataPrevisaoServico = contratarServico.getDataPrevisaoServico();
        this.atrasado = contratarServico.getAtrasado();
        this.donoCarro = contratarServico.getDonoCarro();
        this.donoCarroId = contratarServico.getDonoCarro().getId();
        this.donoCarroNome = contratarServico.getDonoCarro().getNome();
        this.servicoId = contratarServico.getServico();
        this.veiculo = contratarServico.getVeiculo();
        this.placaCarro = contratarServico.getPlacaCarro();
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

    public LocalDateTime getDataContratacaoServico() {
        return dataContratacaoServico;
    }

    public void setDataContratacaoServico(LocalDateTime dataServico) {
        this.dataContratacaoServico = dataServico;
    }

    public LocalDateTime getDataFinalServico() {
        return dataFinalServico;
    }

    public void setDataFinalServico(LocalDateTime dataFinalServico) {
        this.dataFinalServico = dataFinalServico;
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

    public VeiculoModel getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoModel veiculo) {
        this.veiculo = veiculo;
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

    public String getPlacaCarro() {
        return placaCarro;
    }

    public void setPlacaCarro(String placaCarro) {
        this.placaCarro = placaCarro;
    }

    public String getNomeLavacar() {
        return nomeLavacar;
    }

    public void setNomeLavacar(String nomeLavacar) {
        this.nomeLavacar = nomeLavacar;
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
                ", dataServico=" + dataContratacaoServico +
                ", donoCarroId=" + donoCarroId +
                ", donoCarroNome='" + donoCarroNome + '\'' +
                ", servicoId=" + servicoId +
                ", placaCarro='" + placaCarro + '\'' +
                '}';
    }
}
