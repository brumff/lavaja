package com.br.lavaja.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.lavaja.enums.StatusServico;
import com.br.lavaja.models.ContratarServicoModel;
import com.br.lavaja.models.DonoCarroModel;
import com.br.lavaja.models.LavacarModel;

@Repository
public interface ContratarServicoRepository extends JpaRepository<ContratarServicoModel, Integer> {

    List<ContratarServicoModel> findByDonoCarro(DonoCarroModel donoCarro);

    @Query("SELECT c FROM ContratarServicoModel c JOIN c.servico s  WHERE c.deleted = 0 ORDER BY c.dataContratacaoServico ASC")
    List<ContratarServicoModel> findByLavacar(LavacarModel lavacar);

    List<ContratarServicoModel> findByDeletedFalse();

    @Query("SELECT c FROM ContratarServicoModel c JOIN c.servico s  WHERE c.statusServico != 'FINALIZADO' ")
    List<ContratarServicoModel> findServicosNaoFinalizados();

    List<ContratarServicoModel> findByStatusServico(StatusServico statusServico);

    List<ContratarServicoModel> findByStatusServicoIn(List<StatusServico> statusServico);


    @Query("SELECT cs FROM ContratarServicoModel cs INNER JOIN cs.servico s WHERE s.lavacarId = :lavacarId ORDER BY cs.deleted ASC")
    List<ContratarServicoModel> findByLavacarIdOrderByDeletedAsc(@Param("lavacarId") Integer lavacarId);

    @Query("SELECT c FROM ContratarServicoModel c  WHERE c.statusServico != 'AGUARDANDO' ")
    List<ContratarServicoModel> findServicosAguardando();
}
