package com.iberifest.controlador;

import com.iberifest.EJB.EventFacadeLocal;
import com.iberifest.modelo.Event;
import com.iberifest.modelo.User;
import com.iberifest.util.IberiUtil;
import com.iberifest.util.SessionUtil;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@SessionScoped
public class EventCreationController implements Serializable {
    private static Logger logger = Logger.getLogger(EventCreationController.class);

    private Event evento;
    private User usuario;

    @EJB
    EventFacadeLocal eventFacade;

    @PostConstruct
    public void init() {
        evento = new Event();
        usuario = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);
         if (usuario == null){
             logger.info("Alguien se ha intentado colar en la creación de eventos " );

             try {
                 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
                FacesContext.getCurrentInstance().getExternalContext().redirect(IberiUtil.WELLCOME);


             } catch (IOException e) {
                e.printStackTrace();
            }
         }else{
             logger.info("El usuario " + usuario.getUsername() + " quiere crear eventos" );
         }


        //en un futuro se pueden añadir restricciones de creación de eventos basadas en roles


    }
    public void create(){
        Date register = new Date();
        evento.setCreation_date(register);

        eventFacade.create(evento);
        logger.info("El usuario " + usuario.getUsername() + " crea el evento: " + evento.getName());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha creado el evento", "usuario registrado correctamente"));


    }

    public Event getEvento() {
        return evento;
    }

    public void setEvento(Event evento) {
        this.evento = evento;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public EventFacadeLocal getEventFacade() {
        return eventFacade;
    }

    public void setEventFacade(EventFacadeLocal eventFacade) {
        this.eventFacade = eventFacade;
    }
}
