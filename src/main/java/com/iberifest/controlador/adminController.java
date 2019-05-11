/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.iberifest.controlador;

import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.modelo.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;

/**
 *
 * @author adolfo
 */
@Named
@ViewScoped
public class adminController implements Serializable{
    
    private String printOnXhtml;
    
    
    @EJB
    private UserFacadeLocal userEJB;
    
    private User user;
    
    List<String> listaUsuarios;
    @PostConstruct
    public void init() {
        printOnXhtml = "";
        user = new User();
        listaUsuarios = new ArrayList<>();
        
        for (User u : userEJB.findAll()) {
            listaUsuarios.add(u.getUsername());
        }
        
    }
    
    public void getByUserName()
    {
        
        //System.out.println(user.getBirthday());
        List<User> listaUsuarios = userEJB.getUserByUsername(user);
        printOnXhtml = "";
        for(User u : listaUsuarios)
        {

            //System.out.println(user.getUsername());
            printOnXhtml += u.getUsername();

            

            user = new User();//Para resetear los campos de filtrado            
            
        }
        

    }
    
    public void changeRoleOfUser()
    {
        
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
    
    public String getPrintOnXhtml() {
        return printOnXhtml;
    }
    
    public void setPrintOnXhtml(String printOnXhtml) {
        this.printOnXhtml = printOnXhtml;
    }
    
    
    
    
}
