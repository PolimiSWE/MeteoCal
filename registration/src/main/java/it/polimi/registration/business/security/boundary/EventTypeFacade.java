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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventTypeFacade() {
        super(EventType.class);
    }
    
    public void save(EventType et) {
        em.persist(et);
    }

    public List<EventType> getDB_Table() {
        TypedQuery<EventType> query = em.createQuery("SELECT et FROM EventType AS et", EventType.class);
        return query.getResultList();
    }
    
}
