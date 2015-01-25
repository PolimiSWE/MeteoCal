/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import meteocal.boundary.CalendarFacade;
import meteocal.boundary.EventFacade;
import meteocal.boundary.InvitationFacade;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.entity.Invitation;
import meteocal.entity.User;
import meteocal.helper.CalendarHelper;
import meteocal.helper.DayHelper;
import meteocal.interfaces.CalendarBeanInterface;
import meteocal.interfaces.CommonBeanInterface;
import meteocal.lazyviewbeans.DayHelperLazyView;
import meteocal.lazyviewbeans.UserLazyView;

/**
 *
 * @author Milos
 */
@Named(value = "publicCalendarsBean")
@SessionScoped
public class PublicCalendarsBean implements Serializable {

    @EJB
    CalendarFacade cm;
    @EJB
    EventFacade em;
    @EJB
    InvitationFacade im;
    
    @Inject 
    CommonBeanInterface commonData;
    @Inject
    DayHelperLazyView dayHelperView;
    @Inject
    UserLazyView usersView;
    @Inject
    EventBean eventData;
    @Inject
    CalendarBeanInterface calendarData;
    
    private Calendar current;
    private List<Event> currentCalendarEvents;
    private List<Event> currentCalendarAttendingEvents;
    private CalendarHelper calHelper;
    private Date current_date;
    private List<Invitation> currentCalendarInvitations;
    
    
    public PublicCalendarsBean() {
        
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        if(current == null) 
        {
            current = new Calendar();
        }
        this.current_date = new Date(System.currentTimeMillis());
        this.calHelper = new CalendarHelper(this.current_date);
        this.currentCalendarAttendingEvents = new ArrayList<>();
        this.currentCalendarEvents = new ArrayList<>();
        this.currentCalendarInvitations = new ArrayList<>();
        //this.populateUsers();
    }
    
    public void oneDayLess(){
        this.calHelper.moveByOneDayDown();
        this.populateCalHelper();
    }
    
    public void oneDayMore(){
        this.calHelper.moveByOneDayUp();
        this.populateCalHelper();
    }
    
    public void populateUsers(){
        this.usersView.init();
    }
    
    public void goToEditEventPage(){
       
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
            try {
                context.redirect("editEventPage.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void update(){
        this.populateUsers();
        this.populateEvents();
        this.populateInvitations();
        this.populateCalHelper();
    }
    
    public void goToMyCalendarPage(){
        this.calendarData.update();
        this.dayHelperView.initDayHelperLazyDataModel(this.calendarData.getCalHelper());
    }
    
    public void populateCalHelper(){
        List<DayHelper> daysOfweek = this.calHelper.getCurrentWeek();
        for(DayHelper day : daysOfweek){
            day.setTodaysEvents(this.getEventsForDay(day.getToday()));
        }
        this.calHelper.setCurrentWeek(daysOfweek);
        this.dayHelperView.initDayHelperLazyDataModel(calHelper);
    }
    
    public void selectUser(User usr){
        this.current = usr.getMyCalendar();
        this.populateEvents();
        this.populateInvitations();
        this.populateCalHelper();
    }
    
    public void populateEvents() {
        this.currentCalendarEvents = em.findAll(this.current);
    }
    
    public void populateInvitations() {
        if(this.current!=null)
            if(this.current.getOwner()!=null){
                this.currentCalendarInvitations = im.findAll(this.current.getOwner());
                for(Invitation inv : this.currentCalendarInvitations){
                    if(inv.getEventStatus().getStatus()==1)
                        this.currentCalendarAttendingEvents.add(inv.getEvent());
                }
            }
    }
    
    public List<Event> getEventsForDay(java.util.Date day){
        List<Event> events = new ArrayList<>();
        java.sql.Date e_dateOfEvent;
        java.sql.Date day_date = new java.sql.Date(day.getTime());
        String e_DOF_string;
        String day_string = day_date.toString();
        for(Event e : this.currentCalendarEvents){
            e_dateOfEvent = new java.sql.Date(e.getDateOfEvent().getTime());
            e_DOF_string = e_dateOfEvent.toString();
            if(day_string.equals(e_DOF_string))
                events.add(e);
        }
        for(Event e : this.currentCalendarAttendingEvents){
            if(e.getDateOfEvent().compareTo(day)==0)
                events.add(e);
        }
        return events;
    }
    
    
    //Getters and Setters

    public CalendarFacade getCm() {
        return cm;
    }

    public void setCm(CalendarFacade cm) {
        this.cm = cm;
    }

    public Calendar getCurrent() {
        return current;
    }

    public void setCurrent(Calendar current) {
        this.current = current;
    }

    public CalendarHelper getCalHelper() {
        return calHelper;
    }

    public void setCalHelper(CalendarHelper calHelper) {
        this.calHelper = calHelper;
    }

    public Calendar getCalendar() {
        return current;
    }

    public void selectCalendar(Calendar cal) {
        this.current = cal;
    }

    public List<Event> getCurrentCalendarEvents() {
        return currentCalendarEvents;
    }

    public void setCurrentCalendarEvents(List<Event> currentCalendarEvents) {
        this.currentCalendarEvents = currentCalendarEvents;
    }

}


