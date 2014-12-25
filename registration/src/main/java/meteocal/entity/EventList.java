/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="EVENT_LISTS")
public class EventList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_event_list")
    private Long id;
    
    
    //Relationship Entities
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventList", orphanRemoval = true, targetEntity = meteocal.entity.Event.class)
    private Collection<Event> events;
    
    @NotNull(message = "May not be empty")
    @ManyToOne(optional = false, targetEntity = meteocal.entity.Calendar.class)
    @JoinColumn(name = "event_list_calendar", referencedColumnName = "id_calendar")
    private Calendar includedInCalendar;
    
    @OneToOne(optional = false, targetEntity = meteocal.entity.User.class)
    @JoinColumn(name="owner_event_list", referencedColumnName = "id_user")
    private User owner;
    
    
    //Transient attributes
    @Transient 
    private Collection<Event> ownedEvents;
    
    @Transient
    private Collection<Event> otherEvents;
    

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(Collection<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    public Collection<Event> getOtherEvents() {
        return otherEvents;
    }

    public void setOtherEvents(Collection<Event> otherEvents) {
        this.otherEvents = otherEvents;
    }

    public Calendar getIncludedInCalendar() {
        return includedInCalendar;
    }

    public void setIncludedInCalendar(Calendar includedInCalendar) {
        this.includedInCalendar = includedInCalendar;
    }

    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
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
        if (!(object instanceof EventList)) {
            return false;
        }
        EventList other = (EventList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meteocal.entity.EventList[ id=" + id + " ]";
    }
    
}
