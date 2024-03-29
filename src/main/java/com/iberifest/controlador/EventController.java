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
import com.iberifest.util.EventEnum;
import com.iberifest.util.IberiUtil;
import com.iberifest.util.SessionUtil;
import org.json.JSONObject;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.iberifest.util.JsonReader.readJsonFromUrl;
import javax.persistence.PostLoad;

/**
 * @author adolfo
 */
@ManagedBean
@SessionScoped
public class EventController implements Serializable {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EventController.class);

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

    private static String API_KEY = "";
    private double maxDistancia;
    private String coordenadasOrigenTexto;
    private String coordenadasOrigen;
    private Marker marker;

    @PostConstruct
    public void init() {
        event = new Event();
        User userLog = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);
        if (userLog == null) {
            logger.info("Alguien se ha intentado colar en la página principal");

            try {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
                FacesContext.getCurrentInstance().getExternalContext().redirect(IberiUtil.WELLCOME);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listaEventos = new ArrayList<>();
        listaUsuarios = new ArrayList<>();
        simpleModel = new DefaultMapModel();
        hashMaps = new HashMap<>();
        maxDistancia = 20.0;
        coordenadasOrigenTexto = "";
        coordenadasOrigen = "41.9792243,-6.0599661";
        user = new User();

    }

    public void searchEventByFilter() {
        FacesMessage message = null;

        coordenadasOrigen = getCoordinatesFromString(coordenadasOrigenTexto);
        System.out.println(maxDistancia + " " + coordenadasOrigenTexto + " " + coordenadasOrigen);

        if (!user.getUsername().equals("")) {
            User userAux = userEJB.getByUserFullUserName(user);

            if (userAux != null) {

                listaEventos = eventEJB.getEventByName(event, userAux, coordenadasOrigen, maxDistancia);
            } else {
                // message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se ha encontrado dicho usuario: ", user.getUsername());
                listaEventos = new ArrayList<>();
            }

        } else {

            listaEventos = eventEJB.getEventByName(event, null, coordenadasOrigen, maxDistancia);
        }
        //FacesContext.getCurrentInstance().addMessage(null, message);
        renderCoordinates();
 

    }

    public String getCoordinatesFromString(String address) {

        if (address.equals("")) {
            return null;
        }
        address = address.replace(" ", "+");
        JSONObject json;
        String lng, lat, coordenadas;

        try {
            json = readJsonFromUrl("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + API_KEY + "");
            lat = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            lng = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
            coordenadas = lat + "," + lng;
        } catch (Exception ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return coordenadas;
        //System.out.println(json.);
    }

    public String getAddressFromCoordinates(String coordinates) {

        JSONObject json;
        String lng, lat, coordenadas;
        String address = "";
        try {
            json = readJsonFromUrl("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + coordinates + "&key=" + API_KEY + "");
            //address = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            address = json.getJSONArray("results").getJSONObject(1).getString("formatted_address");
        } catch (Exception ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        System.out.println(address);
        return address;

    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        System.out.println("ASFKASKMF: " + marker.getData());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected", marker.getTitle()));
        //FacesContext.getCurrentInstance().getExternalContext().redirect("");
    }

    public static double calcularDistancia(String coordenadas1, String coordenadas2) {
        double distancia, radioTierra = 6371;
        double lat1, long1, lat2, long2;
        double sindLat, sindLong;
        double va1, va2;

        lat1 = Double.valueOf(coordenadas1.split(",")[0]);
        long1 = Double.valueOf(coordenadas1.split(",")[1]);
        lat2 = Double.valueOf(coordenadas2.split(",")[0]);
        long2 = Double.valueOf(coordenadas2.split(",")[1]);

        sindLat = Math.sin(Math.toRadians(lat2 - lat1) / 2);
        sindLong = Math.sin(Math.toRadians(long2 - long1) / 2);

        va1 = Math.pow(sindLat, 2) + Math.pow(sindLong, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        distancia = radioTierra * va2;

        return Math.ceil(distancia);
    }

    public void renderCoordinates() {
        simpleModel = new DefaultMapModel();
        for (Event e : listaEventos) {
            String[] coordenadas = e.getCoordinates().split(",");
            String lat = coordenadas[0];
            String lng = coordenadas[1];
            double latD = Double.parseDouble(lat);
            double lngD = Double.parseDouble(lng);
            coord = new LatLng(latD, lngD);

            //Basic marker
            simpleModel.addOverlay(new Marker(coord, e.getName(), e.getId_event()));

            //hashMaps.put(e.getId_event(), simpleModel);
        }

    }

    public void renderAllCoordinates() {
        simpleModel = new DefaultMapModel();

        List<Event> allEvents = eventEJB.findAll();
        for (Event e : allEvents) {

            String[] coordenadas = e.getCoordinates().split(",");
            String lat = coordenadas[0];
            String lng = coordenadas[1];
            double latD = Double.parseDouble(lat);
            double lngD = Double.parseDouble(lng);
            coord = new LatLng(latD, lngD);

            //Basic marker
            simpleModel.addOverlay(new Marker(coord, e.getName(), e.getId_event()));

            //hashMaps.put(e.getId_event(), simpleModel);
        }

    }

    String getMediaCoordinates(List<Event> events) {
        Double lng = 0.0, lat = 0.0;
        int x = 0;
        for (Event event : events) {
            x++;
            lat += Double.valueOf(event.getCoordinates().split(",")[0]);
            lng += Double.valueOf(event.getCoordinates().split(",")[1]);

        }

        lat = lat / x;
        lng = lng / x;

        return lat + "," + lng;
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();

        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Point Selected", "Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng()));
        String lat = String.valueOf(latlng.getLat());
        String lng = String.valueOf(latlng.getLng());
        String coordenadas = lat + "," + lng;
        coordenadasOrigenTexto = getAddressFromCoordinates(coordenadas);
    }

    public void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public void goEvent(int idEvento) {
        String direccion = "/private/eventView.xhtml";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(EventEnum.ID.name(), idEvento);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(direccion);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getCoordenadasOrigen() {
        coordenadasOrigen = "41.9792243,-6.0599661";
        if (!listaEventos.isEmpty()) {
            coordenadasOrigen = getMediaCoordinates(listaEventos);
        } else {
            renderAllCoordinates();
        }

        return coordenadasOrigen;
    }

    public void setCoordenadasOrigen(String coordenadasOrigen) {
        this.coordenadasOrigen = coordenadasOrigen;
    }

    public double getMaxDistancia() {
        return maxDistancia;
    }

    public void setMaxDistancia(double maxDistancia) {
        this.maxDistancia = maxDistancia;
    }

    public String getCoordenadasOrigenTexto() {
        return coordenadasOrigenTexto;
    }

    public void setCoordenadasOrigenTexto(String coordenadasOrigenTexto) {
        this.coordenadasOrigenTexto = coordenadasOrigenTexto;
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
