package com.iberifest.controlador;


import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.modelo.User;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named
@ViewScoped
public class RegisterController implements Serializable {

    private User user = new User();

    @EJB
    private UserFacadeLocal userFacade;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void save() {
        user.setRegister_date(new Date());
        userFacade.create(user);
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getUsername());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
