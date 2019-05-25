/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Role;
import com.iberifest.modelo.User;
import com.iberifest.modelo.User_role;
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
public class User_roleFacade extends AbstractFacade<User_role> implements User_roleFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public User_roleFacade() {
        super(User_role.class);
    }
    
    @Override
    public List<User_role> findByUserId(User usuario)
    {
        List<User_role> listaUsuarios_rol = new ArrayList<>();
        
        try {
            String consulta = "FROM User_role ur WHERE ur.user.id_user LIKE ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, usuario.getId_user());
           
            listaUsuarios_rol = query.getResultList();
            if (!listaUsuarios_rol.isEmpty()) {
                System.out.println("encontre algo en finduserid");

            }
        } catch (Exception e) {
            System.out.println("Error al obtener el modelo");
            System.err.println(e);
        }        
        return listaUsuarios_rol;
    }
    
    @Override
    public User_role findByUserAndRole(User usuario, Role role){
        
        List<User_role> listaRoles = new ArrayList<>();
        User_role userRole = new User_role();
        
        try {
            String consulta = "FROM User_role u WHERE u.user = ?1 AND u.role = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, usuario);
            query.setParameter(2, role);
            listaRoles = query.getResultList();
            if (!listaRoles.isEmpty()) {
                System.out.println("encontre algo en FIND USER ROLE");
                userRole = listaRoles.get(0);
            }
        } catch (Exception e) {
            System.out.println("Error al obteenr el modelo");
            System.err.println(e);
        }        
        
        
        
        return userRole;
        
        
    }    
}
