/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import meteocal.boundary.CalendarFacade;
import meteocal.boundary.EventFacade;
import meteocal.boundary.EventStatusFacade;
import meteocal.boundary.EventTypeFacade;
import meteocal.boundary.InvitationFacade;
import meteocal.boundary.PrivacyTypeFacade;
import meteocal.boundary.UserFacade;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.entity.Invitation;
import meteocal.entity.User;
import meteocal.interfaces.CalendarBeanInterface;
import meteocal.interfaces.CommonBeanInterface;
import meteocal.interfaces.UserBeanInterface;

/**
 *
 * @author Milos
 */
@Named(value = "commonBean")
@SessionScoped
public class CommonBean implements Serializable,CommonBeanInterface {

    // <editor-fold defaultstate="collapsed" desc="@EJB annotated elements">
    @EJB
    UserFacade userFacade;
    @EJB
    CalendarFacade calFacade;
    @EJB
    EventFacade eventFacade;
    @EJB
    EventStatusFacade esFacade;
    @EJB
    EventTypeFacade etFacade;
    @EJB
    InvitationFacade invFacade;
    @EJB
    PrivacyTypeFacade ptFacade;
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Data Members and Managed Properties">
    @Inject
    UserBeanInterface userData;
    @Inject
    CalendarBeanInterface calendarData;
    
    
    private List<Calendar> allCalendars;
    private List<Calendar> publicCalendars;
    private List<Calendar> privateCalendars;
    
    private List<Event> ownedEvents;
    private List<Event> otherEvents;
    private List<Event> attendingEvents;
    private List<Event> declinedEvents;
    
    private List<Invitation> allInvites;
    private List<Invitation> pendingInvites;
    private List<Invitation> declinedInvites;
    private List<Invitation> acceptedInvites;
    
    private List<User> allUsers;
    private List<String> allUsersString;
    private List<User> allUsersDB;
    // </editor-fold>
    
    
    
    public CommonBean() {
        this.ownedEvents = new ArrayList<>(0);
        this.otherEvents = new ArrayList<>(0);
        this.attendingEvents = new ArrayList<>(0);
        this.declinedEvents = new ArrayList<>(0);
        
        this.allInvites = new ArrayList<>(0);
        this.pendingInvites = new ArrayList<>(0);
        this.declinedInvites = new ArrayList<>(0);
        this.acceptedInvites = new ArrayList<>(0);
        
        this.allCalendars = new ArrayList<>(0);
        this.privateCalendars = new ArrayList<>(0);
        this.publicCalendars = new ArrayList<>(0);
        
    }
    
    @PostConstruct
    public void Init() {
        
        this.populateCalendars();
        this.populateUsers();
        this.allUsersDB = this.userFacade.findAll();
    }
    
     @Override
    public void populateCalendars() {
        this.allCalendars = calFacade.findAll();
        for (Calendar cal : this.allCalendars) {
            if (cal.getCalendarPrivacy().getPrivacy())
                this.publicCalendars.add(cal);
            else
                this.privateCalendars.add(cal);
        }
    }

    @Override
    public void populateEvents() {
        this.ownedEvents = eventFacade.findAll(this.calendarData.getCalendar());
    }

    @Override
    public void populateInvitations() {
        if(this.userData!=null){
            this.pendingInvites = new ArrayList<>();
            this.acceptedInvites = new ArrayList<>();
            this.declinedInvites = new ArrayList<>();
            this.attendingEvents = new ArrayList<>();
            this.declinedEvents = new ArrayList<>();
            this.otherEvents = new ArrayList<>();
            this.allInvites = invFacade.findAll(userData.getUser());  
            for (Invitation inv : this.allInvites) {
                this.otherEvents.add(inv.getEvent());
                    if (inv.getEventStatus().getStatus() == 0)
                        this.pendingInvites.add(inv);
                    if (inv.getEventStatus().getStatus() == 2){
                        this.declinedInvites.add(inv);
                        this.declinedEvents.add(inv.getEvent());
                    }
                    if (inv.getEventStatus().getStatus() == 1){
                        this.acceptedInvites.add(inv);
                        this.attendingEvents.add(inv.getEvent());
                    }
            }
        }
    }
    
    @Override
    public void populateUsers() {
        this.allUsers = userFacade.findAll();
        this.allUsersString = new ArrayList<>();
        for(User u : this.allUsers){
            this.allUsersString.add(u.getUsername());
        }
    }
    
    
    @Override
    public List<User> fetchAllUser(User usr){
        List<User> output = this.userFacade.findAll();
        User tmp = new User();
        for(User u : output)
            if(u.getId().equals(usr.getId()))
                tmp = u;
        output.remove(tmp);
        return output;
    }
    
    @Override
    public List<Event> getEventsForDay(Date day){
        List<Event> events = new ArrayList<>();
        java.sql.Date e_dateOfEvent;
        java.sql.Date day_date = new java.sql.Date(day.getTime());
        String e_DOF_string;
        String day_string = day_date.toString();
        for(Event e : this.ownedEvents){
            e_dateOfEvent = new java.sql.Date(e.getDateOfEvent().getTime());
            e_DOF_string = e_dateOfEvent.toString();
            if(day_string.equals(e_DOF_string))
                events.add(e);
        }
        for(Event e : this.attendingEvents){
            e_dateOfEvent = new java.sql.Date(e.getDateOfEvent().getTime());
            e_DOF_string = e_dateOfEvent.toString();
            if(day_string.equals(e_DOF_string))
                events.add(e);
        }
        return events;
    }
    
    @Override
    public void deleteEvent(Event evt) {
        this.eventFacade.remove(evt);
    }

    @Override
    public void declineInvite(Event evt, User usr) {
        this.invFacade.declineInvite(evt,usr);
    }
    
    @Override
    public void acceptInvite(Invitation inv) {
        this.invFacade.acceptInvite(inv);
    }

    @Override
    public void declineInvite(Invitation inv) {
        this.invFacade.declineInvite(inv);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public List<User> getAllUsers() {
        return allUsers;
    }

    @Override
    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }
    
    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public CalendarFacade getCalFacade() {
        return calFacade;
    }

    public void setCalFacade(CalendarFacade calFacade) {
        this.calFacade = calFacade;
    }

    public EventFacade getEventFacade() {
        return eventFacade;
    }

    public void setEventFacade(EventFacade eventFacade) {
        this.eventFacade = eventFacade;
    }

    public EventStatusFacade getEsFacade() {
        return esFacade;
    }

    public void setEsFacade(EventStatusFacade esFacade) {
        this.esFacade = esFacade;
    }

    public EventTypeFacade getEtFacade() {
        return etFacade;
    }

    public void setEtFacade(EventTypeFacade etFacade) {
        this.etFacade = etFacade;
    }

    public InvitationFacade getInvFacade() {
        return invFacade;
    }

    public void setInvFacade(InvitationFacade invFacade) {
        this.invFacade = invFacade;
    }

    public PrivacyTypeFacade getPtFacade() {
        return ptFacade;
    }

    public void setPtFacade(PrivacyTypeFacade ptFacade) {
        this.ptFacade = ptFacade;
    }

    @Override
    public UserBeanInterface getUserData() {
        return userData;
    }

    public void setUserData(UserBeanInterface userData) {
        this.userData = userData;
    }

    public CalendarBeanInterface getCalendarData() {
        return calendarData;
    }

    public void setCalendarData(CalendarBeanInterface calendarData) {
        this.calendarData = calendarData;
    }

    @Override
    public List<Calendar> getAllCalendars() {
        return allCalendars;
    }

    @Override
    public void setAllCalendars(List<Calendar> allCalendars) {
        this.allCalendars = allCalendars;
    }

    @Override
    public List<Calendar> getPublicCalendars() {
        return publicCalendars;
    }

    @Override
    public void setPublicCalendars(List<Calendar> publicCalendars) {
        this.publicCalendars = publicCalendars;
    }

    @Override
    public List<Calendar> getPrivateCalendars() {
        return privateCalendars;
    }

    @Override
    public void setPrivateCalendars(List<Calendar> privateCalendars) {
        this.privateCalendars = privateCalendars;
    }

    public List<Event> getOtherEvents() {
        return otherEvents;
    }

    public void setOtherEvents(List<Event> otherEvents) {
        this.otherEvents = otherEvents;
    }

    public List<Event> getAttendingEvents() {
        return attendingEvents;
    }

    public void setAttendingEvents(List<Event> attendingEvents) {
        this.attendingEvents = attendingEvents;
    }

    public List<Invitation> getDeclinedInvites() {
        return declinedInvites;
    }

    public void setDeclinedInvites(List<Invitation> declinedInvites) {
        this.declinedInvites = declinedInvites;
    }

    public List<Invitation> getAcceptedInvites() {
        return acceptedInvites;
    }

    public void setAcceptedInvites(List<Invitation> acceptedInvites) {
        this.acceptedInvites = acceptedInvites;
    }

    public List<Event> getDeclinedEvents() {
        return declinedEvents;
    }

    public void setDeclinedEvents(List<Event> declinedEvents) {
        this.declinedEvents = declinedEvents;
    }

    public List<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(List<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    public List<String> getAllUsersString() {
        return allUsersString;
    }

    public void setAllUsersString(List<String> allUsersString) {
        this.allUsersString = allUsersString;
    }

    @Override
    public List<Invitation> getAllInvites() {
        return allInvites;
    }

    public void setAllInvites(List<Invitation> allInvites) {
        this.allInvites = allInvites;
    }

    @Override
    public List<Invitation> getPendingInvites() {
        return pendingInvites;
    }

    public void setPendingInvites(List<Invitation> pendingInvites) {
        this.pendingInvites = pendingInvites;
    }
    // </editor-fold>

    @Override
    public void update() {
        this.populateEvents();
        this.populateInvitations();
        this.allUsersDB = this.userFacade.findAll();
    }

    public Object getUserByString(String arg2) {
        for(User u: this.allUsersDB)
            if(u.toString().equals(arg2))
                return u;
        return null;
    }

}
