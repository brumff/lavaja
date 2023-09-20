package com.br.lavaja.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.br.lavaja.enums.Perfil;
import com.br.lavaja.models.LavacarModel;

public class UserSS implements UserDetails {

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;
    private String nome;

    public UserSS() {

    }

    public UserSS(Integer id, String email, String senha, Perfil perfis, String nome) {
        final List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(perfis.getDescricao()));

        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = authorities;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(Perfil perfil) {
        return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
    }
}
