/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import meteocal.boundary.PrivacyTypeFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import meteocal.entity.PrivacyType;

/**
 *
 * @author Milos
 */
@Named(value = "privacyTypeBean")
@SessionScoped
public class PrivacyTypeBean implements Serializable {

    @EJB
    PrivacyTypeFacade ptm;
    
    private Boolean value;
    private PrivacyType pt;
    private List<PrivacyType> dboutput;
    
    public PrivacyTypeBean() {
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        dboutput = ptm.getDB_Table();
        if(pt == null)
            pt = new PrivacyType();
    }
    
    public void createNew(){
        pt = ptm.createNew();
        dboutput = ptm.getDB_Table();
    }
    
    public void save() {
        ptm.save(pt);
        dboutput = ptm.getDB_Table();
        //return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    public void edit(int ptId) { 
       pt = ptm.getPrivacyType(ptId);
       //return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    public void delete(int ptId) {
        ptm.delete(ptId);
        dboutput = ptm.getDB_Table();
        //return "privacyTypeAdminPage?faces-redirect=true";
    }

    //Getters and Setters
    public PrivacyTypeFacade getPtm() {
        return ptm;
    }

    public void setPtm(PrivacyTypeFacade ptm) {
        this.ptm = ptm;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public PrivacyType getPt() {
        return pt;
    }

    public void setPt(PrivacyType pt) {
        this.pt = pt;
    }

    public List<PrivacyType> getDboutput() {
        return dboutput;
    }

    public void setDboutput(List<PrivacyType> dboutput) {
        this.dboutput = dboutput;
    }
    
}
