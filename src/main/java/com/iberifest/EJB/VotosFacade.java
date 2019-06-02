/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Comentario;
import com.iberifest.modelo.User;
import com.iberifest.modelo.Votos;
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
public class VotosFacade extends AbstractFacade<Votos> implements VotosFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VotosFacade() {
        super(Votos.class);
    }

    @Override
    public boolean existeVoto(Comentario comentario, User user) {
        List<Votos> lista;
        try {
            String consulta = "FROM Votos v WHERE v.id_comentario = ?1 AND v.id_user = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, comentario);
            query.setParameter(2, user);

            lista = query.getResultList();
            if (!lista.isEmpty()) {
                System.out.println("encontre algo en findComentrid");
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

    @Override
    public Votos findByComentAndUser(Comentario comentario, User user) {
        Votos votos = null;
        try {
            String consulta = "FROM Votos v WHERE v.id_comentario = ?1 AND v.id_user = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, comentario);
            query.setParameter(2, user);

            votos = (Votos)query.getSingleResult();

        } catch (Exception e) {
            System.out.println("Error al obtener el modelo");
            System.err.println(e);
            return null;

        }

        return votos;
    }
}
