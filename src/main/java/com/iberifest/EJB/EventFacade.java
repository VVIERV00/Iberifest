/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Event;
import com.iberifest.modelo.User;
import com.iberifest.EJB.UserFacade;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author adolfo
 */
@Stateless
public class EventFacade extends AbstractFacade<Event> implements EventFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventFacade() {
        super(Event.class);
    }

    @Override
    public List<Event> getEventByName(Event event, User user, String coordenadasOrigen, int maxDistancia) {

        Query query = null;
        String consulta;
        List<Event> listaEvents;
        
        if (maxDistancia == 0) maxDistancia = 20;
        
        try {
            //consulta = "FROM event e WHERE u.username LIKE ?1 AND u.email LIKE ?2 AND u.birthday = ?3 AND u.register_date BETWEEN ?4 AND ?5";
            consulta = "FROM Event e WHERE ?1 ?2 ?3 ?4";
            query = em.createQuery(consulta);
            
            query.setParameter(1, "e.name LIKE " + event.getName() + "%");
            
            if (user != null) query.setParameter(2, "AND e.user_iduser.id_user LIKE " + user.getId_user());
            else query.setParameter(2, "");
            
            if (event.getDate_start() != null) query.setParameter(3, "AND e.date_start LIKE " + event.getDate_start());
            else query.setParameter(3, "");
            
            /*
            if (event.getDate_start() != null) query.setParameter(4, "AND " + maxDistancia + " <= " + calcularDistancia(coordenadasOrigen, event.getCoordinates()));
            else query.setParameter(4, "");
            */  
            
            listaEvents = query.getResultList();

            if (!listaEvents.isEmpty()) {
                System.out.println("encontre algo en EVENT");
            }

        } catch (Exception e) {
            System.out.print(e);
            System.out.println("Error al obtener el modelo en getEVENT");
            List<Event> emptyList = new ArrayList<Event>();
            return emptyList;
        }
        //System.out.print(user);
        return listaEvents;

    }

}
