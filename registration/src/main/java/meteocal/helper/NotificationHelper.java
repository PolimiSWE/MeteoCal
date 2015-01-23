/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.helper;

import meteocal.entity.Event;
import meteocal.entity.Notification;
import meteocal.entity.User;

/**
 *
 * @author Milos
 */
public class NotificationHelper {
    
    private User destUser;
    private Event causedEvent;
    private String description;

    public NotificationHelper() {
    }
    
    public NotificationHelper(Event evt){
        this.destUser = evt.getOwner();
        this.causedEvent = evt;
        this.description = "Bad Weather Conditions, Please Consider Rescheduling the Event.";
    }
    
    public NotificationHelper(Notification notf){
        this.destUser = notf.getOwner();
        this.description = notf.getDescription();
    }

    public User getDestUser() {
        return destUser;
    }

    public void setDestUser(User destUser) {
        this.destUser = destUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Event getCausedEvent() {
        return causedEvent;
    }

    public void setCausedEvent(Event causedEvent) {
        this.causedEvent = causedEvent;
    }

   
    
}
