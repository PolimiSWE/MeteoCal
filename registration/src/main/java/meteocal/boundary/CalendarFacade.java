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
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.entity.PrivacyType;

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
    
    public PrivacyType getPrivacyType(boolean pt){
        TypedQuery<PrivacyType> query = 
                em.createQuery("SELECT pt FROM PrivacyType AS pt WHERE pt.privacy=:ptParam", PrivacyType.class);
        query.setParameter("ptParam", pt);
        return query.getSingleResult();
    }
    
    public void save(Calendar cal, boolean cal_privacy) {
        Calendar tmp;
        if(cal.getId() != null) 
        {
            tmp = em.find(Calendar.class, (long)cal.getId());
            if(tmp!=null)
            {
                tmp.setCalendarPrivacy(this.getPrivacyType(cal_privacy));
                //em.merge(cal);
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
    
    public List<Event> getCalendarEvents(long calendarId) {
        TypedQuery<Event> query = em.createQuery("SELECT evt FROM Event AS evt WHERE evt.includedInCalendar.id=:calendarIdParam", Event.class);
        query.setParameter("calendarIdParam", calendarId);
        return query.getResultList();
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
