package com.br.lavaja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.repositories.DonoCarroRepository;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LavacarRepository lavacarRepository;

    @Autowired
    private DonoCarroRepository donoCarroRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LavacarModel lavacar = lavacarRepository.findByEmail(email);
        DonoCarroModel donocarro = donoCarroRepository.findByEmail(email);

        if (lavacar == null && donocarro == null) {
            throw new UsernameNotFoundException(email);
        }

        if (lavacar != null) { // Se encontrou um usuário do tipo lavacar, retorna as informações dele
            return new UserSS(lavacar.getId(), lavacar.getEmail(), lavacar.getSenha(), lavacar.getPerfis(), lavacar.isAberto());
        } else { // Se encontrou um usuário do tipo dono de carro, retorna as informações dele
            return new UserSS(donocarro.getId(), donocarro.getEmail(), donocarro.getSenha(), donocarro.getPerfis(), false);
        }

    }

}
