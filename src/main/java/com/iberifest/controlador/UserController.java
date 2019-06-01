/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.controlador;

import com.iberifest.EJB.AsistenteFacadeLocal;
import com.iberifest.EJB.EventFacadeLocal;
import com.iberifest.EJB.SubscriptionsFacadeLocal;
import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.modelo.Asistente;
import com.iberifest.modelo.Event;

import com.iberifest.modelo.Subscriptions;
import com.iberifest.modelo.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adolfo
 */
@ManagedBean
@SessionScoped
public class UserController {

    @EJB
    private UserFacadeLocal userEJB;

    @EJB
    private SubscriptionsFacadeLocal subscriptionsEJB;

    @EJB
    private AsistenteFacadeLocal asistenciaEJB;

    @EJB
    private EventFacadeLocal eventEJB;

    private User user;

    private List<Subscriptions> listaSubscriptions;
    private List<Asistente> listaAsistencia;
    private List<Event> listaEventosSuscritos;
    private List<Event> listaEventosAsistente;

    /*@PostConstruct
     public void init() {
     user = userEJB.find(1);
     listaSubscriptions = subscriptionsEJB.findByIdUser(user);
     listaAsistencia = asistenciaEJB.findByIdUser(user);
     listaEventosSuscritos = new ArrayList<>();
     listaEventosAsistente = new ArrayList<>();
     for(Subscriptions s: listaSubscriptions)
     {
            
     listaEventosSuscritos.add(s.getId_event());
     }
        
     for(Asistente a: listaAsistencia)
     {
     listaEventosAsistente.add(a.getId_evento());
     }
     }*/
    public User getUser() {
        user = userEJB.find(1);
        getListaSubscriptions();
        getListaAsistencia();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Subscriptions> getListaSubscriptions() {
        listaSubscriptions = subscriptionsEJB.findByIdUser(user);
        return listaSubscriptions;
    }

    public void setListaSubscriptions(List<Subscriptions> listaSubscriptions) {
        this.listaSubscriptions = listaSubscriptions;
    }

    public List<Asistente> getListaAsistencia() {
        listaAsistencia = asistenciaEJB.findByIdUser(user);
        return listaAsistencia;
    }

    public void setListaAsistencia(List<Asistente> listaAsistencia) {
        this.listaAsistencia = listaAsistencia;
    }

    public List<Event> getListaEventosSuscritos() {
        listaEventosSuscritos = new ArrayList<>();

        for (Subscriptions s : listaSubscriptions) {

            listaEventosSuscritos.add(s.getId_event());
        }

        return listaEventosSuscritos;
    }

    public void setListaEventosSuscritos(List<Event> listaEventosSuscritos) {
        this.listaEventosSuscritos = listaEventosSuscritos;
    }

    public List<Event> getListaEventosAsistente() {

        listaEventosAsistente = new ArrayList<>();

        for (Asistente a : listaAsistencia) {
            listaEventosAsistente.add(a.getId_evento());
        }
        return listaEventosAsistente;
    }

    public void setListaEventosAsistente(List<Event> listaEventosAsistente) {
        this.listaEventosAsistente = listaEventosAsistente;
    }

}
