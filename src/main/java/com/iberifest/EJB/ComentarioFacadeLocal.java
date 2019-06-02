/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Comentario;
import com.iberifest.modelo.Event;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author adolfo
 */
@Local
public interface ComentarioFacadeLocal {

    void create(Comentario comentario);

    void edit(Comentario comentario);

    void remove(Comentario comentario);

    Comentario find(Object id);

    List<Comentario> findAll();

    List<Comentario> findRange(int[] range);

    int count();
    
    List<Comentario> findByIdEvent(Event evento);
    
}
