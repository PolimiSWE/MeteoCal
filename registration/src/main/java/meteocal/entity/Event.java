/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.persistence.Transient;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="EVENTS")
public class Event implements Serializable,Comparable<Event> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE , generator="evt_gen")
    @TableGenerator(name="evt_gen", table="ID_GEN",
            pkColumnName="ID_NAME", valueColumnName="ID_VAL",
            pkColumnValue="EVT_GEN", initialValue = 130000000)
    @Column(name = "id_event")
    private Long id;
    
    @NotNull(message = "May not be empty")
    @Column(name = "begin_hour")
    private Time beginHour;    
    
    @NotNull(message = "May not be empty")
    @Column(name = "city")
    private String city; 
    
    @NotNull(message = "May not be empty")
    @Temporal(DATE)
    @Column(name = "dateOfEvent")
    private Date dateOfEvent;
    
    @NotNull(message = "May not be empty")
    @Temporal(DATE)
    @Column(name = "date_created")
    private Date dateCreated; 
    
    @Temporal(DATE)
    @Column(name = "date_modified")
    private Date dateModified; 
    
    @Temporal(DATE)
    @Column(name = "date_rescheduled")
    private Date dateRescheduled; 
    
    @NotNull(message = "May not be empty")
    @Column(name = "duration")
    private Integer duration;            
    
    @Column(name = "passed")
    private Boolean passed;  
    
    @NotNull(message = "May not be empty")
    @Column(name = "name")
    private String name;  
    
    @NotNull(message = "May not be empty")
    @Column(name = "street_and_number")
    private String streetAndNumber; 
    
    @NotNull(message = "May not be empty")
    @Column(name = "notify_owner")
    private boolean notifyOwner;
    
    
    //Relationship Entities
    @ManyToOne(optional = false, targetEntity = meteocal.entity.EventType.class)
    @JoinColumn(name = "event_type", referencedColumnName = "id_event_type")
    private EventType eventType;
    
    @ManyToOne(optional = false, targetEntity = meteocal.entity.PrivacyType.class)
    @JoinColumn(name = "event_privacy", referencedColumnName = "id_privacy_type")
    private PrivacyType eventPrivacy;
    
    @NotNull(message = "May not be empty")
    @ManyToOne(optional = false, targetEntity = meteocal.entity.Calendar.class)
    @JoinColumn(name = "event_calendar", referencedColumnName = "id_calendar")
    private Calendar includedInCalendar; 
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true, targetEntity = meteocal.entity.Invitation.class)
    private Collection<Invitation> invitations;
    
    
    //Transient attributes
    @Transient 
    private User owner;
    
    @Transient 
    private Collection<User> invited;
    
    @Transient
    private Collection<User> participants;
    
    @Transient
    private Collection<User> notAttending;
    
    
    //Constructor

    public Event() {
        this.beginHour = Time.valueOf("12:00:00");
        this.city = "Enter City Name";
        this.dateCreated = new Date(System.currentTimeMillis());
        this.duration = 1;
        this.name = "Enter Event Name";
        this.streetAndNumber = "Enter Address";
    }
    
    public boolean isPassed(){
        Date now = new Date(System.currentTimeMillis());
        if(dateOfEvent!=null)
            return now.compareTo(dateOfEvent)>0;
        else
            return true;
    }
    
    
    
    //Getters and Setters 
    public Time getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(Time beginHour) {
        this.beginHour = beginHour;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(Date dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateRescheduled() {
        return dateRescheduled;
    }

    public void setDateRescheduled(Date dateRescheduled) {
        this.dateRescheduled = dateRescheduled;
    }

    public Collection<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Collection<Invitation> invitations) {
        this.invitations = invitations;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Calendar getIncludedInCalendar() {
        return includedInCalendar;
    }

    public void setIncludedInCalendar(Calendar includedInCalendar) {
        this.includedInCalendar = includedInCalendar;
    }

    public Collection<User> getInvited() {
        return invited;
    }

    public void setInvited(Collection<User> invited) {
        this.invited = invited;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<User> participants) {
        this.participants = participants;
    }

    public PrivacyType getEventPrivacy() {
        return eventPrivacy;
    }

    public void setEventPrivacy(PrivacyType eventPrivacy) {
        this.eventPrivacy = eventPrivacy;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public boolean isNotifyOwner() {
        return notifyOwner;
    }

    public void setNotifyOwner(boolean notifyOwner) {
        this.notifyOwner = notifyOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<User> getNotAttending() {
        return notAttending;
    }

    public void setNotAttending(Collection<User> notAttending) {
        this.notAttending = notAttending;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        String output;
        output = "Time: " + this.beginHour.toString() 
                + ".  Name: " + this.name;
        return output;
        
    }
    
    public String toStringOnlyPublic(){
        String output;
        if(this.eventPrivacy.getPrivacy())
            output = this.toString();
        else
            output = "Time: " + this.beginHour.toString() + ".  User busy.";
        return output;
    }

    @Override
    public int compareTo(Event o) {
       return this.getName().compareTo(o.getName());
    }

    public String parse() {
        return "Event details:  'Event name: " + this.name
                + "', 'Event date:" + this.dateOfEvent.toString()
                + "', 'Event time:" + this.beginHour.toString() + "'." ;
    }
    
}
