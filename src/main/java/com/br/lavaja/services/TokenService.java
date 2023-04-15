package com.br.lavaja.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.lavaja.models.LavacarModel;

@Service
public class TokenService {

    public static String gerarToken(LavacarModel usuario){
        return JWT.create()
        .withIssuer("Lavacar")
        .withSubject(usuario.getEmail())
        .withClaim("id", usuario.getId())
        .withExpiresAt(Date.from(LocalDateTime.now()
            .plusMinutes(10).toInstant(ZoneOffset.of("-03:00")))
            ).sign(Algorithm.HMAC256("secreta"));
    }

}
