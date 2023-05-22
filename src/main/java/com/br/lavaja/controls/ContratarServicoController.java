package com.br.lavaja.controls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.lavaja.dto.ContratarServicoDTO;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.repositories.ContratarServicoRepository;
import com.br.lavaja.services.ContratarServicoService;

@RestController
@RequestMapping("/api/v1/contratarservico")
public class ContratarServicoController {

    @Autowired 
    private ContratarServicoService contratarServicoService;

    @Autowired
    private ContratarServicoRepository contratarServicoRepository;

    @PostMapping
    public ContratarServicoDTO createDonoCarro(@RequestBody ContratarServicoModel contratarServico) {
        return  new ContratarServicoDTO(contratarServicoService.createContratoServico(contratarServico));
    }

    @PreAuthorize("hasAnyRole('DONOCARRO')")
    @GetMapping("/donocarro-servicos")
    public ResponseEntity<List<?>> getListarServicosDonoCarro() {
        List<ContratarServicoDTO> servicos = contratarServicoService.listarServicosDonoCarroLogado();
        return ResponseEntity.ok(servicos);
    }

    
    @PreAuthorize("hasAnyRole('LAVACAR')")
    @GetMapping("/lavacar-servicos")
    public ResponseEntity<List<?>> getListarServicosLavacar() {
        List<ContratarServicoDTO> servicos = contratarServicoService.listarServicosLavaCarLogado();
        return ResponseEntity.ok(servicos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleted(@PathVariable Integer id) {
        ContratarServicoModel contratarServicoModel = contratarServicoService.findById(id);

        if (contratarServicoModel == null) {
            return ResponseEntity.notFound().build();
        }

        contratarServicoService.softDeleted(contratarServicoModel);
        return ResponseEntity.ok("Objeto excluído com sucesso");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContratarServicoModel> patchContratarServico(@RequestBody ContratarServicoModel newServico, @PathVariable Integer id) {
        return contratarServicoService.updateContratarServivo(id, newServico);
    }


}
