/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.modelo;

import java.io.Serializable;
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
@Table(name = "asistentes")
public class Asistente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_asistentes;

    @JoinColumn(name = "id_user")
    @ManyToOne
    private User id_user;
    
    @JoinColumn(name = "id_evento")
    @ManyToOne
    private Event id_evento;

    public int getId_asistentes() {
        return id_asistentes;
    }

    public void setId_asistentes(int id_asistentes) {
        this.id_asistentes = id_asistentes;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id_asistentes;
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
        final Asistente other = (Asistente) obj;
        if (this.id_asistentes != other.id_asistentes) {
            return false;
        }
        return true;
    }
    
    
}
