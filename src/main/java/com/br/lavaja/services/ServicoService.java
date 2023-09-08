package com.br.lavaja.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.br.lavaja.dto.LavacarDTO;
import com.br.lavaja.dto.ServicoDTO;
import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.repositories.LavacarRepository;
import com.br.lavaja.repositories.ServicoRepository;
import com.br.lavaja.security.UserSS;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    LavacarRepository lavacarRepository;

    @Autowired
    LavaCarService lavaCarService;

    public ServicoModel createServico(ServicoModel servico) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LavacarModel lavacar = lavacarRepository.findByEmail(username);
        servico.setLavacarId(lavacar.getId());
        ServicoModel createServico = servicoRepository.save(servico);
        return createServico;
    }

    public ResponseEntity<ServicoModel> updateServico(Integer id, ServicoModel newServico) {
        Optional<ServicoModel> servicoOptional = servicoRepository.findById(id);
        if (servicoOptional.isPresent()) {
            ServicoModel servico = servicoOptional.get();
            UserSS user = UserService.authenticated();
            System.out.println("id lavacar" + user.getId());
            System.out.println("servico" + id);
            if (user == null || !user.getId().equals(servico.getLavacarId())) {
                throw new AuthorizationException("Acesso negado");
            }
            servico.setNome(newServico.getNome());
            servico.setValor(newServico.getValor());
            servico.setTamCarro(newServico.getTamCarro());
            servico.setTempServico(newServico.getTempServico());
            servico.setAtivo(newServico.isAtivo());

            ServicoModel servicoUpdate = servicoRepository.save(servico);
            return ResponseEntity.ok().body(servicoUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Lista os serviços do lavacar logado
    public List<ServicoModel> listaServicoLavacarLogado() {
        UserSS user = UserService.authenticated();
        LavacarModel lavacar = lavacarRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));

        return servicoRepository.findByLavacarId(lavacar.getId());
    }

   public List<ServicoDTO> listaServicoLavacar(Integer lavacarId) {
        List<ServicoModel> servicos = servicoRepository.findByLavacarId(lavacarId);
        
        return servicos.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    private ServicoDTO mapToDTO(ServicoModel servico) {
        ServicoDTO dto = new ServicoDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setValor(servico.getValor());
        dto.setTamCarro(servico.getTamCarro());
        dto.setTempServico(servico.getTempServico());
        dto.setAtivo(servico.isAtivo());
        

      LavacarModel lavacar = lavacarRepository.findById(servico.getLavacarId())
            .orElseThrow(() -> new EntityNotFoundException("Lavacar não encontrado"));

        LavacarDTO lavacarDTO = new LavacarDTO();
        lavacarDTO.setId(lavacar.getId());
        lavacarDTO.setNome(lavacar.getNome());
        lavacarDTO.setCidade(lavacar.getCidade());
        lavacarDTO.setBairro(lavacar.getBairro());
        lavacarDTO.setRua(lavacar.getRua());
        lavacarDTO.setNumero(lavacar.getNumero());
        lavacarDTO.setTelefone1(lavacar.getTelefone1());
        lavacarDTO.setTelefone2(lavacar.getTelefone2());
        dto.setLavacar(lavacarDTO);
        
        return dto;
    }
}
