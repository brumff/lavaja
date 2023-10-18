package com.br.lavaja.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.br.lavaja.enums.Perfil;
import com.br.lavaja.validation.UniqueCNPJ;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "lavacar")
public class LavacarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String imgLavacar;
    @NotEmpty(message = "CNPJ é obrigatório")
    @UniqueCNPJ
    private String cnpj;
    @NotEmpty(message = "Nome é obrigatório")
    private String nome;
    @NotEmpty(message = "Endereço é obrigatório")
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
    private Double latitude;
    private Double longitude;
    @NotEmpty(message = "Telefone é obrigatório")
    private String telefone1;
    private String telefone2;
    @Email
    private String email;
    private String senha;
    private String confSenha;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean aberto;
    private Float tempoFila;

    @Enumerated(EnumType.STRING)
    private Perfil perfis;

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

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public Boolean isAberto() {
        return aberto;
    }

    public Float getTempoFila() {
        return tempoFila;
    }

    public void setTempoFila(Float tempoFila) {
        this.tempoFila = tempoFila;
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

    public LavacarModel() {
        this.perfis = Perfil.LAVACAR;
    }

}
