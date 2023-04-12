package com.br.lavaja.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.repositories.DonoCarroRepository;
import com.br.lavaja.services.DonoCarroService;

@RestController
@RequestMapping("/api/v1/donocarro")
public class DonoCarroController {
    
    @Autowired
    private DonoCarroService donoCarroService;

    @Autowired
    private DonoCarroRepository donoCarroRepository;

    @PostMapping
    public DonoCarroModel createDonoCarro(@RequestBody DonoCarroModel donoCarro){
        return donoCarroService.createDonoCarro(donoCarro);
    }

    @GetMapping("/{id}")
    public DonoCarroModel getDonoCarro(@PathVariable Integer id){
        return this.donoCarroRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonoCarroModel> putDonoCarro (@RequestBody DonoCarroModel newDonoCarro, @PathVariable Integer id){
        return donoCarroRepository.findById(id).map(donoCarroModel -> {
            donoCarroModel.setNome(newDonoCarro.getNome());
            donoCarroModel.setTelefone(newDonoCarro.getTelefone());
            donoCarroModel.setEmail(newDonoCarro.getEmail());
            donoCarroModel.setGenero(newDonoCarro.getGenero());
            DonoCarroModel donoCarroUpdate = donoCarroRepository.save(donoCarroModel);
            return ResponseEntity.ok().body(donoCarroUpdate);
        }).orElse(ResponseEntity.notFound().build());
    }
    

}
