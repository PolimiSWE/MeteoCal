/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

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

    public EventType createNew() {
        EventType tmp = new EventType();
        em.persist(tmp);
        em.flush();
        return tmp;
    }

    public void save(EventType et) {
        EventType tmp;
        if (et.getId() != null) {
            tmp = em.find(EventType.class, (long) et.getId());
            if (tmp != null) {
                em.merge(et);
                em.flush();
            }
        } else {
            em.persist(et);
            em.flush();
        }

    }

    public void delete(int etId) {
        EventType et;
        et = em.find(EventType.class, (long) etId);
        if (et != null) {
            em.remove(et);
        }
        em.flush();
    }

    public EventType getEventType(int etId) {
        EventType et;
        et = em.find(EventType.class, (long) etId);
        if (et != null) {
            return et;
        } else {
            return new EventType();
        }
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
    
    @Override
    public List<EventType> findAll() {
        TypedQuery<EventType> query = em.createQuery("SELECT et FROM EventType AS et", EventType.class);
        return query.getResultList();
    }

    public EventType find(int id) {
        EventType et;
        et = em.find(EventType.class, (long) id);
        if (et != null) {
            return et;
        }
        else
            return null;
    }
}
