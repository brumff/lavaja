package com.br.lavaja.controls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.repositories.ServicoRepository;
import com.br.lavaja.services.ServicoService;

@RestController
@RequestMapping("/api/v1/servico")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ServicoService servicoService;

    @PreAuthorize("hasAnyRole('LAVACAR')")
    @PostMapping
    public ServicoModel createServico(@RequestBody ServicoModel servico) {
        return servicoService.createServico(servico);
    }

    @GetMapping
    public ResponseEntity<List<ServicoModel>> getAllLavacar() {
        return ResponseEntity.status(HttpStatus.OK).body(servicoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ServicoModel getLavacar(@PathVariable Integer id) {
        
        return this.servicoRepository.findById(id).get();
    }

    @PreAuthorize("hasAnyRole('LAVACAR')")
    @PutMapping("/{id}")
    public ResponseEntity<ServicoModel> putLavacar(@RequestBody ServicoModel newServico, @PathVariable Integer id) {
        return servicoService.updateServico(id, newServico);
    }


}
