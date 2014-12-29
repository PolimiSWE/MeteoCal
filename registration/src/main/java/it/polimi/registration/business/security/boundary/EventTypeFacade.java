/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import meteocal.entity.EventType;

/**
 *
 * @author Milos
 */
@Stateless
public class EventTypeFacade extends AbstractFacade<EventType> {
    
    @PersistenceContext(unitName = "authPU")
    private EntityManager em;

    public void save(EventType et) {
        EventType tmp = em.find(EventType.class, (long)et.getId());
        if(tmp != null) 
            em.merge(et);
        else
        {
            tmp = new EventType(et);
            em.persist(tmp);
        }
        
    }
    
    public void delete(int etId) {
        EventType et;
        et = em.find(EventType.class, (long)etId);
        if(et != null)
        {
            em.remove(et);
        }
    }

    public EventType getEventType(int etId) {
        EventType et;
        et = em.find(EventType.class, (long)etId);
        if(et != null)
            return et;
        else
            return new EventType();
    }

    public List<EventType> getDB_Table() {
        TypedQuery<EventType> query = em.createQuery("SELECT et FROM EventType AS et", EventType.class);
        return query.getResultList();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventTypeFacade() {
        super(EventType.class);
    }
    
}
