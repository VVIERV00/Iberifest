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
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author adolfo
 */
@Named
@SessionScoped
public class EventController implements Serializable {

    @EJB
    private EventFacadeLocal eventEJB;

    @EJB
    private UserFacadeLocal userEJB;

    private Event event;
    private User user;
    private List<Event> listaEventos;
    private MapModel simpleModel;
    private LatLng coord;
    private HashMap<Integer, MapModel> hashMaps;

    @PostConstruct
    public void init() {
        event = new Event();
        user = new User();
        listaEventos = new ArrayList<>();
        simpleModel = new DefaultMapModel();
        hashMaps = new HashMap<>();

    }

    public void searchEventByFilter() {

        listaEventos = eventEJB.getEventByName(event);
        renderCoordinates();

    }

    public void renderCoordinates() {

        for (Event e : listaEventos) {
            String[] coordenadas = e.getCoordinates().split(",");
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
