/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.controlador;

import com.iberifest.EJB.EventFacadeLocal;
//import com.iberifest.EJB.SubscriptionsFacadeLocal;
import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.modelo.Event;
import com.iberifest.modelo.Subscriptions;
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
public class AltaSubscriptionController implements Serializable {

   
    // @EJBprivate SubscriptionsFacadeLocal subscriptionsEJB;

    @EJB
    private EventFacadeLocal eventEJB;

    @EJB
    private UserFacadeLocal userEJB;

    private Subscriptions subscription;
    private Event event;
    private User user;

    @PostConstruct
    public void init() {
        subscription = new Subscriptions();
        event = new Event();
        user = new User();
    }

    public void createSubscription() {

        try {

            //subscriptionsEJB.create(subscription);

        } catch (Exception e) {

            System.out.println("Error al crear subscricion");
        }
    }

    public Subscriptions getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscriptions subscription) {
        this.subscription = subscription;
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
