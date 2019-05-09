/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Topics_Event;

import javax.ejb.Local;
import java.util.List;

/**
 * @author adolfo
 */
@Local
public interface Topics_EventFacadeLocal {

    void create(Topics_Event topics_Event);

    void edit(Topics_Event topics_Event);

    void remove(Topics_Event topics_Event);

    Topics_Event find(Object id);

    List<Topics_Event> findAll();

    List<Topics_Event> findRange(int[] range);

    int count();

}
