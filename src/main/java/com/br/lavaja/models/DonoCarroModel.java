package com.br.lavaja.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.br.lavaja.enums.Perfil;

@Entity
@Table(name = "donocarro")
public class DonoCarroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Size(max = 15)
    private String telefone;
    @Size(max = 150)
    private String email;
    private String genero;
    @Size(max = 30, min = 6)
    private String senha;
    @Size(max = 30, min = 6)
    private String confSenha;
    @Enumerated(EnumType.STRING)
    private Perfil perfis;

    public DonoCarroModel() {
        this.perfis = Perfil.DONOCARRO;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public Perfil getPerfis() {
        return this.perfis;
    }

}
