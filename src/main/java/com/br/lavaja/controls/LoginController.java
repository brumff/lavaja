package com.br.lavaja.controls;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.services.LoginService;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    

    @PostMapping("/login")
    public String realizarLogin(@RequestParam String email, @RequestParam String senha, HttpSession session, RedirectAttributes redirectAttributes) {
        if (loginService.validarLogin(email, senha)) {
            session.setAttribute("email", email);
            return  "";
        } else {
            redirectAttributes.addFlashAttribute("erro", "Email ou senha incorretos");
            return "";
        }
    }
}