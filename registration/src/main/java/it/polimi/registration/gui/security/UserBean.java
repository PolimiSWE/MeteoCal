/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.UserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import meteocal.entity.User;

/**
 *
 * @author Milos
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    @EJB
    UserFacade um;
    
    private User current;
    private List<User> dboutput;
    
    public UserBean() {
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        dboutput = um.getDB_Table();
        if(current == null) 
        {
            current = new User();
        }
    }
    
    public void createNew(){
        current = um.createNew();
        dboutput = um.getDB_Table();
    }
    
    public void save() {
        um.save(current);
        dboutput = um.getDB_Table();
        //return "user/eventTypeAdminPage?faces-redirect=true";
    }

    public void edit(int usrId) { 
       current = um.getUser(usrId);
       //return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    public void delete(int usrId) {
        um.delete(usrId);
        dboutput = um.getDB_Table();
        //return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    
    //Getters and Setters
    public String getName() {
        return um.getLoggedUser().getName();
    }

    public UserFacade getUm() {
        return um;
    }

    public void setUm(UserFacade um) {
        this.um = um;
    }

    public List<User> getDboutput() {
        return dboutput;
    }

    public void setDboutput(List<User> dboutput) {
        this.dboutput = dboutput;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }
    
    
}
