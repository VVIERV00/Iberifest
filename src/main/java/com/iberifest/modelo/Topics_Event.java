/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.modelo;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "topics_event")
public class Topics_Event implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id_event")
    @ManyToOne
    private Event id_event;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id_topic")
    @ManyToOne
    private Topic id_topic;

    public Event getId_event() {
        return id_event;
    }

    public void setId_event(Event id_event) {
        this.id_event = id_event;
    }

    public Topic getId_topic() {
        return id_topic;
    }

    public void setId_topic(Topic id_topic) {
        this.id_topic = id_topic;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id_event);
        hash = 89 * hash + Objects.hashCode(this.id_topic);
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
        final Topics_Event other = (Topics_Event) obj;
        if (!Objects.equals(this.id_event, other.id_event)) {
            return false;
        }
        if (!Objects.equals(this.id_topic, other.id_topic)) {
            return false;
        }
        return true;
    }
    
    
    
}
