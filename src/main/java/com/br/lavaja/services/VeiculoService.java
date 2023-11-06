package com.br.lavaja.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.br.lavaja.exceptions.AuthorizationException;
import com.br.lavaja.exceptions.ValidationException;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.models.VeiculoModel;
import com.br.lavaja.repositories.DonoCarroRepository;
import com.br.lavaja.repositories.VeiculoRepository;
import com.br.lavaja.security.UserSS;

@Service
public class VeiculoService {

    @Autowired
    VeiculoRepository veiculoRepository;

    @Autowired
    DonoCarroRepository donoCarroRepository;

    public VeiculoModel createVeiculo(VeiculoModel veiculo) {
        boolean existPlaca = veiculoRepository.existsByPlaca(veiculo.getPlaca());
    
        if (existPlaca) {
            throw new RuntimeException("Veículo com essa placa já cadastrado");
        }
    
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DonoCarroModel donocarro = donoCarroRepository.findByEmail(username);
        veiculo.setDonoCarroModel(donocarro);
        VeiculoModel createVeiculo = veiculoRepository.save(veiculo);
        return createVeiculo;
    }

    public ResponseEntity<VeiculoModel> updateVeiculo(Integer id, VeiculoModel newVeiculo) {
        Optional<VeiculoModel> veiculoOptional = veiculoRepository.findById(id);
        if (veiculoOptional.isPresent()) {
            VeiculoModel veiculo = veiculoOptional.get();
            UserSS user = UserService.authenticated();

            veiculo.setMarca(newVeiculo.getMarca());
            veiculo.setModelo(newVeiculo.getModelo());
            veiculo.setCor(newVeiculo.getCor());
            if (!veiculo.getPlaca().equals(newVeiculo.getPlaca())) {
                Optional<VeiculoModel> veiculoComMesmaPlacaOptional = veiculoRepository
                        .findByPlaca(newVeiculo.getPlaca());
                if (veiculoComMesmaPlacaOptional.isPresent()) {
                    VeiculoModel veiculoComMesmaPlaca = veiculoComMesmaPlacaOptional.get();
                    if (!veiculoComMesmaPlaca.getId().equals(veiculo.getId())) {
                        throw new ValidationException("Placa já cadastrada para outro veículo.");
                    }
                }
            }

            veiculo.setPlaca(newVeiculo.getPlaca());

            VeiculoModel veiculoUpdate = veiculoRepository.save(veiculo);
            return ResponseEntity.ok().body(veiculoUpdate);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<VeiculoModel> listaCarrosLogado() {
        UserSS user = UserService.authenticated();
        DonoCarroModel donoCarro = donoCarroRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));

        return veiculoRepository.findByDonoCarroModelAndDeleted(donoCarro, false);
    }

     public VeiculoModel findById(Integer id) {
        return veiculoRepository.findById(id).orElse(null);
    }


 public void softDeleted(VeiculoModel veiculo) {
        UserSS user = UserService.authenticated();
        DonoCarroModel donoCarro = donoCarroRepository.findById(user.getId())
                .orElseThrow(() -> new AuthorizationException("Acesso negado"));
        veiculo.setDeleted(true);
        veiculoRepository.save(veiculo);

    }
}