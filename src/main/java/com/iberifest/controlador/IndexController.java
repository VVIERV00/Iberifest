package com.iberifest.controlador;

import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.modelo.User;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class IndexController implements Serializable {

    @Inject
    private User user;

    @EJB
    private UserFacadeLocal userFacade;

    public String checkUser() {
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
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
