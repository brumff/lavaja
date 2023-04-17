package com.br.lavaja.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.services.LavaCarService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lavacar")
public class LavacarController {

    @Autowired
    private LavacarRepository lavacarRepository;

    @Autowired
    private LavaCarService lavaCarService; 

    @PostMapping
    public LavacarModel createLavacar(@RequestBody LavacarModel lavacar) {
        return lavaCarService.createDonoCarro(lavacar);
    }

    @GetMapping
    public ResponseEntity<List<LavacarModel>> getAllLavacar() {
        return ResponseEntity.status(HttpStatus.OK).body(lavacarRepository.findAll());
    }
    @PreAuthorize("hasAnyRole('LAVACAR')")
    @GetMapping("/{id}")
    public LavacarModel getLavacar(@PathVariable Integer id) {
        return this.lavacarRepository.findById(id).get();
    }
    @PreAuthorize("hasAnyRole('LAVACAR')")
    @PutMapping("/{id}")
    public ResponseEntity<LavacarModel> putLavacar(@RequestBody LavacarModel newLavacar, @PathVariable Integer id) {
        return lavacarRepository.findById(id).map(lavacarModel -> {
            lavacarModel.setCnpj(newLavacar.getCnpj());
            lavacarModel.setNome(newLavacar.getNome());
            lavacarModel.setLogradouro(newLavacar.getLogradouro());
            lavacarModel.setNumero(newLavacar.getNumero());
            lavacarModel.setComplemento(newLavacar.getComplemento());
            lavacarModel.setBairro(newLavacar.getBairro());
            lavacarModel.setCidade(newLavacar.getCidade());
            lavacarModel.setCep(newLavacar.getCep());
            lavacarModel.setTelefone1(newLavacar.getTelefone1());
            lavacarModel.setTelefone2(newLavacar.getTelefone2());
            lavacarModel.setEmail(newLavacar.getEmail());
            lavacarModel.setSenha(newLavacar.getSenha());
            lavacarModel.setConfSenha(newLavacar.getConfSenha());
            lavacarModel.setAtivo(newLavacar.getAtivo());
            LavacarModel lavacarUpdate = lavacarRepository.save(lavacarModel);
            return ResponseEntity.ok().body(lavacarUpdate);
        }).orElse(ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasAnyRole('LAVACAR')")
    @DeleteMapping("/{id}")
    public void deleteLavacar(@PathVariable Integer id) {
        this.lavacarRepository.deleteById(id);
    }

   /* @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        LavacarModel lavacar = lavacarRepository.findByEmail(email);

       // if (lavacar == null || !lavacar.getSenha().equals(senha)) {
         //   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        //}

        return ResponseEntity.ok().build();
    } */
}
