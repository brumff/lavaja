package com.br.lavaja.dto;

public class ServicoDTO {
    private Integer id;
    private String nome;
    private Double valor;
    private String tamCarro;
    private Integer tempServico;
    private boolean ativo;
    private LavacarDTO lavacar;
    
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
    public LavacarDTO getLavacar() {
        return lavacar;
    }
    public void setLavacar(LavacarDTO lavacar) {
        this.lavacar = lavacar;
    }


}