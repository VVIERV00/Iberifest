package com.iberifest.controlador;

import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.modelo.User;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class IndexController implements Serializable {
    private static Logger logger = Logger.getLogger(IndexController.class);


    private User user;

    @EJB
    private UserFacadeLocal userFacade;

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
            direccion = "/private/admin.xhtml";
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
