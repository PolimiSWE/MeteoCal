/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.entity.EventStatus;
import meteocal.entity.EventType;
import meteocal.entity.Invitation;
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
            em.persist(evt.getIncludedInCalendar());
            em.flush();
        }
    }
    
    public void save(Event current, List<User> current_invited, User current_owner, String current_privacy, String current_type,String current_beginHour,String current_dateOfEvent) {
        Event tmp;
        this.preparePrivacy(current, current_privacy);
        this.prepareType(current, current_type);
        this.prepareInvitations(current, current_invited);
        this.prepareOwner(current, current_owner);
        this.prepareBeginHour(current, current_beginHour);
        this.prepareDateOfEvent(current, current_dateOfEvent);
        if(current.getId() != null) 
        {
            tmp = em.find(Event.class, (long)current.getId());
            if(tmp!=null)
            {
                em.merge(current);
                em.flush();
            }
        }
        else
        {
            em.persist(current);
            //em.merge(current.getIncludedInCalendar());
            em.flush();
        }
    }
    
    public void delete(int evtId) {
        Event evt;
        evt = em.find(Event.class, (long)evtId);
        if(evt != null)
        {
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

    private void preparePrivacy(Event current, String current_privacy){
        TypedQuery<PrivacyType> query = em.createQuery("SELECT pt FROM PrivacyType AS pt WHERE pt.privacy=:privacyParam", PrivacyType.class);
        Boolean privacyParam = false;
        if(current_privacy.equalsIgnoreCase("private"))
            privacyParam = false;
        if(current_privacy.equalsIgnoreCase("public"))
            privacyParam = true;
        query.setParameter("privacyParam", privacyParam);
        PrivacyType pt = query.getResultList().get(0);
        current.setEventPrivacy(pt);
    }
    
    private void prepareType(Event current, String current_type){
       TypedQuery<EventType> query = em.createQuery("SELECT et FROM EventType AS et WHERE et.type=:typeParam", EventType.class);
        Boolean typeParam = false;
        if(current_type.equalsIgnoreCase("outdoors"))
            typeParam = false;
        if(current_type.equalsIgnoreCase("indoors"))
            typeParam = true;
        query.setParameter("typeParam", typeParam);
        EventType et = query.getResultList().get(0);
        current.setEventType(et); 
    }
    
    private void prepareInvitations(Event current, List<User> current_invited){
        TypedQuery<EventStatus> query = em.createQuery("SELECT es FROM EventStatus AS es WHERE es.status=:statusParam", EventStatus.class);
        query.setParameter("statusParam", 0);
        EventStatus es = query.getResultList().get(0);
        Invitation inv;
        List<Invitation> invites = new ArrayList<>();
        for (Iterator<User> iter = current_invited.iterator(); iter.hasNext(); ) {
            inv = new Invitation();
            inv.setEvent(current);
            inv.setUser(iter.next());
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
    
    private void prepareBeginHour(Event current, String current_beginHour) {
        try{
            Time tmp = Time.valueOf(current_beginHour);
            current.setBeginHour(tmp);
        }
        catch(Exception e){
            Time tmp = Time.valueOf("12:00:00");
            current.setBeginHour(tmp);
        }
    }

    private void prepareDateOfEvent(Event current, String current_dateOfEvent) {
        try{
            Date tmp = Date.valueOf(current_dateOfEvent);
            current.setDateOfEvent(tmp);
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
}
