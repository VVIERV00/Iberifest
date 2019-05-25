package com.iberifest.controlador;


import com.iberifest.EJB.RoleFacadeLocal;
import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.EJB.User_roleFacadeLocal;
import com.iberifest.modelo.Role;
import com.iberifest.modelo.User;
import com.iberifest.util.RoleEnum;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;

@ViewScoped
@ManagedBean(name = "registerController")
public class RegisterController implements Serializable {
    private static Logger logger = Logger.getLogger(RegisterController.class);
    private User user;
    private Role role;

    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private RoleFacadeLocal roleFacade;
    @EJB
    private User_roleFacadeLocal userRoleEJB;

    @PostConstruct
    public void init() {
        user = new User();
        //role = roleFacade.getRoleById(RoleEnum.USER.ordinal());

    }

    public void save() {
        if (!userFacade.userExistNick(user)) {
            if (!userFacade.userExistEmail(user)) {
                role = roleFacade.getRoleById(RoleEnum.USER.ordinal());
                Date register = new Date();
                user.setRegister_date(register);
                logger.info("Se procede a crear el usuario " + user.getUsername());
                userFacade.create(user);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "usuario registrado correctamente"));
                user = new User();
            } else {
                logger.info("No se ha podido crear el usuario " + user.getUsername() + " porque ya existe una entrada con ese email en base de datos");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal", "Ese Email ya está en uso"));
            }


        } else {
            logger.info("No se ha podido crear el usuario " + user.getUsername() + " porque ya existe una entrada con ese nick en base de datos");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal", "Ese Nick ya está en uso"));
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserFacadeLocal getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacadeLocal userFacade) {
        this.userFacade = userFacade;
    }

    public RoleFacadeLocal getRoleFacade() {
        return roleFacade;
    }

    public void setRoleFacade(RoleFacadeLocal roleFacade) {
        this.roleFacade = roleFacade;
    }
}
