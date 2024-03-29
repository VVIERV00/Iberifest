/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.controlador;

import com.iberifest.EJB.TopicFacadeLocal;
import com.iberifest.modelo.Topic;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 * @author adolfo
 */
@ManagedBean
@SessionScoped
public class AltaTopicController implements Serializable {

    @EJB
    private TopicFacadeLocal topicEJB;

    private Topic topic;

    @PostConstruct
    public void init() {
        topic = new Topic();
    }

    public void createTopic() {
        try {
            topicEJB.create(topic);
        } catch (Exception e) {

            System.out.println("Error al crear topic");
        }
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }


}
