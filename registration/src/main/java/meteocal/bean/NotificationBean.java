/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import meteocal.boundary.EventFacade;
import meteocal.boundary.InvitationFacade;
import meteocal.boundary.NotificationFacade;
import meteocal.entity.Event;
import meteocal.entity.Notification;
import meteocal.entity.User;
import meteocal.helper.NotificationHelper;

/**
 *
 * @author Milos
 */
@Named(value = "notificationBean")
@SessionScoped
public class NotificationBean implements Serializable {
    
    @EJB
    NotificationFacade  notff;
    @EJB
    EventFacade evtf;
   
 
    
    private List<NotificationHelper> internalNOTF;
    private List<Notification> externalNOTF;

    
    @PostConstruct
    public void init(){
        
    }
    
    public void updateNotifications(User u){
        this.populateExternalNotf(u);
        this.populateInternalNotf(u);
    }
    
    public void populateExternalNotf(User u){
        this.externalNOTF = new ArrayList<>();
        this.externalNOTF = new ArrayList(u.getNotifications());
    }
    
    public void populateInternalNotf(User u){
        this.internalNOTF = new ArrayList<>();
        List<Event> dirtyInvitations = evtf.findDirtyEvents(u.getId());
        for(Event evt: dirtyInvitations){
            this.internalNOTF.add(new NotificationHelper(evt));
        }
    }
    
    public List<NotificationHelper> getInternalNOTF() {
        return internalNOTF;
    }

    public void setInternalNOTF(List<NotificationHelper> internalNOTF) {
        this.internalNOTF = internalNOTF;
    }

    public List<Notification> getExternalNOTF() {
        return externalNOTF;
    }

    public void setExternalNOTF(List<Notification> externalNOTF) {
        this.externalNOTF = externalNOTF;
    }
    
    public void clearNotf(long notf_id){
        notff.removeByID(notf_id);
    }
    
    public void clearInternalNotf(long notf_evt_id){
        NotificationHelper tmp = new NotificationHelper();
        for(NotificationHelper nh:this.internalNOTF)
            if( Objects.equals((long) nh.getCausedEvent().getId(),notf_evt_id))
                tmp = nh;
        this.internalNOTF.remove(tmp);
        Event evt = tmp.getCausedEvent();
        this.evtf.updateDirty(evt);
    }
    
    
    
}
