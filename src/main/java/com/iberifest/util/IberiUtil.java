package com.iberifest.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class IberiUtil implements Serializable {

    public static String WELLCOME = "/public/index.xhtml?faces-redirect=true";
    public static String REGISTER = "/public/register.xhtml";
    public static String ADMIN = "/private/admin.xhtml";
    public static String HOME = "/private/event.xhtml";
    public static String EVENT_CREATION = "/private/eventCreation.xhtml";
    public static String EVENT_VIEW = "/private/eventView.xhtml";
    public static String TEMPLATE = "/WEB-INF/templates/infamousPage.xhtml";
}
