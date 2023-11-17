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
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.schedules.ContratarServiceShedule;
import com.br.lavaja.security.UserSS;
import com.br.lavaja.services.ContratarServicoService;
import com.br.lavaja.services.UserService;

@RestController
@RequestMapping("/api/v1/contratarservico")
public class ContratarServicoController {

    @Autowired
    private ContratarServicoService contratarServicoService;

    @Autowired
    private ContratarServiceShedule contratarServiceShedule;

    @PostMapping
    public ContratarServicoDTO createContratarServ(@RequestBody ContratarServicoModel contratarServico) {
        var donoCarro = contratarServico.getDonoCarro() == null ? new DonoCarroModel()
                : contratarServico.getDonoCarro();
        donoCarro.setId(0);
        contratarServico.setDonoCarro(donoCarro);
        return new ContratarServicoDTO(contratarServicoService.contratarServicoLavacar(contratarServico));
    }

    @GetMapping
    public void debug(){
        contratarServiceShedule.atualizarTempoEspera();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContratarServicoDTO> patchContratarServico(@RequestBody ContratarServicoModel newServico,
            @PathVariable Integer id) {
        return contratarServicoService.updateContratarServicoLavacar(id, newServico);
    }

    @GetMapping("/token/{donoCarroId}")
    public ResponseEntity<String> getTokenByDonoCarroId(@PathVariable Integer donoCarroId) {
        String token = contratarServicoService.getTokenFirebaseByDonoCarroId(donoCarroId);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lavacar-servicos")
    public ResponseEntity<List<?>> getListarServicosLavacar() {
        List<ContratarServicoDTO> servicos = contratarServicoService.listarServicosLavaCarLogado();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/atualizar-tempo-espera")
    public void atualizarTempoEspera() {
        contratarServiceShedule.atualizarTempoEspera();
    }
}
