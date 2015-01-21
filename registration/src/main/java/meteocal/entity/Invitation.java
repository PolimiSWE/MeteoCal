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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="INVITATIONS")
public class Invitation implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE , generator="inv_gen")
    @TableGenerator(name="inv_gen", table="ID_GEN",
            pkColumnName="ID_NAME", valueColumnName="ID_VAL",
            pkColumnValue="INV_GEN", initialValue = 170000000)
    @Column(name = "id_invitation")
    private Long id;
    
    //Relationship Entities
    @ManyToOne(optional = false, targetEntity = meteocal.entity.EventStatus.class)
    @JoinColumn(name = "event_status", referencedColumnName = "id_event_status")
    private EventStatus eventStatus; 
    
    @ManyToOne(optional = false, targetEntity = meteocal.entity.Event.class)
    @JoinColumn(name = "owner_event", referencedColumnName = "id_event")
    private Event event;
    
    @ManyToOne(optional = false, targetEntity = meteocal.entity.User.class)
    @JoinColumn(name = "owner_user", referencedColumnName = "id_user")
    private User user;
    
    
    //Getters and Setters
    public EventStatus getEventStatus() {
        return eventStatus;
    }    

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
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
