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
import javax.persistence.TableGenerator;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="CALENDARS")
public class Calendar implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE , generator="cal_gen")
    @TableGenerator(name="cal_gen", table="ID_GEN",
            pkColumnName="ID_NAME", valueColumnName="ID_VAL",
            pkColumnValue="CAL_GEN", initialValue = 110000000)
    @Column(name = "id_calendar")
    private Long id;

    
    //Relationship Entities
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "includedInCalendar", orphanRemoval = true, targetEntity = meteocal.entity.Event.class)
    private Collection<Event> events;
    
    @OneToOne(optional = false, targetEntity = meteocal.entity.User.class)
    @JoinColumn(name="owner_calendar", referencedColumnName = "id_user")
    private User owner;
    
    @ManyToOne(optional = false, targetEntity = meteocal.entity.PrivacyType.class)
    @JoinColumn(name = "calendar_privacy", referencedColumnName = "id_privacy_type")
    private PrivacyType calendarPrivacy;
    
    
    //Constructors  
    public Calendar() {
    }

    public Calendar(User owner) {
        this.owner = owner;
    }
    
    
    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) throws NullPointerException {
        if(id == null) throw new NullPointerException();
        this.id = id;
    }

    public PrivacyType getCalendarPrivacy() {
        return calendarPrivacy;
    }

    public void setCalendarPrivacy(PrivacyType calendarPrivacy) throws NullPointerException {
        if(calendarPrivacy == null) throw new NullPointerException();
        this.calendarPrivacy = calendarPrivacy;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) throws NullPointerException {
        if(owner == null) throw new NullPointerException();
        this.owner = owner;
    }

    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) throws NullPointerException {
        if(events == null) throw new NullPointerException();
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
        return "meteocal.entity.Calendar[ id=" + id.toString() + " ]";
    }
    
}
