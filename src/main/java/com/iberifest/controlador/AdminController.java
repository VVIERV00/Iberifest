/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.iberifest.controlador;

import com.iberifest.EJB.RoleFacadeLocal;
import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.EJB.User_roleFacadeLocal;
import com.iberifest.modelo.Role;
import com.iberifest.modelo.User;
import com.iberifest.modelo.User_role;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class AdminController implements Serializable{
    
    
    
    @EJB
    private UserFacadeLocal userEJB;
    
    @EJB
    private User_roleFacadeLocal userRoleEJB;
    
    @EJB
    private RoleFacadeLocal roleEJB;
    
    private User user;
    private User_role userRole;
    
    private List<User> listaUsuarios;
    private String printOnXhtml;
    // Create a HashMap object called capitalCities
    HashMap<Integer, List<User_role>> rolesPorUsuario;
    
    @PostConstruct
    public void init() {
        printOnXhtml = "";
        user = new User();
        userRole = new User_role();
        listaUsuarios = new ArrayList<>();
        rolesPorUsuario = new HashMap<Integer, List<User_role>>();
        
    }
    
    public void getByUserName()
    {
        
        //System.out.println(user.getBirthday());
        listaUsuarios = userEJB.getUserByUsername(user);
        List<User_role> listaUserRoles;
        Role role;
        
        printOnXhtml = "";
        for(User u : listaUsuarios)
        {

            //System.out.println(user.getUsername());
            printOnXhtml += u.getUsername();

            listaUserRoles = userRoleEJB.findByUserId(u);
            
           
            
            rolesPorUsuario.put(u.getId_user(), listaUserRoles);
            
            //role = rolesPorUsuario.get(u.getId_user()).get(0).getRole();
            
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
    
    public List<User> getListaUsuarios() {
        return listaUsuarios;
    }
    
    public void setListaUsuarios(List<User> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    public String getPrintOnXhtml() {
        return printOnXhtml;
    }
    
    public void setPrintOnXhtml(String printOnXhtml) {
        this.printOnXhtml = printOnXhtml;
    }

    public User_role getUserRole() {
        return userRole;
    }

    public void setUserRole(User_role userRole) {
        this.userRole = userRole;
    }
    
    
    
    
}
