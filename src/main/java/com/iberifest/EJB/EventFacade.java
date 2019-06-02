/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.controlador.EventController;
import com.iberifest.modelo.Event;
import com.iberifest.modelo.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
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
    public List<Event> getEventByName(Event event, User user, String coordenadasOrigen, double maxDistancia) {

        Query query = null;
        String consulta;
        List<Event> listaEvents = new ArrayList<Event>(), listaEventsAux;

        if (maxDistancia == 0) {
            maxDistancia = 20;
        }

        try {
            //consulta = "FROM event e WHERE u.username LIKE ?1 AND u.email LIKE ?2 AND u.birthday = ?3 AND u.register_date BETWEEN ?4 AND ?5";
            consulta = "FROM Event e WHERE e.name LIKE ?1";
            if (user != null) {
                consulta += " AND e.user_iduser.id_user LIKE ?2 ";
            }
            if (event.getDate_start() != null) {
                consulta += " AND e.date_start LIKE ?3 ";
            }
            query = em.createQuery(consulta);

            query.setParameter(1, event.getName() + "%");

            if (user != null) {
                query.setParameter(2, user.getId_user());
            }
            if (event.getDate_start() != null) {
                query.setParameter(3, event.getDate_start());
            }

            listaEventsAux = query.getResultList();

            if (coordenadasOrigen != null) {
                for (Event e : listaEventsAux) {

                    if (maxDistancia >= EventController.calcularDistancia(coordenadasOrigen, e.getCoordinates())) {
                        listaEvents.add(e);
                    }
                }
            } else {
                listaEvents = listaEventsAux;
            }

            if (!listaEvents.isEmpty()) {
                System.out.println("encontre algo en EVENT");
            }

        } catch (Exception e) {
            System.out.print(e);
            System.out.println("Error al obtener el modelo en getEVENT");
            return listaEvents;
        }
        //System.out.print(user);
        return listaEvents;

    }

    @Override
    public List<Event> findNoVerificados() {

        List<Event> lista = new ArrayList<>();

        try {
            String consulta = "FROM Event e WHERE e.verificado = ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, false);

            lista = query.getResultList();
            if (!lista.isEmpty()) {
                System.out.println("encontre algo en EVENT NO VERIFICADOS");

            }
        } catch (Exception e) {
            System.out.println("Error al obtener el modelo");
            System.err.println(e);
            return null;
        }

        return lista;
    }

}
