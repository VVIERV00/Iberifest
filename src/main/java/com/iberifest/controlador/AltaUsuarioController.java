/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.controlador;

import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.modelo.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author adolfo
 */
@Named
@ViewScoped
public class AltaUsuarioController implements Serializable {

    @EJB
    private UserFacadeLocal userEJB;

    private User user;


    @PostConstruct
    public void init() {
        user = new User();
  
    }

    public void createUser() {
        try {
            //user.setBirthday(new Date(1997, 12, 7)); //Cambiar por calendario en la vista
            user.setRegister_date(new Date()); //se harcodea 
            userEJB.create(user);

        } catch (Exception e) {
            System.out.println("Error al crear usuario");
        }

    }
    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    

}
