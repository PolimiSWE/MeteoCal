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
import meteocal.entity.Calendar;

/**
 *
 * @author Milos
 */
@Stateless
public class CalendarFacade extends AbstractFacade<Calendar> {
    @PersistenceContext(unitName = "authPU")
    private EntityManager em;

    public List<Calendar> getDB_Table() {
        TypedQuery<Calendar> query = em.createQuery("SELECT cal FROM Calendar AS cal", Calendar.class);
        return query.getResultList();
    }
    
    public void save(Calendar cal) {
        Calendar tmp;
        if(cal.getId() != null) 
        {
            tmp = em.find(Calendar.class, (long)cal.getId());
            if(tmp!=null)
            {
                em.merge(cal);
                em.flush();
            }
        }
    }
    
    public void delete(int calId) {
        Calendar cal;
        cal = em.find(Calendar.class, (long)calId);
        if(cal != null)
        {
            em.remove(cal);
        }
        em.flush();
    }
    
    public Calendar getCalendar(int calId) {
        Calendar cal;
        cal = em.find(Calendar.class, (long)calId);
        if(cal != null)
            return cal;
        else
            return new Calendar();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CalendarFacade() {
        super(Calendar.class);
    }
    
}
