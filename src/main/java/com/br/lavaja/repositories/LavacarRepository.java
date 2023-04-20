package com.br.lavaja.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.lavaja.models.LavacarModel;

@Repository
public interface LavacarRepository extends JpaRepository<LavacarModel, Integer> {

    LavacarModel findByEmail(String email);

    @Query("SELECT l.id FROM LavacarModel l WHERE l.email = :email")
    Integer findIdByEmail(@Param("email") String email);


}
