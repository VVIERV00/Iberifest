package com.iberifest.controlador;

import com.iberifest.EJB.UserFacadeLocal;
import com.iberifest.EJB.User_roleFacadeLocal;
import com.iberifest.modelo.User;
import com.iberifest.modelo.User_role;
import com.iberifest.util.SessionUtil;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@SessionScoped

public class IndexController implements Serializable {
    private static Logger logger = Logger.getLogger(IndexController.class);

    private User user;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private User_roleFacadeLocal userRoleEJB;

    //private SessionUtil session;

    private List<User_role> listaUsuariosRoles;


    //private User_role userRole;

    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear(); //borro la sesion si hubiera
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
            logger.info("Se procede a iniciar la sesion del usuario " + user.getUsername() + " con rol: ");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido ", user.getUsername());
            listaUsuariosRoles = userRoleEJB.findByUserId(comprobado);

            for (User_role userRole : listaUsuariosRoles) {

                if (userRole.getRole().getName().equals("ADMIN")) {
                    direccion = "/private/admin.xhtml";
                }
            }
            FacesContext.getCurrentInstance().getAttributes().put(SessionUtil.USER_KEY, comprobado);

            //session.add(SessionUtil.USER_KEY, comprobado);
        } else {
            logger.info("La contraseña o nombre de usuario (" + user.getUsername() + ") son incorrectos");
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en el logueo", "Credenciales incorrectas");
            direccion = "index.xhtml";
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        /*try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(direccion);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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


    public String moveToEvent() {
        return "event.xhtml";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
