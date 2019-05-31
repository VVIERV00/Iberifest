package com.iberifest.controlador;

import com.iberifest.EJB.ComentarioFacadeLocal;
import com.iberifest.EJB.EventFacadeLocal;
import com.iberifest.modelo.Comentario;
import com.iberifest.modelo.Event;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

@ManagedBean
@SessionScoped
public class EventViewController implements Serializable {

    @EJB
    private EventFacadeLocal eventEJB;

    @EJB
    private ComentarioFacadeLocal comentarioEJB;

    private int idEvento;
    private Event evento;
    private List<Comentario> listaComentario;
    private DefaultMapModel modelMap;
    
    public void searchEventDetails(int id) {

        evento = eventEJB.find(id);
        listaComentario = comentarioEJB.findByIdEvent(evento);
        modelMap = renderCoordinates();
    }

    public DefaultMapModel renderCoordinates() {
        DefaultMapModel simpleModel = new DefaultMapModel();

        String[] coordenadas = evento.getCoordinates().split(",");
        String lat = coordenadas[0];
        String lng = coordenadas[1];
        double latD = Double.parseDouble(lat);
        double lngD = Double.parseDouble(lng);
        LatLng coord = new LatLng(latD, lngD);

        //Basic marker
        simpleModel.addOverlay(new Marker(coord, evento.getName(), evento.getId_event()));

        return simpleModel;
    }

    public int getIdEvento() {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        String data = params.get("idEvento");
        idEvento = Integer.parseInt(data);
        searchEventDetails(idEvento);

        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public Event getEvento() {
        return evento;
    }

    public void setEvento(Event evento) {
        this.evento = evento;
    }

    public List<Comentario> getListaComentario() {
        return listaComentario;
    }

    public void setListaComentario(List<Comentario> listaComentario) {
        this.listaComentario = listaComentario;
    }

    public DefaultMapModel getModelMap() {
        return modelMap;
    }

    public void setModelMap(DefaultMapModel modelMap) {
        this.modelMap = modelMap;
    }
    
    

}
