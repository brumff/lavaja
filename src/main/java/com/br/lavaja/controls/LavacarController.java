package com.br.lavaja.controls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lavacar")
public class LavacarController {

    @Autowired
    private LavacarRepository lavacarRepository;

    @PostMapping
    public LavacarModel createLavacar(@RequestBody LavacarModel lavacar) {
        return lavacarRepository.save(lavacar);
    }

    @GetMapping
    public ResponseEntity<List<LavacarModel>> getAllLavacar() {
        return ResponseEntity.status(HttpStatus.OK).body(lavacarRepository.findAll());
    }

    @GetMapping("/{id}")
    public LavacarModel getLavacar(@PathVariable Integer id) {
        return this.lavacarRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public ResponseEntity <LavacarModel> putLavacar(@RequestBody LavacarModel newLavacar, @PathVariable Integer id) {
        return lavacarRepository.findById(id).map(lavacarModel -> {
            lavacarModel.setNome(newLavacar.getNome());
            LavacarModel lavacarUpdate = lavacarRepository.save(lavacarModel);
            return ResponseEntity.ok().body(lavacarUpdate);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteLavacar(@PathVariable Integer id) {
        this.lavacarRepository.deleteById(id); 
    }

}
