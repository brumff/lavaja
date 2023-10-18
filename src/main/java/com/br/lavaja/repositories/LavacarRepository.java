package com.br.lavaja.repositories;


import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.lavaja.models.LavacarModel;

@Repository
public interface LavacarRepository extends JpaRepository<LavacarModel, Integer> {

    LavacarModel findByEmail(String email);

    boolean existsByCnpj(String cnpj);

    @Query("SELECT l.id FROM LavacarModel l WHERE l.email = :email")
    Integer findIdByEmail(@Param("email") String email);

    @Query("SELECT l FROM LavacarModel l WHERE l.aberto = 1")
     List<LavacarModel> lavacarAberto();

    

}
