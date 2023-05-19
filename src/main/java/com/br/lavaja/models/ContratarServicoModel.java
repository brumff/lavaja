package com.br.lavaja.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.br.lavaja.enums.StatusServico;

@Entity
@Table(name = "contratarservico")
public class ContratarServicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String origem;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_servico")
    private StatusServico statusServico;
    private Date dataServico;
    @ManyToOne
    @JoinColumn(name = "donocarro_id", referencedColumnName = "id")
    private DonoCarroModel donoCarro;
    @ManyToOne
    @JoinColumn(name = "servico_id", referencedColumnName = "id")
    private ServicoModel servico;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOrigem() {
        return origem;
    }
    public void setOrigem(String origem) {
        this.origem = origem;
    }
    public StatusServico getStatusServico() {
        return statusServico;
    }
    public void setStatusServico(StatusServico statusServico) {
        this.statusServico = statusServico;
    }
    public Date getDataServico() {
        return dataServico;
    }
    public void setDataServico(Date dataServico) {
        this.dataServico = dataServico;
    }
    public DonoCarroModel getDonoCarro() {
        return donoCarro;
    }
    public void setDonoCarro(DonoCarroModel donoCarro) {
        this.donoCarro = donoCarro;
    }
    public ServicoModel getServico() {
        return servico;
    }
    public void setServico(ServicoModel servico) {
        this.servico = servico;
    }
    
    
        
}
