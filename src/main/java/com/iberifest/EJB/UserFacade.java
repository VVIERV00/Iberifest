/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.User;

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
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_IberiFest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public boolean userExist(String nombreUsuario) {
        boolean existe = false;
        List<User> listaUsuarios;
        try {
            String consulta = "FROM Usuarios u WHERE u.user=?1";//no u.user=nombreUsuario
            Query query = em.createQuery(consulta);
            query.setParameter(1, nombreUsuario);
            listaUsuarios = query.getResultList();
            if (!listaUsuarios.isEmpty()) existe = true;
        } catch (Exception e) {
            System.out.print("Error al consultar la base de datos");
        }
        return existe;
    }

    @Override
    public User getUser(User usuario) {
        User user = null;
        List<User> listaUsuarios;
        try {
            String consulta = "FROM User u WHERE u.user = ?1 AND u.password = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, user.getUsername());
            query.setParameter(2, usuario.getPassword());
            listaUsuarios = query.getResultList();
            if (!listaUsuarios.isEmpty()) {
                System.out.println("encontre algo");
                user = listaUsuarios.get(0);
            }
        } catch (Exception e) {
            System.out.println("Error al obteenr el modelo");
        }
        return user;
    }
    
}
