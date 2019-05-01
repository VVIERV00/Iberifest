package com.iberifest.modelo;

import com.iberifest.modelo.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-01T12:29:30")
@StaticMetamodel(Event.class)
public class Event_ { 

    public static volatile SingularAttribute<Event, Date> date_start;
    public static volatile SingularAttribute<Event, User> user_iduser;
    public static volatile SingularAttribute<Event, String> name;
    public static volatile SingularAttribute<Event, String> coordinates;
    public static volatile SingularAttribute<Event, String> description;
    public static volatile SingularAttribute<Event, Date> end_start;
    public static volatile SingularAttribute<Event, String> location;
    public static volatile SingularAttribute<Event, Integer> id_event;
    public static volatile SingularAttribute<Event, Date> creation_date;

}