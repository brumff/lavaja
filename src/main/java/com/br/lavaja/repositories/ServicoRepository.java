package com.br.lavaja.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.lavaja.models.ServicoModel;

@Repository
public interface ServicoRepository extends JpaRepository <ServicoModel, Integer>{
    
    Optional<ServicoModel> findById(Integer id);
}
