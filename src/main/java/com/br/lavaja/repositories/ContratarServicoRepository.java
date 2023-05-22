package com.br.lavaja.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.LavacarModel;

@Repository
public interface ContratarServicoRepository  extends JpaRepository<ContratarServicoModel, Integer> {
    
    List<ContratarServicoModel> findByDonoCarro(DonoCarroModel donoCarro);

    @Query("SELECT c FROM ContratarServicoModel c JOIN c.servico s  WHERE c.statusServico != 'FINALIZADO' ORDER BY c.dataServico DESC")
    List<ContratarServicoModel> findByLavacar(LavacarModel lavacar);

    List<ContratarServicoModel> findByDeletedFalse();

    @Query("SELECT c FROM ContratarServicoModel c JOIN c.servico s  WHERE c.statusServico != 'FINALIZADO' ")
    List<ContratarServicoModel> findServicosNaoFinalizados();
}
