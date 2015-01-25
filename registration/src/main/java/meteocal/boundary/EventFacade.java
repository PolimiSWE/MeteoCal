/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.entity.EventStatus;
import meteocal.entity.EventType;
import meteocal.entity.Invitation;
import meteocal.entity.Notification;
import meteocal.entity.PrivacyType;
import meteocal.entity.User;

/**
 *
 * @author Milos
 */
@Stateless
public class EventFacade extends AbstractFacade<Event> {
    @PersistenceContext(unitName = "authPU")
    private EntityManager em;

    public Event createNew(){
        Event tmp = new Event();
        return tmp;
    }
    
    public void save(Event evt) {
        Event tmp;
        if(evt.getId() != null) 
        {
            tmp = em.find(Event.class, (long)evt.getId());
            if(tmp!=null)
            {
                em.merge(evt);
                em.flush();
            }
        }
        else
        {
            em.persist(evt);
            em.flush();
        }
    }
    
    public void save(Event current, List<User> current_invited, User current_owner,
            boolean current_privacy, boolean current_type, Time current_input_beginHour,
            Date current_input_dateOfEvent) {
        Event tmp;
        
        if(current.getId() != null) 
        {
            tmp = em.find(Event.class, (long)current.getId());
            if(tmp!=null)
            {
                
                List<Invitation> old_invites = (List<Invitation>) tmp.getInvitations();
                //this.cleanUpInvites(old_invites);
                this.preparePrivacy(current, current_privacy);
                this.prepareType(current, current_type);
                this.prepareInvitations(current, current_invited);
                
                this.prepareECNotification(current, old_invites);
                
                this.prepareOwner(current, current_owner);
                this.prepareBeginHour(current, current_input_beginHour);
                this.prepareDateOfEvent(current, current_input_dateOfEvent);
                
                em.merge(current);
                em.flush();
                
                
                em.flush();
            }
        }
        else
        {
            this.preparePrivacy(current, current_privacy);
            this.prepareType(current, current_type);
            this.prepareInvitations(current, current_invited);
            this.prepareOwner(current, current_owner);
            this.prepareBeginHour(current, current_input_beginHour);
            this.prepareDateOfEvent(current, current_input_dateOfEvent);
            em.persist(current);
            em.flush();
        }
    }
    
    public void prepareECNotification(Event evt, List<Invitation> old_invites){
        List<Invitation> new_invitations = (List<Invitation>) evt.getInvitations();
        List<Invitation> CD_invites = new ArrayList<>(); // changed data notification
        List<Invitation> UI_invites = new ArrayList<>(); // uninvited notification
        Notification notf;
        for(Invitation inv: old_invites){
            if(this.isInvited(inv, new_invitations))
                CD_invites.add(inv);
            else
                UI_invites.add(inv);
        }
        for(Invitation inv: CD_invites){
           
                notf = new Notification();
                notf.setDescription("Event:" + evt.parse() 
                        + "Event details changed!"
                        + "Please check the details IF available.");
                notf.setOwner(inv.getUser());
                em.persist(notf);
            
        }
        for(Invitation inv: UI_invites){
           
                notf = new Notification();
                notf.setDescription("Event:" + evt.parse() 
                        + "Event details changed!"
                        + "You have been uninvited from the event.");
                notf.setOwner(inv.getUser());
                em.persist(notf);
            
        }
      //  em.flush();
    }
    
    public boolean isInvited(Invitation invite, List<Invitation> invites){
        boolean indicator=false;
        for(Invitation inv: invites)
            if(Objects.equals(inv.getUser().getId(), invite.getUser().getId())) {
                indicator = true;
            } else {
            }
        return indicator;
    }
    
    public void delete(int evtId) {
        Event evt;
        evt = em.find(Event.class, (long)evtId);
        if(evt != null)
        {
            this.prepareEDNotifications(evt);
            em.remove(evt);
        }
        em.flush();
    }

    public Event getEvent(int evtId) {
        Event evt;
        evt = em.find(Event.class, (long)evtId);
        if(evt != null)
            return evt;
        else
            return new Event();
    }

    public List<Event> getDB_Table() {
        TypedQuery<Event> query = em.createQuery("SELECT evt FROM Event AS evt", Event.class);
        return query.getResultList();
    }
 
    public User getUser(int usrId) {
        User usr;
        usr = em.find(User.class, (long)usrId);
        return usr;
    }

    private void preparePrivacy(Event current, boolean current_privacy){
        TypedQuery<PrivacyType> query = em.createQuery("SELECT pt FROM PrivacyType AS pt WHERE pt.privacy=:privacyParam", PrivacyType.class);
        query.setParameter("privacyParam", current_privacy);
        PrivacyType pt = query.getResultList().get(0);
        current.setEventPrivacy(pt);
    }
    
    private void prepareType(Event current, boolean current_type){
       TypedQuery<EventType> query = em.createQuery("SELECT et FROM EventType AS et WHERE et.type=:typeParam", EventType.class);
        query.setParameter("typeParam", current_type);
        EventType et = query.getResultList().get(0);
        current.setEventType(et); 
    }
    
    private void prepareInvitations(Event current, List<User> current_invited){
        TypedQuery<EventStatus> query = em.createQuery("SELECT es FROM EventStatus AS es WHERE es.status=:statusParam", EventStatus.class);
        query.setParameter("statusParam", 0);
        EventStatus es = query.getResultList().get(0);
        Invitation inv;
        List<Invitation> invites = new ArrayList<>();
        for (User u: current_invited ) {
            inv = new Invitation();
            inv.setEvent(current);
            inv.setUser(u);
            inv.setEventStatus(es);
            invites.add(inv);
        }
        current.setInvitations(invites);
    }
    
    private void prepareOwner(Event current,User current_owner)
    {
        Calendar tmp_cal = em.find(Calendar.class, (long)current_owner.getMyCalendar().getId());
        current.setIncludedInCalendar(tmp_cal);
    }
    
    private void prepareBeginHour(Event current, Time current_beginHour) {
        try{
            current.setBeginHour(current_beginHour);
        }
        catch(Exception e){
            Time tmp = Time.valueOf("12:00:00");
            current.setBeginHour(tmp);
        }
    }

    private void prepareDateOfEvent(Event current, Date current_dateOfEvent) {
        try{
            current.setDateOfEvent(current_dateOfEvent);
        }
        catch(Exception e){
            Date tmp = Date.valueOf("2020-12-12");
            current.setDateOfEvent(tmp);
        }
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public EventFacade(){
       super(Event.class);
    }

    public List<Event> findAll(Calendar cal) {
        TypedQuery<Event> query = em.createQuery("SELECT evt FROM Event AS evt WHERE evt.includedInCalendar.id=:calendarIdParam", Event.class);
        query.setParameter("calendarIdParam", cal.getId());
        return query.getResultList();
    }

    public List<Event> findDirtyEvents(Long id) {
        TypedQuery<Event> query = em.createQuery("SELECT evt FROM Event AS evt WHERE"
                + " evt.includedInCalendar.owner.id=:calendarIdParam AND evt.notifyOwner=:dirtyParam", Event.class);
        query.setParameter("calendarIdParam", id);
        query.setParameter("dirtyParam", true);
        return query.getResultList();
    }
    
    public List<Event> findDirtyEventsForTheDay(java.sql.Date dt) {
        TypedQuery<Event> query = em.createQuery("SELECT evt FROM Event AS evt WHERE"
                + " evt.dateOfEvent=:dateParam AND evt.notifyOwner=:dirtyParam", Event.class);
        query.setParameter("dateParam", dt, TemporalType.DATE);
        query.setParameter("dirtyParam", true);
        return query.getResultList();
    }
    
    public List<Event> findEventsForTheDay(java.sql.Date dt){//dateOfEvent, city, begin_hour, notifyOwner
        TypedQuery<Event> query = em.createQuery("SELECT evt FROM Event AS evt WHERE"
                + " evt.dateOfEvent=:dateParam", Event.class);
        query.setParameter("dateParam", dt, TemporalType.DATE);
        List<Event> result = query.getResultList();
        if(result!=null)
            return result;
        else
            return new ArrayList<>();
    }
    //
    public List<Event> findEventsForTheDayAndTheCity(String city, java.sql.Date dt){//dateOfEvent, city, begin_hour, notifyOwner
        TypedQuery<Event> query = em.createQuery("SELECT evt FROM Event AS evt WHERE"
                + " evt.dateOfEvent=:dateParam AND evt.city=:cityParam", Event.class);
        query.setParameter("dateParam", dt, TemporalType.DATE);
        query.setParameter("cityParam", city);
        List<Event> result = query.getResultList();
        if(result!=null)
            return result;
        else
            return new ArrayList<>();
    }

    private void prepareEDNotifications(Event evt) {
        List<Invitation> invites = (List<Invitation>) evt.getInvitations();
        Notification notf;
        for(Invitation inv: invites)
        {
                notf = new Notification();
                notf.setDescription("Event:" + evt.parse() 
                        + "Event Deleted by Owner!"
                       );
                notf.setOwner(inv.getUser());
                em.persist(notf);
        }
        em.flush();
    }

    private void cleanUpInvites(List<Invitation> old_invites) {
       for(Invitation inv: old_invites)
               em.remove(inv);
       em.flush();
    }

    public void updateDirty(Event evt) {
        evt.setNotifyOwner(false);
        em.merge(evt);
        em.flush();
    }
    
}
