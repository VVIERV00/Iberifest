/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Event;
import com.iberifest.modelo.User;
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
    public List<Event> getEventByName(Event event) {

        Query query = null;
        String consulta;
        List<Event> listaEvents;

        try {
            //consulta = "FROM event e WHERE u.username LIKE ?1 AND u.email LIKE ?2 AND u.birthday = ?3 AND u.register_date BETWEEN ?4 AND ?5";
            consulta = "FROM Event e WHERE e.name LIKE ?1";
            query = em.createQuery(consulta);
            query.setParameter(1, event.getName() + "%");

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
