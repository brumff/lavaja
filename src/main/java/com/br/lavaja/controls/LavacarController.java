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

    @GetMapping
    public ResponseEntity<List<LavacarModel>> getAllLavacar() {
        return ResponseEntity.status(HttpStatus.OK).body(lavacarRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('LAVACAR')")
    @GetMapping("/")
    public LavacarModel getLavacar() {
         UserSS user = UserService.authenticated();
        return this.lavacarRepository.findById(user.getId()).get();
    }
    
    @PreAuthorize("hasAnyRole('LAVACAR')")
    @PutMapping("/")
    public ResponseEntity<LavacarModel> putLavacar(@RequestBody LavacarModel newLavacar) {
        return lavaCarService.updateLavacar(newLavacar);
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
