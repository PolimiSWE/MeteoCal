/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import java.io.IOException;
import meteocal.boundary.CalendarFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.helper.CalendarHelper;
import meteocal.helper.DayHelper;
import meteocal.interfaces.CalendarBeanInterface;
import meteocal.interfaces.CommonBeanInterface;
import meteocal.lazyviewbeans.DayHelperLazyView;
import meteocal.lazyviewbeans.InvitationLazyView;

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
    @Inject
    InvitationLazyView invitationsView;
    @Inject
    EventBean eventData;
    @Inject
    PublicCalendarsBean pubCalData;
    
    private Calendar current;
    private boolean current_privacy;
    private CalendarHelper calHelper;
    private Date current_date;
    
    
    public CalendarBean() {
        
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
    }
    
    public void save() {
        cm.save(current,current_privacy);
    }

    public void edit(int calId) { 
       current = cm.getCalendar(calId);
    }
    
    public void delete(int calId) {
        cm.delete(calId);
    }
    
    public void oneDayLess(){
        this.calHelper.moveByOneDayDown();
        this.populateCalHelper();
    }
    
    public void oneDayMore(){
        this.calHelper.moveByOneDayUp();
        this.populateCalHelper();
    }
    
    @Override
    public void populateInvitations(){
        this.commonData.populateInvitations();
        this.invitationsView.initInvitationDataModel(this.commonData.getAllInvites());
    }
    
    public void goToEditEventPage(Event evt){
        this.eventData.setCurrent(evt);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
            try {
                context.redirect("editEventPage.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void goToPublicCalendarsPage(){
        if(this.pubCalData.getCurrent().getOwner()!=null)
            this.dayHelperView.initDayHelperLazyDataModel(this.pubCalData.getCalHelper());
        else
            this.dayHelperView.initDayHelperLazyDataModel(new CalendarHelper(new Date(System.currentTimeMillis())));
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

    @Override
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

    public boolean isCurrent_privacy() {
        return current_privacy;
    }

    public void setCurrent_privacy(boolean current_privacy) {
        this.current_privacy = current_privacy;
    }
    
    @Override
    public void populateCalHelper(){
        List<DayHelper> daysOfweek = this.calHelper.getCurrentWeek();
        for(DayHelper day : daysOfweek){
            day.setTodaysEvents(this.commonData.getEventsForDay(day.getToday()));
        }
        this.calHelper.setCurrentWeek(daysOfweek);
        this.dayHelperView.initDayHelperLazyDataModel(calHelper);
    }
}
