package com.iberifest.controlador;

import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.EJB.User_roleFacadeLocal;
import com.iberifest.modelo.User;
import com.iberifest.modelo.User_role;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class IndexController implements Serializable {
    private static Logger logger = Logger.getLogger(IndexController.class);


    private User user;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private User_roleFacadeLocal userRoleEJB;

    private List<User_role> listaUsuariosRoles;
    //private User_role userRole;

    @PostConstruct
    public void init() {
        user = new User();
        //role = roleFacade.getRoleById(RoleEnum.USER.ordinal());

    }

    public String login() {
        FacesMessage message = null;
        String direccion = "index.xhtml";
        User comprobado = checkUser();
        if (comprobado != null) {
            //TODO roles


            //guardo el usuario en la sesion
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", comprobado);
            logger.info("Se procede a iniciar la sesion del usuario " + user.getUsername() + " con rol: ");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido ", user.getUsername());
            listaUsuariosRoles = userRoleEJB.findByUserId(comprobado);

            for (User_role userRole : listaUsuariosRoles) {

                if (userRole.getRole().getName().equals("ADMIN")) {
                    direccion = "/private/admin.xhtml";
                }
            }
        } else {
            logger.info("La contraseña o nombre de usuario (" + user.getUsername() + ") son incorrectos");
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en el logueo", "Credenciales incorrectas");
            direccion = "index.xhtml";
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        return direccion;
    }

    public User checkUser() {
        User comprobado = null;
        try {
            comprobado = userFacade.getUser(user);

        } catch (Exception e) {
            logger.warn("Error al comprobar credenciales del usuario " + user.getUsername());
        }
        return comprobado;
    }

    public String moveToAdmin() {
        return "admin.xhtml";
    }

    public String moveToEvent() {
        return "event.xhtml";
    }

    /*public String checkUser() {
     String direccion = "";
     try {
     user = userFacade.getUser(user);
     if (user == null) {
     direccion = "publico/error.xhtml?autenticationFailure=true";
     System.out.println("error euthenticacion");
     } else {
     if (true) //TODO comprobar si es admin o no
     direccion = "privado/admin.xhtml";
     else
     direccion = "privado/iberifest.xhtml";
     //guardo el usuario en la sesion
     FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
     }
     } catch (Exception e) {
     System.out.println("Error al comprobar");
     }
     return direccion;
     }*/
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
