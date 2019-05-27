/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author adolfo
 */
@Stateless
public class RoleFacade extends AbstractFacade<Role> implements RoleFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleFacade() {
        super(Role.class);
    }

    @Override
    public Role getRoleById(int roleId) {
        return null;
    }
}
