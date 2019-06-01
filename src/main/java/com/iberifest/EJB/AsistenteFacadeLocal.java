/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Asistente;
import com.iberifest.modelo.Event;
import com.iberifest.modelo.Subscriptions;
import com.iberifest.modelo.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author adolfo
 */
@Local
public interface AsistenteFacadeLocal {

    void create(Asistente asistente);

    void edit(Asistente asistente);

    void remove(Asistente asistente);

    Asistente find(Object id);

    List<Asistente> findAll();

    List<Asistente> findRange(int[] range);

    int count();
    
    List<Asistente> findByIdUser(User user);
    
    boolean existAsistencia(User user, Event event);
}
