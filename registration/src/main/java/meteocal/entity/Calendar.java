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

/**
 *
 * @author Milos
 */
@Entity
@Table(name="CALENDARS")
public class Calendar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_calendar")
    private Long id;

    
    //Relationship Entities
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "includedInCalendar", orphanRemoval = true, targetEntity = meteocal.entity.Event.class)
    private Collection<Event> events;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "includedInCalendar", orphanRemoval = true, targetEntity = meteocal.entity.EventList.class)
    private Collection<EventList> eventLists;
    
    @OneToOne(optional = false, targetEntity = meteocal.entity.User.class)
    @JoinColumn(name="owner_calendar", referencedColumnName = "id_user")
    private User owner;
    
    @ManyToOne(optional = false, targetEntity = meteocal.entity.CalendarList.class)
    @JoinColumn(name = "calendar_list", referencedColumnName = "id_calendar_list")
    private CalendarList calendarList;
    
    @ManyToOne(optional = false, targetEntity = meteocal.entity.PrivacyType.class)
    @JoinColumn(name = "calendar_privacy", referencedColumnName = "id_privacy_type")
    private PrivacyType calendarPrivacy;
    
    
    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrivacyType getCalendarPrivacy() {
        return calendarPrivacy;
    }

    public void setCalendarPrivacy(PrivacyType calendarPrivacy) {
        this.calendarPrivacy = calendarPrivacy;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public CalendarList getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(CalendarList calendarList) {
        this.calendarList = calendarList;
    }

    public Collection<EventList> getEventLists() {
        return eventLists;
    }

    public void setEventLists(Collection<EventList> eventLists) {
        this.eventLists = eventLists;
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
        if (!(object instanceof Calendar)) {
            return false;
        }
        Calendar other = (Calendar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meteocal.entity.Calendar[ id=" + id + " ]";
    }
    
}
