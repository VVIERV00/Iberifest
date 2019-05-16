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
import java.util.Map;
import java.util.Set;
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
public class AdminController implements Serializable {

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
    List<User_role> listaUserRoles;

    @PostConstruct
    public void init() {
        printOnXhtml = "";
        user = new User();
        userRole = new User_role();
        listaUsuarios = new ArrayList<>();
        rolesPorUsuario = new HashMap<Integer, List<User_role>>();
        listaUserRoles = new ArrayList<>();

    }

    public void getByUserName() {

        //System.out.println(user.getBirthday());
        listaUsuarios = userEJB.getUserByUsername(user);
        //listaUserRoles = new ArrayList<>();

        
        user = new User();//Para resetear los campos de filtrado            


    }
    
    public void getAllUsers()
    {
        listaUsuarios = userEJB.findAll();
    }
    
    public void deleteUser(User u)
    {
        userEJB.remove(u);
        //user = new User();
        getAllUsers();
        
    }

    public List<User_role> getRolOfUser(User u) {

        listaUserRoles = userRoleEJB.findByUserId(u);

        for (int i = 0; i < listaUserRoles.size(); i++) {
            System.out.println(listaUserRoles.get(i).getRole().getName());
        }

        return listaUserRoles;

    }
    
    public void getAllRoles()
    {
        
    }

    public void changeRoleOfUser() {

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

    public HashMap<Integer, List<User_role>> getRolesPorUsuario() {
        return rolesPorUsuario;
    }

    public void setRolesPorUsuario(HashMap<Integer, List<User_role>> rolesPorUsuario) {
        this.rolesPorUsuario = rolesPorUsuario;
    }

    public List<User_role> getListaUserRoles() {
        return listaUserRoles;
    }

    public void setListaUserRoles(List<User_role> listaUserRoles) {
        this.listaUserRoles = listaUserRoles;
    }

    
}
