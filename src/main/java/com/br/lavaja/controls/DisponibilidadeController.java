package com.br.lavaja.controls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.lavaja.models.DisponibilidadeModel;
import com.br.lavaja.repositories.DisponibilidadeRepository;


@RestController
@RequestMapping("/api/v1/disponibilidade")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @PostMapping
    public DisponibilidadeModel createDisponibilidade(@RequestBody DisponibilidadeModel disponibilidade) {
        return disponibilidadeRepository.save(disponibilidade);
    }

    @GetMapping
    public ResponseEntity<List<DisponibilidadeModel>> getAllDisponibilidade() {
        return ResponseEntity.status(HttpStatus.OK).body(disponibilidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public DisponibilidadeModel getDisponibilidade(@PathVariable Integer id) {
        return this.disponibilidadeRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public ResponseEntity <DisponibilidadeModel> putDisponibilidade(@RequestBody DisponibilidadeModel newDisponibilidade, @PathVariable Integer id) {
        return disponibilidadeRepository.findById(id).map(disponibilidadeModel -> {
            disponibilidadeModel.setSeg(newDisponibilidade.getSeg());
            disponibilidadeModel.setTer(newDisponibilidade.getTer());
            disponibilidadeModel.setQua(newDisponibilidade.getQua());
            disponibilidadeModel.setQui(newDisponibilidade.getQui());
            disponibilidadeModel.setSex(newDisponibilidade.getSex());
            disponibilidadeModel.setSab(newDisponibilidade.getSab());
            disponibilidadeModel.setDom(newDisponibilidade.getDom());
            disponibilidadeModel.setAbre(newDisponibilidade.getAbre());
            disponibilidadeModel.setFecha(newDisponibilidade.getFecha());
            DisponibilidadeModel disponibilidadeUpdate = disponibilidadeRepository.save(disponibilidadeModel);
            return ResponseEntity.ok().body(disponibilidadeUpdate);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteDisponibilidade(@PathVariable Integer id) {
        this.disponibilidadeRepository.deleteById(id); 
    }
}