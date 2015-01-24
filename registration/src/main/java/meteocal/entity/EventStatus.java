/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="EVENT_STATUSES")
public class EventStatus implements Serializable,Comparable<EventStatus> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE , generator="es_gen")
    @TableGenerator(name="es_gen", table="ID_GEN",
            pkColumnName="ID_NAME", valueColumnName="ID_VAL",
            pkColumnValue="ES_GEN", initialValue = 150000000)
    @Column(name = "id_event_status")
    private Long id;
    
    @NotNull(message = "May not be empty")
    @Column(name = "status")
    private Integer status;
    
    
    //Relationship Entities
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventStatus", orphanRemoval = true, targetEntity = meteocal.entity.Invitation.class)
    private Collection<Invitation> invitationList;

    
    public EventStatus(EventStatus es) {
        this.id = es.id;
        this.status = es.status;
    }
    
    public EventStatus() {
        this.status = 0;
        invitationList = new ArrayList<>();
    }
    
    
    //Getters and Setters 
    public Collection<Invitation> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(Collection<Invitation> invitationList) {
        this.invitationList = invitationList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        if (!(object instanceof EventStatus)) {
            return false;
        }
        EventStatus other = (EventStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(this.status==0)
            return "Pending";
        else if(this.status==1)
            return "Accepted";
        else
            return "Declined";
    }

    @Override
    public int compareTo(EventStatus o) {
        return this.getStatus().compareTo(o.getStatus());
    }
    
}
