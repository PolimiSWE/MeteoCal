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
import meteocal.entity.Event;
import meteocal.entity.Invitation;
import meteocal.entity.User;
import meteocal.interfaces.CalendarBeanInterface;
import meteocal.interfaces.UserBeanInterface;

/**
 *
 * @author Nemanja
 */
@Named(value = "eventBean")
@SessionScoped
public class EventBean implements Serializable {
    
    @EJB
    EventFacade em;
    
    @Inject
    CalendarBeanInterface  calendarData;
    
    @Inject
    UserBeanInterface userData;
    
    private Event current;
    private boolean current_privacy;
    private boolean current_type;
    private Time current_input_beginHour;
    private Date current_input_dateOfEvent;
    private List<User> current_invited;
    private String[] current_invited_string;

    /**
     * Creates a new instance of EventBean
     */
    public EventBean() {
    }

    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        if(current == null) 
        {
            current = new Event();
            current_invited = new ArrayList<>();
            current_privacy = false;
            current_type = false;
            current_input_beginHour = new Time(System.currentTimeMillis());
            current_input_dateOfEvent = new Date(System.currentTimeMillis());
        }
    }
    
    public void createNew(){
        current = em.createNew();
        current.setIncludedInCalendar(this.calendarData.getCalendar());
        current_invited = new ArrayList<>();
        current_privacy = false;
        current_type = false;
        current_input_beginHour = new Time(System.currentTimeMillis());
        current_input_dateOfEvent = new Date(System.currentTimeMillis());
    }
    
    public void save() {
        Date currentTime = new Date(System.currentTimeMillis());
        current.setDateCreated(currentTime);
        this.prepareCurrentInvited();
        em.save(current,current_invited,this.userData.getUser(),current_privacy,current_type,
                current_input_beginHour,current_input_dateOfEvent);
        //this.calendarData.updateCalendarEvents();
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
        current_privacy = false;
        current_type = false;
        current_input_beginHour = new Time(System.currentTimeMillis());
        current_input_dateOfEvent = new Date(System.currentTimeMillis());
        //return "privacyTypeAdminPage?faces-redirect=true";
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
        this.current_invited = (List<User>) current.getInvited();
        this.current_privacy = current.getEventPrivacy().getPrivacy();
        this.current_type = current.getEventType().getType();
        this.current_input_beginHour = current.getBeginHour();
        this.current_input_dateOfEvent = new Date(current.getDateOfEvent().getTime());
    }
    
    private void prepareCurrentInvited() {
        this.current_invited = new ArrayList<>();
        if(this.current_invited_string!=null){
            int arrayLength = this.current_invited_string.length;
            for(int i=0; i<arrayLength; i++){
                this.current_invited.add(this.userData.getUserByUsername(this.current_invited_string[i]));
            } 
        }
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
        this.current_input_beginHour = current.getBeginHour();
        this.current_input_dateOfEvent = new Date(current.getDateOfEvent().getTime());
        this.current_invited = new ArrayList<>();
        this.current_invited_string = new String[current.getInvitations().size()];
        int i=0;
        for(Invitation inv : current.getInvitations()){
            this.current_invited.add(inv.getUser());
            this.current_invited_string[i] = inv.getUser().getUsername();
            i++;
        }
        this.current_privacy = current.getEventPrivacy().getPrivacy();
        this.current_type = current.getEventType().getType();
    }

    public boolean getCurrent_privacy() {
        return current_privacy;
    }

    public void setCurrent_privacy(boolean current_privacy) {
        this.current_privacy = current_privacy;
    }

    public boolean getCurrent_type() {
        return current_type;
    }

    public void setCurrent_type(boolean current_type) {
        this.current_type = current_type;
    }

    public List<User> getCurrent_invited() {
        return current_invited;
    }

    public void setCurrent_invited(List<User> current_invited) {
        this.current_invited = current_invited;
    }

    public java.util.Date getCurrent_input_beginHour() {
        return current_input_beginHour;
    }

    public void setCurrent_input_beginHour(java.util.Date current_beginHour) {
        try{
            this.current_input_beginHour = new Time(current_beginHour.getTime());
            current.setBeginHour(this.current_input_beginHour);
        }
        catch(Exception e){
            Time tmp = Time.valueOf("12:00:00");
            current.setBeginHour(tmp);
        }
            
    }

    public java.util.Date getCurrent_input_dateOfEvent() {
        return current_input_dateOfEvent;
    }

    public void setCurrent_input_dateOfEvent(java.util.Date current_dateOfEvent) {
        try{
            this.current_input_dateOfEvent = new Date(current_dateOfEvent.getTime());
            current.setDateOfEvent(this.current_input_dateOfEvent);
        }
        catch(Exception e){
            Date tmp = Date.valueOf("2020-12-12");
            current.setDateOfEvent(tmp);
        }
    }

    public String[] getCurrent_invited_string() {
        return current_invited_string;
    }

    public void setCurrent_invited_string(String[] current_invited_string) {
        this.current_invited_string = current_invited_string;
    }
    
    
    
}
