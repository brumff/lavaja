package com.br.lavaja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.lavaja.repositories.LavacarRepository;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private LavacarRepository lavacarRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return lavacarRepository.findByEmail(username);
    }

}
