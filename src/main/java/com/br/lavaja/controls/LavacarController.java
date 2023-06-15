package com.br.lavaja.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.br.lavaja.dto.LavacarDTO;
import com.br.lavaja.enums.Perfil;
import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.security.UserSS;
import com.br.lavaja.services.LavaCarService;
import com.br.lavaja.services.UserService;

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

    @GetMapping("/todos")
    public ResponseEntity<List<LavacarModel>> getAllLavacar() {
        return ResponseEntity.status(HttpStatus.OK).body(lavacarRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('LAVACAR')")
    @GetMapping("/meu-lavacar")
    public LavacarModel getLavacar() {
         UserSS user = UserService.authenticated();
        return this.lavacarRepository.findById(user.getId()).get();
    }
    
    @PreAuthorize("hasAnyRole('LAVACAR')")
    @PutMapping("/meu-lavacar")
    public ResponseEntity<?> putLavacar(@RequestBody LavacarModel newLavacar) {
        return lavaCarService.updateLavacar(newLavacar);
    }
    
    @PreAuthorize("hasAnyRole('LAVACAR')")
    @PostMapping("/abrir")
    public ResponseEntity<LavacarDTO> abrirLavacar(@RequestBody boolean aberto) {
        return ResponseEntity.ok().body(lavaCarService.abrirLavacar(aberto));
    }
    

}
