/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.lazyviewbeans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import meteocal.boundary.UserFacade;
import meteocal.entity.User;
import meteocal.lazydatamodel.UserLazyDataModel;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Milos
 */
@Named(value="userLazyView")
@RequestScoped
public class UserLazyView implements Serializable {
    
    @EJB
    UserFacade uf;
    
    private UserLazyDataModel lazyModel;
     
    private User selectedUser;
     
    @PostConstruct
    public void init() {
        lazyModel = new UserLazyDataModel(uf.findAll());
    }
 
    public UserLazyDataModel getLazyModel() {
        return lazyModel;
    }
 
    public User getSelectedUser() {
        return selectedUser;
    }
 
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
     
    
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg;
        msg = new FacesMessage("User Selected", this.selectedUser.getName()+" "+this.selectedUser.getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
