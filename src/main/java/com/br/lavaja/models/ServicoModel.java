package com.br.lavaja.models;

import java.time.Duration;

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
    private String tamCarro;
    @NotEmpty(message = "Tempo serviço é obrigatório")
    private float tempServico;
    private boolean ativo;
    private Integer lavacarId;
    private float duracaoEmMinutos;

    public float getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public void setDuracaoEmMinutos(float duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

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

    public String getTamCarro() {
        return tamCarro;
    }

    public void setTamCarro(String tamCarro) {
        this.tamCarro = tamCarro;
    }

    public float getTempServico() {
        return tempServico;
    }

    public void setTempServico(float tempServico) {
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

    public Duration getDuracao() {
        long duracaoEmSegundos = (long) (duracaoEmMinutos * 60); // Converter minutos para segundos
        return Duration.ofSeconds(duracaoEmSegundos); // Criar Duration com base nos segundos
    }
}
