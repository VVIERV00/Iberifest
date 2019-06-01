package com.iberifest.util;

import com.iberifest.EJB.User_roleFacadeLocal;
import com.iberifest.modelo.User;
import com.iberifest.modelo.User_role;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class SessionUtil implements Serializable {
    public final static String USER_KEY = "usuario";
    @EJB
    private User_roleFacadeLocal userRoleEJB;

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(IberiUtil.WELLCOME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IberiUtil.WELLCOME;
    }
    public void add(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }
    public boolean isAdmin(){
        User user = (User) this.get(USER_KEY);
        if (user != null){
            boolean reallyIsAnAdmin = false;
            List<User_role> listaUsuariosRoles;

            listaUsuariosRoles = userRoleEJB.findByUserId(user);

            for (User_role userRole : listaUsuariosRoles) {
                if (userRole.getRole().getName().equals(RoleEnum.ADMIN.name())) {
                    reallyIsAnAdmin = true;
                }
            }
            return reallyIsAnAdmin;
        }else{
            return false;
        }
    }

    public Object get(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }
}