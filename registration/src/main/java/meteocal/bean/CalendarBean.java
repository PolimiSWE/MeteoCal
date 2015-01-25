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
import meteocal.entity.Invitation;
import meteocal.entity.User;
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
        this.pubCalData.getCm();
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
    
    public void deleteEvent(Event evt, User usr){
        User u = evt.getIncludedInCalendar().getOwner(); // add notf deleted
        if(u.getId().equals(this.current.getOwner().getId())){
             this.commonData.deleteEvent(evt);
             this.commonData.populateEvents();
             this.populateCalHelper();
        }
        else{
            this.commonData.declineInvite(evt, usr);
            this.commonData.populateInvitations();
            this.populateCalHelper();
        }
    }
    
    public void acceptInvite(Invitation inv){
        this.commonData.acceptInvite(inv);
        this.commonData.populateInvitations();
        this.invitationsView.update(this.current.getOwner().getId());
        this.populateCalHelper();
    }
    
    public void declineInvite(Invitation inv){
        this.commonData.declineInvite(inv);
        this.commonData.populateInvitations();
        this.invitationsView.update(this.current.getOwner().getId());
        this.populateCalHelper();
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
    
    @Override
    public void update() {
        this.commonData.update();
        this.populateInvitations();
        this.populateCalHelper();
        this.dayHelperView.initDayHelperLazyDataModel(calHelper);
    }
    
    public void goToPublicCalendarsPage(){
        if(this.pubCalData.getCurrent().getOwner()!=null){
            this.pubCalData.update();
            this.dayHelperView.initDayHelperLazyDataModel(this.pubCalData.getCalHelper());
            this.pubCalData.usersView.init();
        }
        else
            this.dayHelperView.initDayHelperLazyDataModel(new CalendarHelper(new Date(System.currentTimeMillis())));
        this.pubCalData.populateUsers();
    }
    
    public void goToMyCalendarPage(){
        this.commonData.update();
        this.populateInvitations();
        this.populateCalHelper();
        this.dayHelperView.initDayHelperLazyDataModel(this.calHelper);
        this.invitationsView.initInvitationDataModel(this.commonData.getAllInvites());
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
        this.current_privacy = cal.getCalendarPrivacy().getPrivacy();
    }

    public boolean getCurrent_privacy() {
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

    
    public boolean isDateInThePast(Object day){
        java.util.Date now = new Date(System.currentTimeMillis());
        java.util.Date evt_date = new Date(((java.util.Date) day).getTime());
        return now.compareTo(evt_date)<0;    
    }
    
    public boolean isTimeToSoon(Object time){
        java.util.Date now = new java.util.Date(System.currentTimeMillis());
        java.util.Date evt_time = new Date(((Date) time).getTime());
        return now.compareTo(evt_time)<0;
    }
}
