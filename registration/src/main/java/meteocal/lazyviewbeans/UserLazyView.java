/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.lazyviewbeans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import meteocal.bean.PublicCalendarsBean;
import meteocal.boundary.UserFacade;
import meteocal.entity.User;
import meteocal.interfaces.CommonBeanInterface;
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
    
    @Inject
    CommonBeanInterface commonData;
    @Inject
    PublicCalendarsBean pubCalData;
    
    private UserLazyDataModel lazyModel;
     
    private User selectedUser;
     
    @PostConstruct
    public void init() {
        lazyModel = new UserLazyDataModel(uf.findAll()); // treba da se promeni na new list 
        //i da pokupi samo public sad kupi sve
        if(pubCalData.getCurrent().getOwner()!=null)
            this.selectedUser = pubCalData.getCurrent().getOwner();
        else
            this.selectedUser = commonData.getAllUsers().get(0);
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
     
    public void initUserLazyView(List<User> users) {
        this.lazyModel = new UserLazyDataModel(users);
    }
    
    public void update(){
        this.commonData.populateUsers();
        this.initUserLazyView(this.commonData.getAllUsers());
    }
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg;
        msg = new FacesMessage("User Selected", this.selectedUser.getName()+" "+this.selectedUser.getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
