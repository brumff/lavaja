package com.br.lavaja.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.br.lavaja.models.LavacarModel;

@Repository
public interface LavacarRepository extends JpaRepository<LavacarModel, Integer>{


   LavacarModel findByEmail(String email);


    
}
