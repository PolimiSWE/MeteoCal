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
import meteocal.entity.EventStatus;

/**
 *
 * @author Milos
 */
@Stateless
public class EventStatusFacade extends AbstractFacade<EventStatus> {
    
    @PersistenceContext(unitName = "authPU")
    private EntityManager em;

    public EventStatus createNew(){
        EventStatus tmp = new EventStatus();
        em.persist(tmp);
        em.flush();
        return tmp;
    }
    
    public void save(EventStatus es) {
        EventStatus tmp = em.find(EventStatus.class, (long)es.getId());
        if(tmp != null) ;
            //em.merge(es);
        else
        {
            tmp = new EventStatus(es);
            em.persist(tmp);
        }
        em.flush();
    }
    
    public void delete(int esId) {
        EventStatus es;
        es = em.find(EventStatus.class, (long)esId);
        if(es != null)
        {
            em.remove(es);
        }
         em.flush();
    }

    public EventStatus getEventType(int esId) {
        EventStatus es;
        es = em.find(EventStatus.class, (long)esId);
        if(es != null)
            return es;
        else
            return new EventStatus();
    }

    public List<EventStatus> getDB_Table() {
        TypedQuery<EventStatus> query = em.createQuery("SELECT es FROM EventStatus AS es", EventStatus.class);
        return query.getResultList();
    }
    
    //Getters and Setters
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    public void setEntityManager(EntityManager em){
        this.em = em;
    }
    
    public EventStatusFacade() {
        super(EventStatus.class);
    }
    
}
