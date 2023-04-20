package com.br.lavaja.repositories;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.br.lavaja.models.LavacarModel;
import com.br.lavaja.models.ServicoModel;

@Repository
public interface ServicoRepository extends JpaRepository <ServicoModel, Integer>{
    
    Optional<ServicoModel> findById(Integer id);

    @Transactional(readOnly=true)
    Page<ServicoModel> findByLavaCar(LavacarModel lavacarModel, Pageable pageRequest);
    @Transactional(readOnly=true)
    Page<ServicoModel> findByLavaCar(LavacarModel lavaCar, PageRequest pageRequest);
}
