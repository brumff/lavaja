package com.br.lavaja.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.ServicoModel;
import com.br.lavaja.models.VeiculoModel;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoModel, Integer> {

    Optional<VeiculoModel> findByPlaca(String placa);

    boolean existsByPlaca(String placa);

    Optional<VeiculoModel> findById(Integer id);

    List<VeiculoModel> findByDonoCarroModelAndDeleted(DonoCarroModel donoCarroModel, boolean deleted);
}
