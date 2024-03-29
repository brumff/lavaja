package com.br.lavaja.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "servico")
public class ServicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Nome é obrigatório")
    private String nome;
    @NotEmpty(message = "Valor é obrigatório")
    private Double valor;
    // private String tamCarro;
    @NotEmpty(message = "Tempo serviço é obrigatório")
    private Integer tempServico;
    private boolean ativo;
    
    @JoinColumn(name = "lavacar_id", referencedColumnName = "id", nullable = true)
    private Integer lavacarId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    // public String getTamCarro() {
    //     return tamCarro;
    // }

    // public void setTamCarro(String tamCarro) {
    //     this.tamCarro = tamCarro;
    // }

    public Integer getTempServico() {
        return tempServico;
    }

    public void setTempServico(Integer tempServico) {
        this.tempServico = tempServico;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getLavacarId() {
        return lavacarId;
    }

    public void setLavacarId(Integer lavacarId) {
        this.lavacarId = lavacarId;
    }

}
