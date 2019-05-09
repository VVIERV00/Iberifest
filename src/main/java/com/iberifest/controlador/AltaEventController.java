/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.controlador;

import com.iberifest.EJB.EventFacadeLocal;
import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.modelo.Event;
import com.iberifest.modelo.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author adolfo
 */

@Named
@ViewScoped
public class AltaEventController implements Serializable {

    @EJB
    private EventFacadeLocal eventEJB;

    @EJB
    private UserFacadeLocal userEJB;

    private Event event;
    private User user;

    @PostConstruct
    public void init() {
        event = new Event();
        user = new User();
    }

    public void createEvent() {
        try {
            //user = userEJB.find("id");
            //event.setUser_iduser(user_iduser);
            eventEJB.create(event);
        } catch (Exception e) {

            System.out.println("Error al crear evento");
        }
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
