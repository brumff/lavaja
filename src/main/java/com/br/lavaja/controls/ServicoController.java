package com.br.lavaja.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.repositories.ServicoRepository;

@RestController
@RequestMapping("/api/v1/servico")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    
}
