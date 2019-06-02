/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.controlador;

import com.iberifest.EJB.DenunciaFacadeLocal;
import com.iberifest.EJB.EventFacadeLocal;
import com.iberifest.EJB.RoleFacadeLocal;
import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.EJB.User_roleFacadeLocal;
import com.iberifest.modelo.Denuncia;
import com.iberifest.modelo.Event;
import com.iberifest.modelo.Role;
import com.iberifest.modelo.User;
import com.iberifest.modelo.User_role;
import com.iberifest.util.RoleEnum;
import com.iberifest.util.SessionUtil;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author adolfo
 */
@ManagedBean
@SessionScoped
public class AdminController implements Serializable {

    private static Logger logger = Logger.getLogger(RegisterController.class);

    @EJB
    private UserFacadeLocal userEJB;

    @EJB
    private User_roleFacadeLocal userRoleEJB;

    @EJB
    private RoleFacadeLocal roleEJB;

    @EJB
    private DenunciaFacadeLocal denunciaEJB;

    @EJB
    private EventFacadeLocal eventoEJB;

    // @Inject // inyectamos la dependencia
    // private SessionUtil session;
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
    HashMap<User, String[]> mapUserRoles;
    HashMap<User, String[]> mapUserRolesNew;
    String[] selectedRolesArr;
    String[] selectedRolesArrNew;
    List<Denuncia> denunciasSinResolver;
    List<Event> eventosSinVerificar;

    @PostConstruct
    public void init() {
        User checkLog = null;
        checkLog = (User) FacesContext.getCurrentInstance().getAttributes().get(SessionUtil.USER_KEY);
        if (checkLog != null) {
            List<User_role> listaRolesUserLogued = userRoleEJB.findByUserId(checkLog);
            boolean verificado = false;
            for (User_role userRole : listaRolesUserLogued) {

                if (userRole.getRole().getId() == RoleEnum.ADMIN.ordinal() + 1) {
                    verificado = true;
                }
            }

            if (!verificado) {
                logout();
            }
        } else {
            logout();
        }
        printOnXhtml = "";
        user = new User();
        userRole = new User_role();
        
        listaUsuarios = new ArrayList<>();
        rolesPorUsuario = new HashMap<Integer, List<User_role>>();
        listaUserRoles = new ArrayList<>();

        selectedRolesSet = new HashSet<>();
        selectedRolesSetNew = new HashSet<>();
        mapUserRoles = new HashMap<>();
        mapUserRolesNew = new HashMap<>();
        denunciasSinResolver = new ArrayList<>();
        eventosSinVerificar = new ArrayList<>();

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

    public String logout() {
        User admin = (User) FacesContext.getCurrentInstance().getAttributes().get(SessionUtil.USER_KEY);
        logger.info("El admin " + admin.getUsername() + " cierra la sesión");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().release();
        return "../public/index.xhtml";

    }

    public void deleteUser(User u) {
        User admin = (User) FacesContext.getCurrentInstance().getAttributes().get(SessionUtil.USER_KEY);
        logger.info("El admin " + admin.getUsername() + " procede a eliminar al usuario " + u.getUsername());
        userEJB.remove(u);
        //user = new User();
        getAllUsers();

    }

    public List<User_role> getRolOfUser(User u) {

        listaUserRoles = userRoleEJB.findByUserId(u);

   
        return listaUserRoles;

    }

    public HashMap<User, String[]> auxChange(User u) {
        getAllRoles();
        selectedRolesArr = new String[listaUserRoles.size()];
        selectedRolesArrNew = new String[allRoles.size()];

        for (int j = 0; j < listaUserRoles.size(); j++) {

            selectedRolesArr[j] = listaUserRoles.get(j).getRole().getName();
            selectedRolesArrNew[j] = listaUserRoles.get(j).getRole().getName();

        }

        mapUserRoles.put(u, selectedRolesArr);
        mapUserRolesNew.put(u, selectedRolesArrNew);

        return mapUserRolesNew;
    }

    public void changeRoleOfUser(User u) {

        /*System.out.println("OLD" + selectedRolesSet);
         System.out.println("NEW" + selectedRolesSetNew);*/
        //Set<T> mySet = new HashSet<>(Arrays.asList(someArray));
        selectedRolesSet = new HashSet<>(Arrays.asList(mapUserRoles.get(u)));
        selectedRolesSetNew = new HashSet<>(Arrays.asList(mapUserRolesNew.get(u)));

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
                for (String r : selectedRolesSet) {
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
    }

    public void resolverDenuncia(Denuncia denuncia) {

        denuncia.setResuelta(true);
        denunciaEJB.edit(denuncia);
    }

    public void verificarEvento(Event event) {
        event.setVerificado(true);
        eventoEJB.edit(event);
    }

    public HashMap<User, String[]> getMapUserRoles() {
        return mapUserRoles;
    }

    public void setMapUserRoles(HashMap<User, String[]> mapUserRoles) {
        this.mapUserRoles = mapUserRoles;
    }

    public HashMap<User, String[]> getMapUserRolesNew() {
        return mapUserRolesNew;
    }

    public void setMapUserRolesNew(HashMap<User, String[]> mapUserRolesNew) {
        this.mapUserRolesNew = mapUserRolesNew;
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
        allRoles = roleEJB.findAll();
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

    public List<Denuncia> getDenunciasSinResolver() {
        //denunciasSinResolver = denunciaEJB.findAll();
        denunciasSinResolver = denunciaEJB.findNoResueltas();
        return denunciasSinResolver;
    }

    public void setDenunciasSinResolver(List<Denuncia> denunciasSinResolver) {
        this.denunciasSinResolver = denunciasSinResolver;
    }

    public List<Event> getEventosSinVerificar() {
        eventosSinVerificar = eventoEJB.findNoVerificados();
        return eventosSinVerificar;
    }

    public void setEventosSinVerificar(List<Event> eventosSinVerificar) {
        this.eventosSinVerificar = eventosSinVerificar;
    }

}
