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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 * @author adolfo
 */
@Named
@ViewScoped
public class EventController implements Serializable {

    @EJB
    private EventFacadeLocal eventEJB;

    @EJB
    private UserFacadeLocal userEJB;

    private Event event;
    private User user;

    private List<Event> listaEventos;
    private List<User> listaUsuarios;

    private MapModel simpleModel;
    private LatLng coord;
    private HashMap<Integer, MapModel> hashMaps;

    @PostConstruct
    public void init() {
        event = new Event();
        user = new User();
        listaEventos = new ArrayList<>();
        listaUsuarios = new ArrayList<>();
        simpleModel = new DefaultMapModel();
        hashMaps = new HashMap<>();

    }

    public void searchEventByFilter() {
        FacesMessage message = null;
        System.out.println(user.getUsername());
        if (!user.getUsername().equals("")) {
            User userAux = userEJB.getByUserFullUserName(user);

            if (userAux != null) {

                listaEventos = eventEJB.getEventByName(event, userAux);

            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se ha encontrado dicho usuario: ", user.getUsername());
                listaEventos = new ArrayList<>();
            }

        } else {
            
            listaEventos = eventEJB.getEventByName(event, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        renderCoordinates();

    }

    public void renderCoordinates() {

        for (Event e : listaEventos) {
            String coordenadas[] = e.getCoordinates().split(",");
            String lat = coordenadas[0];
            String lng = coordenadas[1];
            double latD = Double.parseDouble(lat);
            double lngD = Double.parseDouble(lng);
            coord = new LatLng(latD, lngD);

            //Basic marker
            simpleModel = new DefaultMapModel();

            simpleModel.addOverlay(new Marker(coord, "Localizacion"));

            hashMaps.put(e.getId_event(), simpleModel);
        }

    }

    public LatLng getCoord() {
        return coord;
    }

    public void setCoord(LatLng coord) {
        this.coord = coord;
    }

    public HashMap<Integer, MapModel> getHashMaps() {
        return hashMaps;
    }

    public void setHashMaps(HashMap<Integer, MapModel> hashMaps) {
        this.hashMaps = hashMaps;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

    public List<Event> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<Event> listaEventos) {
        this.listaEventos = listaEventos;
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
