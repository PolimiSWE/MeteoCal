/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import meteocal.boundary.CalendarFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.helper.CalendarHelper;
import meteocal.helper.DayHelper;
import meteocal.interfaces.CalendarBeanInterface;
import meteocal.interfaces.CommonBeanInterface;

/**
 *
 * @author Milos
 */
@Named(value = "calendarBean")
@SessionScoped
public class CalendarBean implements Serializable,CalendarBeanInterface {

    @EJB
    CalendarFacade cm;
    
    @Inject 
    CommonBeanInterface commonData;
    
    private Calendar current;
    private List<Calendar> dboutput;
    private List<Event> events;
    private CalendarHelper calHelper;
    
    
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
        this.calHelper = new CalendarHelper(new Date(System.currentTimeMillis()));
        List<DayHelper> daysOfweek = this.calHelper.getCurrentWeek();
        for(DayHelper day : daysOfweek){
            day.setTodaysEvents(this.commonData.getEventsForDay(day.getToday()));
        }
        this.calHelper.setCurrentWeek(daysOfweek);
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

    public CalendarHelper getCalHelper() {
        return calHelper;
    }

    public void setCalHelper(CalendarHelper calHelper) {
        this.calHelper = calHelper;
    }

    @Override
    public Calendar getCalendar() {
        return current;
    }

    @Override
    public void selectCalendar(Calendar cal) {
        this.current = cal;
    }
}
