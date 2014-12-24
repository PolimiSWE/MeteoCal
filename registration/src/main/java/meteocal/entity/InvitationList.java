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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Milos
 */
@Entity
public class InvitationList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_invitation_list")
    private Long id;

    //Relationship Entities
    @OneToOne(optional = false)
    @JoinColumn(name="event", referencedColumnName = "id_event")
    private Event event;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invitationList", orphanRemoval = true)
    private Collection<Invitation> invitations;
    
    
    
    //Transient attributes
    @Transient 
    private Collection<Invitation> acceptedInvitations;
    
    @Transient
    private Collection<Invitation> declinedInvitations;
    
    @Transient
    private Collection<Invitation> pendingInvitations;
    
    
    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Collection<Invitation> invitations) {
        this.invitations = invitations;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Collection<Invitation> getAcceptedInvitations() {
        return acceptedInvitations;
    }

    public void setAcceptedInvitations(Collection<Invitation> acceptedInvitations) {
        this.acceptedInvitations = acceptedInvitations;
    }

    public Collection<Invitation> getDeclinedInvitations() {
        return declinedInvitations;
    }

    public void setDeclinedInvitations(Collection<Invitation> declinedInvitations) {
        this.declinedInvitations = declinedInvitations;
    }

    public Collection<Invitation> getPendingInvitations() {
        return pendingInvitations;
    }

    public void setPendingInvitations(Collection<Invitation> pendingInvitations) {
        this.pendingInvitations = pendingInvitations;
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
        if (!(object instanceof InvitationList)) {
            return false;
        }
        InvitationList other = (InvitationList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meteocal.entity.InvitationList[ id=" + id + " ]";
    }
    
}
