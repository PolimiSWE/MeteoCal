/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import meteocal.boundary.EventFacade;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.entity.User;
import meteocal.helper.DayHelper;

/**
 *
 * @author Nemanja
 */
@Named(value = "eventBean")
@SessionScoped
public class EventBean implements Serializable {
    
    @EJB
    EventFacade em;
    
    
    private Calendar current_calendar;
    private Event current;
    private String current_privacy;
    private String current_type;
    private String current_beginHour;
    private String current_dateOfEvent;
    private User current_owner;
    private List<User> current_invited;
    private List<Event> dboutput;

    /**
     * Creates a new instance of EventBean
     */
    public EventBean() {
    }

    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        dboutput = em.getDB_Table();
        if(current == null) 
        {
            current = new Event();
            current_invited = new ArrayList<>();
            current_calendar = new Calendar();
            current_owner = new User();
            current_privacy = "Private";
            current_type = "Outdoors";
            current_beginHour = "Enter Begin Hour";
            current_dateOfEvent = "Enter Date of the Event";
        }
    }
    
    public void createNew(){
        current = em.createNew();
        current.setIncludedInCalendar(current_calendar);
        current_invited = new ArrayList<>();
        current_owner = current_calendar.getOwner();
        current_privacy = "Private";
        current_type = "Outdoors";
        current_beginHour = "Enter Begin Hour";
        current_dateOfEvent = "Enter Date of the Event";
        dboutput = em.getDB_Table();
    }
    
    public void save() {
        Date currentTime = new Date(System.currentTimeMillis());
        current.setDateCreated(currentTime);
        em.save(current,current_invited,current_owner,current_privacy,current_type,current_beginHour,current_dateOfEvent);
        dboutput = em.getDB_Table();
        //return "user/eventTypeAdminPage?faces-redirect=true";
    }

    public void edit(int evtId) { 
       this.setCurrent(em.getEvent(evtId));
       //return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    public void delete(int evtId) {
        em.delete(evtId);
        current = new Event();
        current_invited = new ArrayList<>();
        current_owner = new User();
        current_privacy = "Private";
        current_type = "Outdoors";
        current_beginHour = "Enter Begin Hour";
        current_dateOfEvent = "Enter Date of the Event";
        dboutput = em.getDB_Table();
        //return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    public void setOwner(User usr){
        current_owner = usr;
    }
    
    public void addInvited(User usr){
        if(current_invited == null)
            current_invited = new ArrayList<>();
        current_invited.add(usr);
    }
    
    public void removeInvited(User usr){
        if(current_invited != null)
            current_invited.remove(usr);
    }
   
    public void selectCurrent(Event current) {
        this.current = current;
        this.current_owner = current.getOwner();
        this.current_invited = (List<User>) current.getInvited();
        if(current.getEventPrivacy().getPrivacy() == true)
            this.current_privacy = "Public";
        else 
            this.current_privacy = "Private";
        if(current.getEventType().getType() == true)
            this.current_privacy = "Indoors";
        else 
            this.current_privacy = "Outdoors";
        this.current_beginHour = current.getBeginHour().toString();
        this.current_dateOfEvent = current.getDateOfEvent().toString();
    }
    
   
    
    //Getters and Setters

    public EventFacade getEm() {
        return em;
    }

    public void setEm(EventFacade em) {
        this.em = em;
    }

    public Event getCurrent() {
        return current;
    }

    public void setCurrent(Event current) {
        this.current = current;
    }

    public List<Event> getDboutput() {
        return dboutput;
    }

    public void setDboutput(List<Event> dboutput) {
        this.dboutput = dboutput;
    }

    public String getCurrent_privacy() {
        return current_privacy;
    }

    public void setCurrent_privacy(String current_privacy) {
        this.current_privacy = current_privacy;
    }

    public String getCurrent_type() {
        return current_type;
    }

    public void setCurrent_type(String current_type) {
        this.current_type = current_type;
    }

    public User getCurrent_owner() {
        return current_owner;
    }

    public void setCurrent_owner(User current_owner) {
        this.current_owner = current_owner;
    }

    public List<User> getCurrent_invited() {
        return current_invited;
    }

    public void setCurrent_invited(List<User> current_invited) {
        this.current_invited = current_invited;
    }

    public String getCurrent_beginHour() {
        return current_beginHour;
    }

    public void setCurrent_beginHour(String current_beginHour) {
        this.current_beginHour = current_beginHour;
        try{
            Time tmp = Time.valueOf(current_beginHour);
            current.setBeginHour(tmp);
        }
        catch(Exception e){
            Time tmp = Time.valueOf("12:00:00");
            current.setBeginHour(tmp);
        }
            
    }

    public String getCurrent_dateOfEvent() {
        return current_dateOfEvent;
    }

    public void setCurrent_dateOfEvent(String current_dateOfEvent) {
        this.current_dateOfEvent = current_dateOfEvent;
        try{
            Date tmp = Date.valueOf(current_dateOfEvent);
            current.setDateOfEvent(tmp);
        }
        catch(Exception e){
            Date tmp = Date.valueOf("2020-12-12");
            current.setDateOfEvent(tmp);
        }
    }

    public Calendar getCurrent_calendar() {
        return current_calendar;
    }

    public void setCurrent_calendar(Calendar current_calendar) {
        this.current_calendar = current_calendar;
        this.current.setIncludedInCalendar(current_calendar);
    }
    
}
