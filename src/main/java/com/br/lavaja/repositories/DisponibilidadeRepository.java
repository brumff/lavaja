package com.br.lavaja.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.lavaja.models.DisponibilidadeModel;

public interface DisponibilidadeRepository extends JpaRepository<DisponibilidadeModel, Integer> {

    Optional<DisponibilidadeModel> findById(Integer id);

}
