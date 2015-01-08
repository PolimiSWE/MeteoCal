/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.CalendarFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import meteocal.entity.Calendar;
import meteocal.entity.Event;

/**
 *
 * @author Milos
 */
@Named(value = "calendarBean")
@SessionScoped
public class CalendarBean implements Serializable {

    @EJB
    CalendarFacade cm;
    
    private Calendar current;
    private List<Calendar> dboutput;
    private List<Event> events;
    
    
    public CalendarBean() {
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        dboutput = cm.getDB_Table();
        if(this.current!= null)
            if(this.current.getId()!=null)
                events = cm.getCalendarEvents(this.current.getId());
        if(current == null) 
        {
            current = new Calendar();
        }
    }
    
    public void save() {
        cm.save(current);
        dboutput = cm.getDB_Table();
    }

    public void edit(int calId) { 
       current = cm.getCalendar(calId);
    }
    
    public void delete(int calId) {
        cm.delete(calId);
        dboutput = cm.getDB_Table();
    }
    
    public void updateCalendarEvents(){
        if(this.current!= null)
            if(this.current.getId()!= null)
                events = cm.getCalendarEvents(this.current.getId());
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

    public List<Calendar> getDboutput() {
        return dboutput;
    }

    public void setDboutput(List<Calendar> dboutput) {
        this.dboutput = dboutput;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
