/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="EVENTS")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_event")
    private Long id;
    
    @NotNull(message = "May not be empty")
    @Column(name = "begin_hour")
    private Integer beginHour;    
    
    @NotNull(message = "May not be empty")
    @Column(name = "city")
    private String city; 
    
    @NotNull(message = "May not be empty")
    @Column(name = "dateOfEvent")
    private Timestamp dateOfEvent;
    
    @NotNull(message = "May not be empty")
    @Column(name = "date_created")
    private Timestamp dateCreated; 
    
    @NotNull(message = "May not be empty")
    @Column(name = "date_modified")
    private Timestamp dateModified; 
    
    @NotNull(message = "May not be empty")
    @Column(name = "date_rescheduled")
    private Timestamp dateRescheduled; 
    
    @NotNull(message = "May not be empty")
    @Column(name = "duration")
    private Integer duration;            
    
    @NotNull(message = "May not be empty")
    @Column(name = "modified")
    private Boolean modified;  
    
    @NotNull(message = "May not be empty")
    @Column(name = "name")
    private String name;  
    
    @NotNull(message = "May not be empty")
    @Column(name = "street_and_number")
    private String streetAndNumber;    
    
    
    //Relationship Entities
    @ManyToOne(optional = false, targetEntity = meteocal.entity.EventType.class)
    @JoinColumn(name = "event_type", referencedColumnName = "id_event_type")
    private EventType eventType;
    
    @ManyToOne(optional = false, targetEntity = meteocal.entity.PrivacyType.class)
    @JoinColumn(name = "event_privacy", referencedColumnName = "id_privacy_type")
    private PrivacyType eventPrivacy;
    
    @NotNull(message = "May not be empty")
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = meteocal.entity.WeatherDataList.class)
    private WeatherDataList weatherDataList;
    
    @NotNull(message = "May not be empty")
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity =  meteocal.entity.InvitationList.class)
    private InvitationList invitationList;
    
    @NotNull(message = "May not be empty")
    @ManyToOne(optional = false, targetEntity = meteocal.entity.User.class)
    @JoinColumn(name = "owner_event", referencedColumnName = "id_user")
    private User owner;  
    
    @NotNull(message = "May not be empty")
    @ManyToOne(optional = false, targetEntity = meteocal.entity.Calendar.class)
    @JoinColumn(name = "event_calendar", referencedColumnName = "id_calendar")
    private Calendar includedInCalendar; 
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true, targetEntity = meteocal.entity.Invitation.class)
    private Collection<Invitation> invitations;
    
    @NotNull(message = "May not be empty")
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = meteocal.entity.UserList.class)
    private UserList userList;
    
    @NotNull(message = "May not be empty")
    @ManyToOne(optional = false, targetEntity = meteocal.entity.EventList.class)
    @JoinColumn(name = "event_list", referencedColumnName = "id_event_list")
    private EventList eventList;
    
    
    //Transient attributes
    @NotNull(message = "May not be empty")
    @Transient 
    private Collection<User> invited;
    
    @NotNull(message = "May not be empty")
    @Transient
    private Collection<User> participants;
    
    
    //Getters and Setters 
    public Integer getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(Integer beginHour) {
        this.beginHour = beginHour;
    }

    public EventList getEventList() {
        return eventList;
    }

    public void setEventList(EventList eventList) {
        this.eventList = eventList;
    }

    public UserList getUserList() {
        return userList;
    }

    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Timestamp getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(Timestamp dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    public Timestamp getDateRescheduled() {
        return dateRescheduled;
    }

    public void setDateRescheduled(Timestamp dateRescheduled) {
        this.dateRescheduled = dateRescheduled;
    }

    public Collection<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Collection<Invitation> invitations) {
        this.invitations = invitations;
    }

    public InvitationList getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(InvitationList invitationList) {
        this.invitationList = invitationList;
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

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
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

    public WeatherDataList getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(WeatherDataList weatherDataList) {
        this.weatherDataList = weatherDataList;
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
        return "meteocal.entity.Event[ id=" + id + " ]";
    }
    
}
