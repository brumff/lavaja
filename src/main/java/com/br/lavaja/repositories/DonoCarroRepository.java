package com.br.lavaja.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.lavaja.models.DonoCarroModel;

@Repository
public interface DonoCarroRepository extends JpaRepository<DonoCarroModel, Integer> {

    Optional<DonoCarroModel> findById(Integer id);

    DonoCarroModel findByEmail(String email);

}