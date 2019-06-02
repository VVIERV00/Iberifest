/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author adolfo
 */
@Entity
@Table(name = "votos")
public class Votos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_votos;

    @Column(name = "value")
    private int value;
    
    @JoinColumn(name = "id_comentario")
    @ManyToOne
    private Comentario id_comentario;

    @JoinColumn(name = "id_user")
    @ManyToOne
    private User id_user;

    public int getId_votos() {
        return id_votos;
    }

    public void setId_votos(int id_votos) {
        this.id_votos = id_votos;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Comentario getId_comentario() {
        return id_comentario;
    }

    public void setId_comentario(Comentario id_comentario) {
        this.id_comentario = id_comentario;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id_votos;
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
        final Votos other = (Votos) obj;
        if (this.id_votos != other.id_votos) {
            return false;
        }
        return true;
    }

    
    
}
