/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.EventManager;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import meteocal.entity.Event;

/**
 *
 * @author Nemanja
 */
@Named(value = "EventBean")
@Dependent
public class EventBean {
    @EJB
    private EventManager eventManager;

    /**
     * Creates a new instance of EventBean
     */
    public EventBean() {
    }

    public Event getEvent(Long id) {
        return this.eventManager.getEvent(id);
    }

    public void createEvent(Event event) {
        this.eventManager.save(event);
    }
    
    
    
}
