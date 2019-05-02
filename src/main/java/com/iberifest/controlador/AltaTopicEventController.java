/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.controlador;

import com.iberifest.EJB.EventFacadeLocal;
import com.iberifest.EJB.TopicFacadeLocal;
import com.iberifest.EJB.Topics_EventFacadeLocal;
import com.iberifest.modelo.Event;
import com.iberifest.modelo.Topic;
import com.iberifest.modelo.Topics_Event;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author adolfo
 */

@Named
@ViewScoped
public class AltaTopicEventController implements Serializable {
    
    @EJB
    private Topics_EventFacadeLocal topicEventEJB;
    
    @EJB
    private EventFacadeLocal eventEJB;
    
    @EJB
    private TopicFacadeLocal topicEJB;
    
    private Topics_Event topicEvent;
    private Event event;
    private Topic topic;
    
    @PostConstruct
    public void init() {
        topicEvent = new Topics_Event();
        event = new Event();
        topic = new Topic();
        
    }
    
    public void createTopicEvent() {
        
        try {
            topicEventEJB.create(topicEvent);
            
        } catch (Exception e) {
            System.out.println("Error al crear evento");
            
        }
    }
    
}
