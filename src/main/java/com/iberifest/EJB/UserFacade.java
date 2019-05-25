/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.User;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author adolfo
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    private static Logger logger = Logger.getLogger(UserFacade.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public boolean userExistEmail(User usuario) {
        List<User> listaUsuarios;
        boolean condicion = false;

        try {
            String consulta = "SELECT u FROM User u WHERE u.email = '" + usuario.getEmail() + "'";//no u.user=nombreUsuario
            //Query query = getEntityManager().createQuery(consulta);
            //query.setParameter("nombre" , nombreUsuario);
            listaUsuarios = getEntityManager().createQuery(consulta).getResultList();
            System.out.println("IMPRIMIR " + listaUsuarios);
            logger.info("Intento comprobar si existe usuario con email: " + usuario.getEmail() + " y el resultado de la query es " + listaUsuarios.toString());
            if (!listaUsuarios.isEmpty()) {
                condicion = true;
            }
        } catch (Exception e) {
            System.out.print("Error al consultar la base de datos ");
            e.printStackTrace();
        }
        return condicion;
    }

    @Override
    public boolean userExistNick(User usuario) {
        List<User> listaUsuarios;
        boolean condicion = false;

        try {
            String consulta = "SELECT u FROM User u WHERE u.username = '" + usuario.getUsername() + "'";//no u.user=nombreUsuario
            //Query query = getEntityManager().createQuery(consulta);
            //query.setParameter("nombre" , nombreUsuario);
            listaUsuarios = getEntityManager().createQuery(consulta).getResultList();
            System.out.println("IMPRIMIR " + listaUsuarios);
            logger.info("Intento comprobar si existe usuario con nick: " + usuario.getUsername() + " y el resultado de la query es " + listaUsuarios.toString());
            if (!listaUsuarios.isEmpty()) {
                condicion = true;
            }
        } catch (Exception e) {
            System.out.print("Error al consultar la base de datos ");
            e.printStackTrace();
        }
        return condicion;
    }

    @Override
    public User getUser(User usuario) {
        User user = null;
        List<User> listaUsuarios;
        try {
            String consulta = "SELECT u FROM User u WHERE u.username = '" + usuario.getUsername() + "' AND u.password = '" + usuario.getPassword() + "' ";
            Query query = em.createQuery(consulta);
            // query.setParameter(1, usuario.getUsername());
            //query.setParameter(2, usuario.getPassword());
            listaUsuarios = query.getResultList();
            if (!listaUsuarios.isEmpty()) {
                logger.info("He encontrado un usuario con las credenciales dadas");
                user = listaUsuarios.get(0);
            }
        } catch (Exception e) {
            logger.error("Error al rescatar usuario de base de datos, credenciales incorrectas");
        }
        return user;
    }

    @Override
    public List<User> getUserByUsername(User usuario) {


        Query query = null;
        Date finRegister;
        Calendar cal;
        String consulta;
        List<User> listaUsuarios;

        try {


            if (usuario.getBirthday() != null && usuario.getRegister_date() != null) {
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

            } else if (usuario.getBirthday() != null) {
                consulta = "FROM User u WHERE u.username LIKE ?1 AND u.email LIKE ?2 AND u.birthday = ?3";
                query = em.createQuery(consulta);
                query.setParameter(1, usuario.getUsername() + "%");
                query.setParameter(2, usuario.getEmail() + "%");
                query.setParameter(3, usuario.getBirthday());

            } else if (usuario.getRegister_date() != null) {
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

            } else {

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