/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.iberifest.controlador;

import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.modelo.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author adolfo
 */
@Named
@ViewScoped
public class adminController implements Serializable{
    
    @EJB
    private UserFacadeLocal userEJB;
    
    private User user;
    
    List<String> listaUsuarios;
    @PostConstruct
    public void init() {
        user = new User();
        listaUsuarios = new ArrayList<>();
        
        for (User u : userEJB.findAll()) {
            listaUsuarios.add(u.getUsername());
        }
    
    }
    
    

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<String> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    
    
    
}
