/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Role;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author adolfo
 */
@Stateless
public class RoleFacade extends AbstractFacade<Role> implements RoleFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private static Logger logger = Logger.getLogger(RoleFacade.class);


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleFacade() {
        super(Role.class);
    }

    @Override
    public Role getRoleById(int roleId) {

        Query query = null;
        List<Role> roleById = null;
        try {
            String consulta = "SELECT r FROM Role r WHERE r.id = '" + roleId + "'";
            query = em.createQuery(consulta);

            roleById = query.getResultList();

            if (!roleById.isEmpty()) {
                logger.info("He encontrado el rol " + roleById.get(0).getName());
            } else{
                logger.error("No he encontrado el rol con id: " + roleId);
            }


        } catch (Exception e) {
            logger.error("ERROR buscando el rol con id: " + roleId +" "+e.toString());
            return null;
        }
        //System.out.print(user);
        return roleById.get(0);
    }
}
