package com.br.lavaja.models;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "disponibilidade")
public class DisponibilidadeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean seg;
    private Boolean ter;
    private Boolean qua;
    private Boolean qui;
    private Boolean sex;
    private Boolean sab;
    private Boolean dom;
    private Date abre;
    private Date fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSeg() {
        return seg;
    }

    public void setSeg(Boolean seg) {
        this.seg = seg;
    }

    public Boolean getTer() {
        return ter;
    }

    public void setTer(Boolean ter) {
        this.ter = ter;
    }

    public Boolean getQua() {
        return qua;
    }

    public void setQua(Boolean qua) {
        this.qua = qua;
    }

    public Boolean getQui() {
        return qui;
    }

    public void setQui(Boolean qui) {
        this.qui = qui;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getSab() {
        return sab;
    }

    public void setSab(Boolean sab) {
        this.sab = sab;
    }

    public Boolean getDom() {
        return dom;
    }

    public void setDom(Boolean dom) {
        this.dom = dom;
    }

    public Date getAbre() {
        return abre;
    }

    public void setAbre(Date abre) {
        this.abre = abre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


}
