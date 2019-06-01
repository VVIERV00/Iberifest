/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Event;
import com.iberifest.modelo.Subscriptions;
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
public class SubscriptionsFacade extends AbstractFacade<Subscriptions> implements SubscriptionsFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubscriptionsFacade() {
        super(Subscriptions.class);
    }

    @Override
    public List<Subscriptions> findByIdUser(User user) {
        List<Subscriptions> lista;
        try {
            String consulta = "FROM Subscriptions s WHERE s.id_user = ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, user);

            lista = query.getResultList();
            if (!lista.isEmpty()) {
                System.out.println("encontre algo en findComentrid");

            }
        } catch (Exception e) {
            System.out.println("Error al obtener el modelo");
            System.err.println(e);
            return null;
        }

        return lista;

    }

    @Override
    public boolean existSubscription(User user, Event event) {

        List<Subscriptions> lista = new ArrayList<>();
        try {
            String consulta = "FROM Subscriptions s WHERE s.id_user = ?1 AND s.id_event = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, user);
            query.setParameter(2, event);

            lista = query.getResultList();
            if (!lista.isEmpty()) {
                System.out.println("encontre algo en findComentrid");
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el modelo");
            System.err.println(e);
            
        }

        
        return false;
    }

}
