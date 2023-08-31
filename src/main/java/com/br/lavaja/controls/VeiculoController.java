package com.br.lavaja.controls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.models.VeiculoModel;
import com.br.lavaja.repositories.VeiculoRepository;
import com.br.lavaja.services.VeiculoService;

@RestController
@RequestMapping("/api/v1/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoService veiculoService;

    @PreAuthorize("hasAnyRole('DONOCARRO')")
    @PostMapping
    public VeiculoModel createVeiculo(@RequestBody VeiculoModel veiculo) {
        return veiculoService.createVeiculo(veiculo);
    }

    @PreAuthorize("hasAnyRole('DONOCARRO')")
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoModel> putVeiculo(@RequestBody VeiculoModel newVeiculo, @PathVariable Integer id) {
        return veiculoService.updateVeiculo(id, newVeiculo);
    }

    @GetMapping("/{id}")
    public VeiculoModel getVeiculo(@PathVariable Integer id) {
        return this.veiculoRepository.findById(id).get();
    }

    @GetMapping
    public ResponseEntity<List<VeiculoModel>> getAllVeiculos() {
        return ResponseEntity.status(HttpStatus.OK).body(veiculoRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleted(@PathVariable Integer id) {
        VeiculoModel veiculoModel = veiculoService.findById(id);

        if (veiculoModel == null) {
            return ResponseEntity.notFound().build();
        }

        veiculoService.softDeleted(veiculoModel);
        return ResponseEntity.ok("Objeto excluído com sucesso");
    }
}
