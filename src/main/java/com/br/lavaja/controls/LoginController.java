package com.br.lavaja.controls;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.repositories.DonoCarroRepository;
import com.br.lavaja.repositories.LavacarRepository;

@Controller
public class LoginController {
    @Autowired
    private DonoCarroRepository donoCarroRepository;

    @Autowired
    private LavacarRepository lavacarRepository;

    @PostMapping("/loginDonoCarro")
    public ResponseEntity<DonoCarroModel> realizarLoginDonoCarro(@RequestBody DonoCarroModel donoCarro) {
        DonoCarroModel donoCarroEncontrado = donoCarroRepository.findByEmail(donoCarro.getEmail());

        if (donoCarroEncontrado != null && BCrypt.checkpw(donoCarro.getSenha(), donoCarroEncontrado.getSenha())) {

            return ResponseEntity.ok(donoCarroEncontrado);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/loginLavaCar")
    public ResponseEntity<LavacarModel> realizarLoginLavaCar(@RequestBody LavacarModel lavaCar) {
        LavacarModel lavaCarEncontrado = lavacarRepository.findByEmail(lavaCar.getEmail());

        if (lavaCarEncontrado != null && BCrypt.checkpw(lavaCar.getSenha(), lavaCarEncontrado.getSenha())) {

            return ResponseEntity.ok(lavaCarEncontrado);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}