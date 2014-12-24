/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Milos
 */
@Entity
public class Invitation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_invitation")
    private Long id;

    
    //Relationship Entities
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_status", referencedColumnName = "id_event_status")
    private EventStatus eventStatus; 
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_event", referencedColumnName = "id_event")
    private Event event;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_user", referencedColumnName = "id_user")
    private User user;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_invitation_list", referencedColumnName = "id_invitation_list")
    private InvitationList invitationList;
    
    
    //Getters and Setters
    public EventStatus getEventStatus() {
        return eventStatus;
    }    

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public InvitationList getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(InvitationList invitationList) {
        this.invitationList = invitationList;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invitation)) {
            return false;
        }
        Invitation other = (Invitation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meteocal.entity.Invitation[ id=" + id + " ]";
    }
    
}
