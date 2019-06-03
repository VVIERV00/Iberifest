package com.iberifest.controlador;

import com.iberifest.EJB.EventFacadeLocal;
import com.iberifest.modelo.Event;
import com.iberifest.modelo.User;
import com.iberifest.util.IberiUtil;
import com.iberifest.util.SessionUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;
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
import java.util.Date;

import static com.iberifest.util.JsonReader.readJsonFromUrl;
import java.util.logging.Level;
import javax.faces.context.ExternalContext;

@ManagedBean
@SessionScoped
public class EventCreationController implements Serializable {
    private static Logger logger = Logger.getLogger(EventCreationController.class);
    @EJB
    EventFacadeLocal eventFacade;


    private Event evento;
    private User usuario;
    private String coordenadasEvento;
    private String coordenadasEventoTexto;
    private MapModel simpleModel;
    private Marker marker;
    private static String API_KEY = "";

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

        simpleModel = new DefaultMapModel();
        coordenadasEvento = "";
        //en un futuro se pueden añadir restricciones de creación de eventos basadas en roles


    }



    public void create(){
        Date register = new Date();
        EventController ev = new EventController();
        System.out.println("NONONONONONNONOONNOONON "+evento.getLocation());
        String coordenadas = ev.getCoordinatesFromString(evento.getLocation());
        System.out.println("SDJASDKASLDKJASDKJASKJLASKDLJ "+coordenadas);
        evento.setCoordinates(coordenadas);
        evento.setCreation_date(register);
        
        evento.setUser_iduser(usuario); //que es esto?
        eventFacade.create(evento);
        logger.info("El usuario " + usuario.getUsername() + " crea el evento: " + evento.getName());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha creado el evento", "evento registrado correctamente"));
         evento = new Event();
    
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

    public String getCoordenadasEvento() {
        return coordenadasEvento;
    }

    public void setCoordenadasEvento(String coordenadasEvento) {
        this.coordenadasEvento = coordenadasEvento;
    }

    public String getCoordenadasEventoTexto() {
        return coordenadasEventoTexto;
    }

    public void setCoordenadasEventoTexto(String coordenadasEventoTexto) {
        this.coordenadasEventoTexto = coordenadasEventoTexto;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }
}
