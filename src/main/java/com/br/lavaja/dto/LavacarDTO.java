package com.br.lavaja.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.br.lavaja.enums.Perfil;
import com.br.lavaja.models.LavacarModel;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LavacarDTO {
    private Integer id;
    private String imgLavacar;
    private String cnpj;
    private String nome;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String cep;
    private String telefone1;
    private String telefone2;
    private String email;
    private String senha;
    private String confSenha;
    private Boolean aberto;
    private Perfil perfis;

    public LavacarDTO(LavacarModel lavacar, String tipo) {
        switch (tipo) {
            case "aberto":
                this.aberto = lavacar.isAberto();
                break;
            case "fechado":
                this.aberto = lavacar.isAberto();
                break;
        }
    }

    public LavacarDTO(LavacarModel lavacar) {
        this.id = lavacar.getId();
        this.imgLavacar = lavacar.getImgLavacar();
        this.cnpj = lavacar.getCnpj();
        this.nome = lavacar.getNome();
        this.logradouro = lavacar.getLogradouro();
        this.numero = lavacar.getNumero();
        this.complemento = lavacar.getComplemento();
        this.bairro = lavacar.getBairro();
        this.cidade = lavacar.getCidade();
        this.cep = lavacar.getCep();
        this.telefone1 = lavacar.getTelefone1();
        this.telefone2 = lavacar.getTelefone2();
        this.email = lavacar.getEmail();
        this.perfis = lavacar.getPerfis();
    }

    public LavacarDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgLavacar() {
        return imgLavacar;
    }

    public void setImgLavacar(String imgLavacar) {
        this.imgLavacar = imgLavacar;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }

    public Boolean getAberto() {
        return aberto;
    }

    public void setAberto(Boolean aberto) {
        this.aberto = aberto;
    }

    public Perfil getPerfis() {
        return perfis;
    }

    public void setPerfis(Perfil perfis) {
        this.perfis = perfis;
    }

}
