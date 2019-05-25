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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
    private List<Role> allRoles;
    // Create a HashMap object called capitalCities
    HashMap<Integer, List<User_role>> rolesPorUsuario;
    List<User_role> listaUserRoles;

    Set<String> selectedRolesSet;
    Set<String> selectedRolesSetNew;

    @PostConstruct
    public void init() {
        printOnXhtml = "";
        user = new User();
        userRole = new User_role();
        allRoles = roleEJB.findAll();
        listaUsuarios = new ArrayList<>();
        rolesPorUsuario = new HashMap<Integer, List<User_role>>();
        listaUserRoles = new ArrayList<>();

        selectedRolesSet = new HashSet<>();
        selectedRolesSetNew = new HashSet<>();

    }

    public void getByUserName() {

        //System.out.println(user.getBirthday());
        listaUsuarios = userEJB.getUserByUsername(user);
        //listaUserRoles = new ArrayList<>();

        user = new User();//Para resetear los campos de filtrado            

    }

    public void getAllUsers() {
        listaUsuarios = userEJB.findAll();
    }

    public void deleteUser(User u) {
        userEJB.remove(u);
        //user = new User();
        getAllUsers();

    }

    public List<User_role> getRolOfUser(User u) {

        listaUserRoles = userRoleEJB.findByUserId(u);

        for (int i = 0; i < listaUserRoles.size(); i++) {

            // System.out.println(listaUserRoles.get(i).getRole().getName());
            selectedRolesSet.add(listaUserRoles.get(i).getRole().getName());
            selectedRolesSetNew.add(listaUserRoles.get(i).getRole().getName());
        }

        return listaUserRoles;

    }

    public void changeRoleOfUser(User u) {

        System.out.println("OLD" + selectedRolesSet);
        System.out.println("NEW" + selectedRolesSetNew);

        if (selectedRolesSetNew.size() >= selectedRolesSet.size()) {
            selectedRolesSetNew.removeAll(selectedRolesSet);
            if (!selectedRolesSetNew.isEmpty())//AÑADIR
            {
                System.out.println("AÑADIR" + selectedRolesSetNew);
                for (String r : selectedRolesSetNew) {

                    for (Role role : allRoles) {
                        if (role.getName().equals(r)) {
                            userRole.setUser(u);
                            userRole.setRole(role);
                            userRoleEJB.create(userRole);

                        }
                    }
                }
            } else {
                System.out.println("HACER NADA");
            }

        } else {

            selectedRolesSet.removeAll(selectedRolesSetNew);
            if (!selectedRolesSet.isEmpty())//BORRAR
            {
                System.out.println("BORRAR: " + selectedRolesSet);
                for(String r: selectedRolesSet)
                {
                    for (Role role : allRoles) {
                        if (role.getName().equals(r)) {
                            
                            userRole = userRoleEJB.findByUserAndRole(u, role);
                            userRoleEJB.remove(userRole);

                        }
                    }                    
                    
                }
            } else {
                System.out.println("HACER NADA");
            }
        }

        selectedRolesSet = new HashSet<>();
        selectedRolesSetNew = new HashSet<>();
    }

    public Set<String> getSelectedRolesSetNew() {
        return selectedRolesSetNew;
    }

    public void setSelectedRolesSetNew(Set<String> selectedRolesSetNew) {
        this.selectedRolesSetNew = selectedRolesSetNew;
    }

    public Set<String> getSelectedRolesSet() {
        return selectedRolesSet;
    }

    public void setSelectedRolesSet(Set<String> selectedRolesSet) {
        this.selectedRolesSet = selectedRolesSet;
    }

    public List<Role> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(List<Role> allRoles) {
        this.allRoles = allRoles;
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
