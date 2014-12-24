/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import java.security.Principal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import meteocal.entity.Event;

/**
 *
 * @author Nemanja
 */
@Stateless
public class EventManager {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    
    @Inject
    Principal principal;
    
    public void save(Event event){
        //modifications before saving with event.somesetter()
        em.persist(event);
    }
    public Event getEvent(Long id) {
        //we should set access control to the event here
        return em.find(Event.class, id);
        //declaration of find function:
        //public <T extends Object> T find(Class<T> entityClass, Object primaryKey);
    }
    public void deleteEvent(Long id){
        em.remove(getEvent(id));
    }
}
