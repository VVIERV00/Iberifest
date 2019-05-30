/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author adolfo
 */
@Entity
@Table(name = "denuncia")
public class Denuncia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_denuncia;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "resuelta")
    private boolean resuelta;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @JoinColumn(name = "id_user")
    @ManyToOne
    private User id_user;

    @JoinColumn(name = "id_evento")
    @ManyToOne
    private Event id_evento;

    @JoinColumn(name = "id_comentario")
    @ManyToOne
    private Comentario id_comentario;

    public int getId_denuncia() {
        return id_denuncia;
    }

    public void setId_denuncia(int id_denuncia) {
        this.id_denuncia = id_denuncia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isResuelta() {
        return resuelta;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public Event getId_evento() {
        return id_evento;
    }

    public void setId_evento(Event id_evento) {
        this.id_evento = id_evento;
    }

    public Comentario getId_comentario() {
        return id_comentario;
    }

    public void setId_comentario(Comentario id_comentario) {
        this.id_comentario = id_comentario;
    }

    


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id_denuncia;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Denuncia other = (Denuncia) obj;
        if (this.id_denuncia != other.id_denuncia) {
            return false;
        }
        return true;
    }

    
    
}
