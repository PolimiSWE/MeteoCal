/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import meteocal.boundary.EventStatusFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import meteocal.entity.EventStatus;

/**
 *
 * @author Milos
 */
@Named(value = "eventStatusBean")
@SessionScoped
public class EventStatusBean implements Serializable {

    @EJB
    EventStatusFacade etm;
    
    private Integer value;
    private EventStatus es;
    private List<EventStatus> dboutput;
    
    public EventStatusBean() {
        if(es == null) es = new EventStatus();
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        dboutput = etm.getDB_Table();
    }
    
    public void createNew(){
        es = etm.createNew();
        dboutput = etm.getDB_Table();
    }
    
    public void save() {
        etm.save(es);
        dboutput = etm.getDB_Table();
    }

    public void edit(int esId) { 
       es = etm.getEventType(esId);
    }
    
    public void delete(int esId) {
        etm.delete(esId);
        dboutput = etm.getDB_Table();
    }
    
    //Getters and Setters

    public EventStatusFacade getEtm() {
        return etm;
    }

    public void setEtm(EventStatusFacade etm) {
        this.etm = etm;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public EventStatus getEs() {
        return es;
    }

    public void setEs(EventStatus es) {
        this.es = es;
    }

    public List<EventStatus> getDboutput() {
        return dboutput;
    }

    public void setDboutput(List<EventStatus> dboutput) {
        this.dboutput = dboutput;
    }
    
    
}
