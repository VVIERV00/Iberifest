/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Comentario;
import com.iberifest.modelo.Event;
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
public class ComentarioFacade extends AbstractFacade<Comentario> implements ComentarioFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComentarioFacade() {
        super(Comentario.class);
    }

    @Override
    public List<Comentario> findByIdEvent(Event evento) {
        
        List<Comentario> listaComentarios;
        try {
            String consulta = "FROM Comentario c WHERE c.id_event = ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, evento);

            listaComentarios = query.getResultList();
            if (!listaComentarios.isEmpty()) {
                System.out.println("encontre algo en findComentrid");

            }
        } catch (Exception e) {
            System.out.println("Error al obtener el modelo");
            System.err.println(e);
            return null;
        }

        return listaComentarios;
    }

}
