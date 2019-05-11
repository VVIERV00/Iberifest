/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    
    @Override
    public List<User> getUserByUsername(User usuario)
    {
        

        Query query = null;
        Date finRegister;
        Calendar cal; 
        String consulta;
        List<User> listaUsuarios;
        
        try {

 
                            
            if(usuario.getBirthday() != null && usuario.getRegister_date() != null)
            {
                cal = Calendar.getInstance();
                cal.setTime(usuario.getRegister_date()); // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, 23);
                cal.add(Calendar.MINUTE, 59);
                cal.add(Calendar.SECOND, 59);

                finRegister = cal.getTime();                
                
                consulta = "FROM User u WHERE u.username LIKE ?1 AND u.email LIKE ?2 AND u.birthday = ?3 AND u.register_date BETWEEN ?4 AND ?5";
                query = em.createQuery(consulta);
                query.setParameter(1, usuario.getUsername() + "%");
                query.setParameter(2, usuario.getEmail() + "%");        
                query.setParameter(3, usuario.getBirthday());
                query.setParameter(4, usuario.getRegister_date()); 
                query.setParameter(5, finRegister); 
                
            }else if(usuario.getBirthday() != null){
                consulta = "FROM User u WHERE u.username LIKE ?1 AND u.email LIKE ?2 AND u.birthday = ?3";
                query = em.createQuery(consulta);
                query.setParameter(1, usuario.getUsername() + "%");
                query.setParameter(2, usuario.getEmail() + "%");        
                query.setParameter(3, usuario.getBirthday());
                  
            }else if(usuario.getRegister_date() != null){
                cal = Calendar.getInstance();
                cal.setTime(usuario.getRegister_date()); // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, 23);
                cal.add(Calendar.MINUTE, 59);
                cal.add(Calendar.SECOND, 59);

                finRegister = cal.getTime();                
                
                consulta = "FROM User u WHERE u.username LIKE ?1 AND u.email LIKE ?2 AND u.register_date BETWEEN ?3 AND ?4";
                query = em.createQuery(consulta);
                query.setParameter(1, usuario.getUsername() + "%");
                query.setParameter(2, usuario.getEmail() + "%");        
                query.setParameter(3, usuario.getRegister_date()); 
                query.setParameter(4, finRegister);                 
                
            }else{
                
                consulta = "FROM User u WHERE u.username LIKE ?1 AND u.email LIKE ?2";
                query = em.createQuery(consulta);
                query.setParameter(1, usuario.getUsername() + "%");
                query.setParameter(2, usuario.getEmail() + "%");        

                
            }

            listaUsuarios = query.getResultList();
            
            if (!listaUsuarios.isEmpty()) {
                System.out.println("encontre algo");
            }            
            
            
        } catch (Exception e) {
            System.out.print(e);
            System.out.println("Error al obtener el modelo en getUserByUsername");
            List<User> emptyList = new ArrayList<User>();
            return emptyList;
        }
        //System.out.print(user);
        return listaUsuarios;
        
    }    
    
}
