/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Asistente;
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
public class AsistenteFacade extends AbstractFacade<Asistente> implements AsistenteFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsistenteFacade() {
        super(Asistente.class);
    }

    @Override
    public List<Asistente> findByIdUser(User user) {
        List<Asistente> lista;
        try {
            String consulta = "FROM Asistente a WHERE a.id_user= ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, user);

            lista = query.getResultList();
            if (!lista.isEmpty()) {
                System.out.println("encontre algo en ASISTENCIAid");

            }
        } catch (Exception e) {
            System.out.println("Error al obtener el modelo");
            System.err.println(e);
            return null;
        }

        return lista;

    }

    @Override
    public boolean existAsistencia(User user, Event event) {

        List<Subscriptions> lista = new ArrayList<>();
        try {
            String consulta = "FROM Asistente a WHERE a.id_user = ?1 AND a.id_evento = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, user);
            query.setParameter(2, event);

            lista = query.getResultList();
            if (!lista.isEmpty()) {
                System.out.println("encontre algo en EXISTE ASIS");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el modelo");
            System.err.println(e);

        }

        return false;
    }
}
