package com.iberifest.controlador;

import com.iberifest.EJB.EventFacadeLocal;
import com.iberifest.modelo.Event;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class EventViewController implements Serializable {

    @EJB
    private EventFacadeLocal eventEJB;

    private int idEvento;
    private Event evento;

    

    public void searchEventDetails(int id) {
        
        evento = eventEJB.find(id);
        System.out.println(evento.getDescription());
    }

    public int getIdEvento() {
        int id;
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

}
