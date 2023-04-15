package com.br.lavaja.controls;

import org.springframework.web.bind.annotation.RestController;

import com.br.lavaja.dto.Login;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.services.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    public String login(@RequestBody Login login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                login.login(), login.password());


            Authentication authentication = this.authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);

            var usuario = (LavacarModel) authentication.getPrincipal();

            // LavacarModel lavacar = new LavacarModel();
            return TokenService.gerarToken(usuario);

    }
}