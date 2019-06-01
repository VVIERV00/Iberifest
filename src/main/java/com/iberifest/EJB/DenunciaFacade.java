/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Denuncia;
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
public class DenunciaFacade extends AbstractFacade<Denuncia> implements DenunciaFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DenunciaFacade() {
        super(Denuncia.class);
    }

    @Override
    public List<Denuncia> findNoResueltas() {

        List<Denuncia> lista = new ArrayList<>();

        try {
            String consulta = "FROM Denuncia d WHERE d.resuelta = ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, false);

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

}
