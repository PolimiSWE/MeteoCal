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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import meteocal.entity.Event;
import meteocal.entity.Invitation;
import meteocal.entity.User;
import meteocal.interfaces.CalendarBeanInterface;
import meteocal.interfaces.CommonBeanInterface;
import meteocal.interfaces.UserBeanInterface;
import meteocal.lazyviewbeans.EventLazyView;
import meteocal.lazyviewbeans.UserPickListView;

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
    CommonBeanInterface commonData;
    @Inject
    UserBeanInterface userData;
    @Inject
    EventLazyView eventListData;
    @Inject
    UserPickListView userPickList;
    
    
    private Event current;
    private boolean current_privacy;
    private boolean current_type;
    private Time current_input_beginHour;
    private java.util.Date current_input_dateOfEvent;
    private List<User> current_invited;
    private java.util.Date currentBH_AsDateChosen;
    private List<User> all_users;
    private boolean validTime;

    /**
     * Creates a new instance of EventBean
     */
    public EventBean() {
        validTime = true;
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
        this.all_users = this.commonData.fetchAllUser(this.userData.getUser());
        this.current_invited = new ArrayList<>();
            
    }
    
    public void createNew(){
        current = em.createNew();
        current.setIncludedInCalendar(this.calendarData.getCalendar());
        current_invited = new ArrayList<>();
        current_privacy = false;
        current_type = false;
        current_input_beginHour = new Time(System.currentTimeMillis());
        current_input_dateOfEvent = new Date(System.currentTimeMillis());
        this.all_users = commonData.fetchAllUser(this.userData.getUser());
        this.userPickList.init();
    }
    
    public void save() {
        if(this.current_input_dateOfEvent.after(new Date(System.currentTimeMillis()))){
            if(this.current_input_beginHour.after(new java.util.Date(System.currentTimeMillis()))){
                Date currentTime = new Date(System.currentTimeMillis());
                current.setDateCreated(currentTime);
                this.current_input_beginHour = new Time(this.currentBH_AsDateChosen.getTime());
                this.prepareCurrentInvited();
                em.save(current,current_invited,this.userData.getUser(),current_privacy,current_type,
                    current_input_beginHour,new Date(this.current_input_dateOfEvent.getTime()));
                this.eventListData.init();
            }
            else{
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error!", "Current Time of Event in the past. Please choose valid time!"));
                this.validTime = false;
            }
            
        }
        else{
            FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error!", "Current Date of Event in the past. Please choose valid date!"));
                this.validTime = false;
        }
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
        currentBH_AsDateChosen = new Date(System.currentTimeMillis());
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
    
    private void prepareCurrentInvited() {
        this.current_invited = this.userPickList.getUsers().getTarget();        
    }
    
    public void divideUserLists(){
        List<User> tmp = new ArrayList<>();
        for(User u1: this.current_invited){
            for(User u2: this.all_users){
                if(u1.getId().equals(u2.getId()))
                    tmp.add(u2);
            }
        }
        for(User u: tmp)
            this.all_users.remove(u);
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
        this.currentBH_AsDateChosen = new java.util.Date(this.current_input_beginHour.getTime());
        this.current_input_dateOfEvent = new Date(current.getDateOfEvent().getTime());
        this.all_users = this.commonData.fetchAllUser(this.userData.getUser());
        this.current_invited = new ArrayList<>();
        for(Invitation inv : current.getInvitations())
            this.current_invited.add(inv.getUser());
        this.divideUserLists();
        this.userPickList.init();
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
        java.util.Date timeAsDateChosen = new java.util.Date();
        timeAsDateChosen.setTime(this.current_input_beginHour.getTime());
        return timeAsDateChosen;
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
        return new java.util.Date(current_input_dateOfEvent.getTime());
    }
    
    public void validateTime(ValueChangeEvent event) {
        if(event.getNewValue()!=null)
        {
            this.currentBH_AsDateChosen = (java.util.Date) event.getNewValue();
            this.validTime = true;
            if(currentBH_AsDateChosen.compareTo(new java.util.Date(System.currentTimeMillis()))<0){
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error!", "Current Time of Event in the past. Please choose valid time!"));
                this.validTime = false;
                
            }
        }
    }
    
    public void validateDate(ValueChangeEvent event) {
        if(event.getNewValue()!=null)
        {
            this.current_input_dateOfEvent = new Date(((java.util.Date)event.getNewValue()).getTime());
            current.setDateOfEvent(this.current_input_dateOfEvent);
            if(current.isPassed()){
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error!", "Current Date of Event in the past. Please choose valid date!"));
            }
        }
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


    public CalendarBeanInterface getCalendarData() {
        return calendarData;
    }

    public void setCalendarData(CalendarBeanInterface calendarData) {
        this.calendarData = calendarData;
    }


    public void setCurrent_input_beginHour(Time current_input_beginHour) {
        this.current_input_beginHour = current_input_beginHour;
    }

    public java.util.Date getCurrentBH_AsDateChosen() {
        return currentBH_AsDateChosen;
    }

    public void setCurrentBH_AsDateChosen(java.util.Date currentBH_AsDateChosen) {
        this.currentBH_AsDateChosen = currentBH_AsDateChosen;
        this.validTime = true;
        if(currentBH_AsDateChosen==null)    
            this.validTime = false;
    }

    public boolean isValidTime() {
        return validTime;
    }

    public void setValidTime(boolean validTime) {
        this.validTime = validTime;
    }
    
    

    public List<User> getAll_users() {
        return all_users;
    }

    public void setAll_users(List<User> all_users) {
        this.all_users = all_users;
    }

   
    
    
    
}
