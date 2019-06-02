package com.iberifest.controlador;

import com.iberifest.EJB.AsistenteFacadeLocal;
import com.iberifest.EJB.ComentarioFacadeLocal;
import com.iberifest.EJB.DenunciaFacadeLocal;
import com.iberifest.EJB.EventFacadeLocal;
import com.iberifest.EJB.SubscriptionsFacadeLocal;
import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.EJB.VotosFacadeLocal;
import com.iberifest.modelo.Asistente;
import com.iberifest.modelo.Comentario;
import com.iberifest.modelo.Denuncia;
import com.iberifest.modelo.Event;
import com.iberifest.modelo.Subscriptions;
import com.iberifest.modelo.User;
import com.iberifest.modelo.Votos;
import com.iberifest.util.SessionUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
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

    @EJB
    private DenunciaFacadeLocal denunciaEJB;

    @EJB
    private UserFacadeLocal usuarioEJB;

    @EJB
    private SubscriptionsFacadeLocal subscriptionsEJB;

    @EJB
    private AsistenteFacadeLocal asistenciaEJB;

    @EJB
    private VotosFacadeLocal votosEJB;

    private int idEvento;
    private Event evento;
    private List<Comentario> listaComentario;
    private DefaultMapModel modelMap;
    private Comentario comentarioDenuncia;
    private String descripcionDenuncia;
    private boolean subscripcion;
    private boolean asistencia;
    private String contenidoComentario;

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

    public void votarPositivo(Comentario comentario) {

        Votos votos = new Votos();
        int votosContador;
        User userLog = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);

        if (votosEJB.existeVoto(comentario, userLog)) {

            votos = votosEJB.findByComentAndUser(comentario, userLog);

            if (votos.getValue() == -1) {
                comentario.setVotos_down(comentario.getVotos_down() - 1);
                comentario.setVotos_up(comentario.getVotos_up() + 1);
                comentarioEJB.edit(comentario);

                votos.setValue(1);
                votosEJB.edit(votos);
                System.out.println("POSITIVO EXISTE");
            } else {

            }

        } else {
            System.out.println("POSITIVO NO EXISTE");
            votosContador = comentario.getVotos_up();
            votosContador += 1;
            comentario.setVotos_up(votosContador);
            comentarioEJB.edit(comentario);
            votos.setId_comentario(comentario);
            votos.setId_user(userLog);
            votos.setValue(1);
            votosEJB.create(votos);
        }

    }

    public void votarNegativo(Comentario comentario) {

        int votosContador;
        Votos votos = new Votos();
        User userLog = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);

        if (votosEJB.existeVoto(comentario, userLog)) {

            votos = votosEJB.findByComentAndUser(comentario, userLog);

            if (votos.getValue() == 1) {
                comentario.setVotos_down(comentario.getVotos_down() + 1);
                comentario.setVotos_up(comentario.getVotos_up() - 1);
                comentarioEJB.edit(comentario);

                votos.setValue(-1);
                votosEJB.edit(votos);
                System.out.println("NEGATIVO EXISTE");
            } else {
                //
            }

        } else {
            System.out.println("NEGATIVO NO EXISTE");
            votosContador = comentario.getVotos_down();
            votosContador += 1;
            comentario.setVotos_down(votosContador);
            comentarioEJB.edit(comentario);
            votos.setId_comentario(comentario);
            votos.setId_user(userLog);
            votos.setValue(-1);
            votosEJB.create(votos);
        }

    }

    public void denunciar(Event event, Comentario comentario) {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);
        Denuncia denuncia = new Denuncia();
        denuncia.setId_user(user);
        denuncia.setFecha(new Date());
        denuncia.setDescripcion(descripcionDenuncia);
        denuncia.setId_evento(evento);
        denuncia.setId_comentario(comentario);
        denunciaEJB.create(denuncia);

    }

    public void crearComentario()
    {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);
        Comentario comentario = new Comentario();
        comentario.setId_event(evento);
        comentario.setId_user(user);
        comentario.setTexto(contenidoComentario);
        comentario.setFecha(new Date());
        comentarioEJB.create(comentario);
        listaComentario = comentarioEJB.findByIdEvent(evento);
        contenidoComentario = "";
        
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

    public void subscripcionesUsuarios() {

        Subscriptions subsc = new Subscriptions();
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);
        subsc.setId_event(evento);
        subsc.setId_user(user);
        if (subscripcion) {

            subscriptionsEJB.create(subsc);
        } else {
            subscriptionsEJB.remove(subsc);
        }

    }

    public void asistenciaUsuarios() {

        Asistente asistente = new Asistente();
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);

        asistente.setId_evento(evento);
        asistente.setId_user(user);
        if (asistencia) {

            asistenciaEJB.create(asistente);
        } else {
            System.out.println("PORQUE NO ENTRAS?");
            asistenciaEJB.remove(asistente);
        }

    }

    public Comentario getComentarioDenuncia() {
        return comentarioDenuncia;
    }

    public void setComentarioDenuncia(Comentario comentarioDenuncia) {
        this.comentarioDenuncia = comentarioDenuncia;
        System.out.println("ASLMASLDMA" + this.comentarioDenuncia);
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

    public String getDescripcionDenuncia() {
        return descripcionDenuncia;
    }

    public void setDescripcionDenuncia(String descripcionDenuncia) {
        this.descripcionDenuncia = descripcionDenuncia;
    }

    public boolean isSubscripcion() {

        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);
        if (subscriptionsEJB.existSubscription(user, evento)) {
            System.out.println("EXISTEEEEEEEEEEEE");
            subscripcion = true;
        } else {
            System.out.println("NASTY");
            subscripcion = false;
        }
        return subscripcion;
    }

    public void setSubscripcion(boolean subscripcion) {
        this.subscripcion = subscripcion;
    }

    public boolean isAsistencia() {

        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SessionUtil.USER_KEY);
        if (asistenciaEJB.existAsistencia(user, evento)) {
            System.out.println("EXISTEEEEEEEEEEEE ASIS");
            asistencia = true;
        } else {
            System.out.println("NASTY ASIS");
            asistencia = false;
        }
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    public String getContenidoComentario() {
        return contenidoComentario;
    }

    public void setContenidoComentario(String contenidoComentario) {
        this.contenidoComentario = contenidoComentario;
    }

    
}
