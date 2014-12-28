/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.PrivacyTypeFacade;
import java.io.Serializable;
import java.util.List;
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
        if(pt == null) pt = new PrivacyType();
    }
    
    public String save() {
        ptm.save(pt);
        return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    public String edit(int ptId) {
       pt = ptm.getPrivacyType(ptId);
       return "privacyTypeAdminPage?faces-redirect=true";
    }
    
    public String delete(int ptId) {
        ptm.delete(ptId);
        return "privacyTypeAdminPage?faces-redirect=true";
    }

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
        dboutput = ptm.getDB_Table();
        return dboutput;
    }

    public void setDboutput(List<PrivacyType> dboutput) {
        this.dboutput = dboutput;
    }
    
}
