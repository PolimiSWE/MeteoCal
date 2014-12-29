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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="EVENT_TYPES")
public class EventType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_event_type")
    private Long id;
    
    @NotNull(message = "May not be empty")
    @Column(name = "type")
    private Boolean type;
    
    
    //Relationship Entities
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventType", orphanRemoval = true, targetEntity = meteocal.entity.Event.class)
    private Collection<Event> eventList;

    public EventType() {
        this.id = (long)-1;
        this.type = false;
    }

    public EventType(EventType tmp) {
        this.id = tmp.id;
        this.type = tmp.type;
    }
    
    
    
    //Getters and Setters 
    public Collection<Event> getEventList() {
        return eventList;
    }

    public void setEventList(Collection<Event> eventList) {
        this.eventList = eventList;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Long getId() {
        if(id!=null)
            return id;
        else 
            return (long)-1;
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
        if (!(object instanceof EventType)) {
            return false;
        }
        EventType other = (EventType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.registration.business.security.entity.EventType[ id=" + id + " ]";
    }
    
}
