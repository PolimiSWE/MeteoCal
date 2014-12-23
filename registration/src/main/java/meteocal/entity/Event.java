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
import org.apache.derby.client.am.DateTime;

/**
 *
 * @author Milos
 */
@Entity
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private EventStatus status; 
    @ManyToOne
    private EventType eventType;
    @ManyToOne
    private PrivacyType privacy; 
    
    @NotNull(message = "May not be empty")
    private Integer beginHour;    
    @NotNull(message = "May not be empty")
    private String city;    
    @NotNull(message = "May not be empty")
    private DateTime date;    
    @NotNull(message = "May not be empty")
    private DateTime dateCreated;    
    @NotNull(message = "May not be empty")
    private DateTime dateModified;    
    @NotNull(message = "May not be empty")
    private DateTime dateRescheduled;    
    @NotNull(message = "May not be empty")
    private Integer duration;        
    @NotNull(message = "May not be empty")
    private Integer includedInCalendar; // to be changed    
    @NotNull(message = "May not be empty")
    private List<User> invited;    
    @NotNull(message = "May not be empty")
    private Boolean modified;    
    @NotNull(message = "May not be empty")
    private String name;    
    @NotNull(message = "May not be empty")
    private User owner;    
    @NotNull(message = "May not be empty")
    private List<User> participants;       
    @NotNull(message = "May not be empty")
    private String streetAndNumber;    
    @NotNull(message = "May not be empty")
    private Integer weatherDataList; // to be changed

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Integer getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(Integer beginHour) {
        this.beginHour = beginHour;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public DateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
    }

    public DateTime getDateRescheduled() {
        return dateRescheduled;
    }

    public void setDateRescheduled(DateTime dateRescheduled) {
        this.dateRescheduled = dateRescheduled;
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

    public Integer getIncludedInCalendar() {
        return includedInCalendar;
    }

    public void setIncludedInCalendar(Integer includedInCalendar) {
        this.includedInCalendar = includedInCalendar;
    }

    public List<User> getInvited() {
        return invited;
    }

    public void setInvited(List<User> invited) {
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

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public PrivacyType getPrivacy() {
        return privacy;
    }

    public void setPrivacy(PrivacyType privacy) {
        this.privacy = privacy;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public Integer getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(Integer weatherDataList) {
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
        return "it.polimi.registration.business.security.entity.Event[ id=" + id + " ]";
    }
    
}
