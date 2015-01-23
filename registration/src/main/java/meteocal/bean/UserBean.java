/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import meteocal.boundary.UserFacade;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import meteocal.entity.Calendar;
import meteocal.entity.User;
import meteocal.interfaces.CalendarBeanInterface;
import meteocal.interfaces.CommonBeanInterface;
import meteocal.interfaces.UserBeanInterface;

/**
 *
 * @author Milos
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable,UserBeanInterface {

    // <editor-fold defaultstate="collapsed" desc="@EJB annotated elements">
    @EJB
    UserFacade um;
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Data Members and Managed Properties">
    @Inject
    CommonBeanInterface commonData;
    @Inject
    CalendarBeanInterface calendarData;
    @Inject
    NotificationBean notificationData; 
    
    private User current;
    private String password;
    private String username;
    private String usernameMsg;
    private String emailMsg;
    private Boolean emailValid;
    private Boolean usernameValid;
    
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  
    // </editor-fold> 
    
    public UserBean() {
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        if(current == null) 
        {
            current = new User();
            usernameMsg = "";
            emailMsg = "";
            usernameValid = false;
            emailValid = false;
        }
    }
    
    @Override
    public void createNew(){
        current = um.createNew();
        usernameMsg = "";
        emailMsg = "";
        usernameValid = false;
        emailValid = false;
    }
    
    @Override
    public void saveUser(User usr) {
        List<User> tmp_allUsers = this.commonData.getAllUsers();
        int index = -1;
        for (User u : tmp_allUsers) {
            if(Objects.equals(u.getId(), usr.getId()))
                index = tmp_allUsers.indexOf(u);
        }
        if(index!=-1)
            tmp_allUsers.remove(index);
        um.save(usr);
        usernameMsg = "";
        emailMsg = "";
        tmp_allUsers.add(usr);
        this.commonData.setAllUsers(tmp_allUsers);
    }

    public void selectUser(User user) { 
       current = user;
       this.calendarData.selectCalendar(user.getMyCalendar());
       this.commonData.populateEvents();
       this.commonData.populateInvitations();
       this.calendarData.populateCalHelper();
       this.calendarData.populateInvitations();
       usernameMsg = "";
       emailMsg = "";
    }
    
    @Override
    public void selectUser(String usrnm) {
       current = um.getUser(usrnm);
       this.calendarData.selectCalendar(current.getMyCalendar());
       this.commonData.populateEvents();
       this.commonData.populateInvitations();
       this.calendarData.populateCalHelper();
       this.calendarData.populateInvitations();
       usernameMsg = "";
       emailMsg = "";
    }
    
    public void delete(User user) {
        um.delete(user);
        usernameMsg = "";
        emailMsg = "";
        this.commonData.getAllUsers().remove(user);
        this.commonData.getAllCalendars().remove(user.getMyCalendar());
        if(user.getMyCalendar().getCalendarPrivacy().getPrivacy())
            this.commonData.getPublicCalendars().remove(user.getMyCalendar());
        else
            this.commonData.getPrivateCalendars().remove(user.getMyCalendar());
    }
    
    public void validateUsername(FacesContext context, UIComponent toValidate, Object value) {
        current.setUsername(value.toString());
       if(um.checkUsername(current)){
           this.usernameMsg = "Username not taken.";
           usernameValid = true;
       }
       else{
           this.usernameMsg = "Username TAKEN!!! Please select different username";
           usernameValid = false;
       }
    }
    
    public void validateEmail(FacesContext context, UIComponent toValidate, Object value) {
       Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(value.toString());
       if(matcher.find()){ 
           current.setEmail(value.toString());
           if(um.checkEmail(current)){
               this.usernameMsg = "Username not taken.";
               emailValid = true;
           }
           else{
               this.usernameMsg = "Email Address TAKEN!!! Please select different email address";
               emailValid = false;
           }
       }
       else{
           this.usernameMsg = "Email address is not well formed!!!";
           emailValid = false;
       }
    }
    
    @Override
    public User getUser() {
        return this.current;
    }

    @Override
    public void addUser(User usr) {
        List<User> tmp_allUsers = this.commonData.getAllUsers();
        List<Calendar> tmp_allCalendars = this.commonData.getAllCalendars();
        List<Calendar> tmp_publicCalendars = this.commonData.getPublicCalendars();
        List<Calendar> tmp_privateCalendars = this.commonData.getPrivateCalendars();
        um.save(usr);
        usernameMsg = "";
        emailMsg = "";
        tmp_allUsers.add(usr);
        tmp_allCalendars.add(usr.getMyCalendar());
        if(usr.getMyCalendar().getCalendarPrivacy().getPrivacy())
            tmp_publicCalendars.add(usr.getMyCalendar());
        else
            tmp_privateCalendars.add(usr.getMyCalendar());
        this.commonData.setAllCalendars(tmp_allCalendars);
        this.commonData.setAllUsers(tmp_allUsers);
        this.commonData.setPrivateCalendars(tmp_privateCalendars);
        this.commonData.setPublicCalendars(tmp_publicCalendars);
    }
    
    @Override
    public void saveUser() {
        List<User> tmp_allUsers = this.commonData.getAllUsers();
        int index = -1;
        for (User u : tmp_allUsers) {
            if(Objects.equals(u.getId(), current.getId()))
                index = tmp_allUsers.indexOf(u);
        }
        if(index!=-1)
            tmp_allUsers.remove(index);
        um.save(current);
        usernameMsg = "";
        emailMsg = "";
        tmp_allUsers.add(current);
        this.commonData.setAllUsers(tmp_allUsers);
    }
    
    @Override
    public User getUserByUsername(String username) {
        return um.getUser(username);
    }
    
    public void updateNotifications(){
        this.notificationData.updateNotifications(current);
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getName() {
        return um.getLoggedUser().getName();
    }

    public CommonBean getCommonData() {
        return (CommonBean) commonData;
    }

    public void setCommonData(CommonBean commonData) {
        this.commonData = commonData;
    }

    public CalendarBeanInterface getCalendarData() {
        return calendarData;
    }

    public void setCalendarData(CalendarBeanInterface calendarData) {
        this.calendarData = calendarData;
    }

    public Boolean getEmailValid() {
        return emailValid;
    }

    public void setEmailValid(Boolean emailValid) {
        this.emailValid = emailValid;
    }

    public Boolean getUsernameValid() {
        return usernameValid;
    }

    public void setUsernameValid(Boolean usernameValid) {
        this.usernameValid = usernameValid;
    }
    
    public UserFacade getUm() {
        return um;
    }

    public void setUm(UserFacade um) {
        this.um = um;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
        this.current.setPassword(password);
    }

    public String getUsernameMsg() {
        return usernameMsg;
    }

    public void setUsernameMsg(String usernameMsg) {
        this.usernameMsg = usernameMsg;
    }

    public String getEmailMsg() {
        return emailMsg;
    }

    public void setEmailMsg(String emailMsg) {
        this.emailMsg = emailMsg;
    }
    
    @Override
    public void setUsername(String username) {
        this.username = username;
        this.current.setUsername(username);
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    // </editor-fold> 

    @Override
    public void setName(String name) {
        this.current.setName(name);
    }

    @Override
    public void setSurname(String surname) {
        this.current.setSurname(surname);
    }

    @Override
    public void setEmail(String email) {
        this.current.setEmail(email);
    }

}
