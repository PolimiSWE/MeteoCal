/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.EventTypeFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import meteocal.entity.EventType;

/**
 *
 * @author Milos
 */
@Named(value = "eventTypeBean")
@SessionScoped
public class EventTypeBean implements Serializable {

    @EJB
    EventTypeFacade etm;
    
    private Boolean value;
    private EventType et;
    private List<EventType> dboutput;
    
    
    public EventTypeBean() {
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        dboutput = etm.getDB_Table();
        if(et == null) et = new EventType();
    }
    
    public void createNew(){
        et = etm.createNew();
        dboutput = etm.getDB_Table();
    }
    
    public void save() {
        etm.save(et);
        dboutput = etm.getDB_Table();
        //return "user/eventTypeAdminPage?faces-redirect=true";
    }

    public void edit(int etId) { 
       et = etm.getEventType(etId);
       //return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    public void delete(int etId) {
        etm.delete(etId);
        dboutput = etm.getDB_Table();
        //return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    //Getters and Setters
    public EventTypeFacade getEtm() {
        return etm;
    }

    public void setEtm(EventTypeFacade etm) {
        this.etm = etm;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public EventType getEt() {
        return et;
    }

    public void setPt(EventType et) {
        this.et = et;
    }

    public List<EventType> getDboutput() {
        return dboutput;
    }

    public void setDboutput(List<EventType> dboutput) {
        this.dboutput = dboutput;
    }
    
    
}
