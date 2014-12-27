/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.EventTypeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import meteocal.entity.EventType;

/**
 *
 * @author Milos
 */
@Named(value = "eventTypeBean")
@RequestScoped
public class EventTypeBean {

    @EJB
    EventTypeFacade etm;
    
    private Boolean value;
    private EventType et;
    private List<EventType> dboutput;
    
    
    public EventTypeBean() {
        if(et == null) et = new EventType();
    }
    
    public void save() {
        etm.save(et);
        //return "user/eventTypeAdminPage?faces-redirect=true";
    }

    public EventTypeFacade getEtm() {
        return etm;
    }

    public void setEtm(EventTypeFacade ptm) {
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
        dboutput = etm.getDB_Table();
        return dboutput;
    }

    public void setDboutput(List<EventType> dboutput) {
        this.dboutput = dboutput;
    }
    
    
}
