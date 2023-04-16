package com.br.lavaja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private LavacarRepository lavacarRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LavacarModel lavacar = lavacarRepository.findByEmail(email);

        if(lavacar == null){
            throw new UsernameNotFoundException(email);
        }
        return new UserSS(lavacar.getId(), lavacar.getEmail(), lavacar.getSenha(), lavacar.getPerfis());
    }
    
}
