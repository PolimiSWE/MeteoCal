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
import meteocal.lazyviewbeans.DayHelperLazyView;

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
    
    @Inject
    DayHelperLazyView dayHelperView;
    
    private Calendar current;
    private CalendarHelper calHelper;
    
    
    public CalendarBean() {
        
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        if(current == null) 
        {
            current = new Calendar();
        }
        
    }
    
    public void save() {
        cm.save(current);
    }

    public void edit(int calId) { 
       current = cm.getCalendar(calId);
    }
    
    public void delete(int calId) {
        cm.delete(calId);
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

    @Override
    public Calendar getCalendar() {
        return current;
    }

    @Override
    public void selectCalendar(Calendar cal) {
        this.current = cal;
    }
    
    @Override
    public void populateCalHelper(){
        this.calHelper = new CalendarHelper(new Date(System.currentTimeMillis()));
        List<DayHelper> daysOfweek = this.calHelper.getCurrentWeek();
        for(DayHelper day : daysOfweek){
            day.setTodaysEvents(this.commonData.getEventsForDay(day.getToday()));
        }
        this.calHelper.setCurrentWeek(daysOfweek);
        this.dayHelperView.initDayHelperLazyDataModel(calHelper);
    }
}
