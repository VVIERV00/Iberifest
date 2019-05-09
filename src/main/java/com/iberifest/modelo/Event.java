/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author adolfo
 */

@Entity
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_event;


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "date_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_start;

    @Column(name = "end_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_start;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

    @JoinColumn(name = "user_iduser")
    @ManyToOne
    private User user_iduser;

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getEnd_start() {
        return end_start;
    }

    public void setEnd_start(Date end_start) {
        this.end_start = end_start;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public User getUser_iduser() {
        return user_iduser;
    }

    public void setUser_iduser(User user_iduser) {
        this.user_iduser = user_iduser;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id_event;
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
        final Event other = (Event) obj;
        if (this.id_event != other.id_event) {
            return false;
        }
        return true;
    }


}
