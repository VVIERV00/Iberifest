package com.iberifest.util;

import javax.faces.context.FacesContext;
import java.io.Serializable;


public class SessionUtil implements Serializable {
    public final static String USER_KEY = "usuario";

    public void add(String key, Object value) {
        FacesContext.getCurrentInstance().getAttributes().put(key, value);
    }

    public Object get(String key) {
        return FacesContext.getCurrentInstance().getAttributes().get(key);
    }
}