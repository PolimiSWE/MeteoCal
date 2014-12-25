/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import it.polimi.registration.business.security.control.PasswordEncrypter;
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
import javax.validation.constraints.Pattern;

/**
 *
 * @author miglie
 */
@Entity
@Table(name="USERS")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private Long id;
    
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "invalid email")
    @NotNull(message = "May not be empty")
    @Column(name = "email", unique = true)
    private String email;
    
    @NotNull(message = "May not be empty")
    @Column(name = "password")
    private String password;
    
    @NotNull(message = "May not be empty")
    @Column(name = "group_name")
    private String groupName;
    
    @NotNull(message = "May not be empty")
    @Column(name = "name")
    private String name;
    
    @NotNull(message = "May not be empty")
    @Column(name = "surname")
    private String surname;
    
    @NotNull(message = "May not be empty")
    @Column(name = "username", unique = true)
    private String username;
    
    
    //Relationship Entities
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true, targetEntity = meteocal.entity.Invitation.class)
    private Collection<Invitation> invitations;
    
    @ManyToOne(optional = false, targetEntity = meteocal.entity.UserList.class)
    @JoinColumn(name = "owner_user_list", referencedColumnName = "id_user_list")
    private UserList userList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true, targetEntity = meteocal.entity.Event.class)
    private Collection<Event> ownedEvents;
    
    @NotNull(message = "May not be empty")
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity =  meteocal.entity.EventList.class)
    private EventList eventList;
    
    @NotNull(message = "May not be empty")
    @OneToOne(mappedBy = "invitedUser", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity =  meteocal.entity.InvitationList.class)
    private InvitationList invitationList;
    
    @NotNull(message = "May not be empty")
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity =  meteocal.entity.Calendar.class)
    private Calendar myCalendar;
    
    
    //Transient attributes
    @Transient
    private EventList otherEvents;
    
    @Transient
    private InvitationList acceptedInvitations;
    
    @Transient
    private InvitationList pendingInvitations;
    
    //Getters and Setters 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public InvitationList getAcceptedInvitations() {
        return acceptedInvitations;
    }

    public void setAcceptedInvitations(InvitationList acceptedInvitations) {
        this.acceptedInvitations = acceptedInvitations;
    }

    public InvitationList getPendingInvitations() {
        return pendingInvitations;
    }

    public void setPendingInvitations(InvitationList pendingInvitations) {
        this.pendingInvitations = pendingInvitations;
    }

    public EventList getOtherEvents() {
        return otherEvents;
    }

    public void setOtherEvents(EventList otherEvents) {
        this.otherEvents = otherEvents;
    }

    public Calendar getMyCalendar() {
        return myCalendar;
    }

    public void setMyCalendar(Calendar myCalendar) {
        this.myCalendar = myCalendar;
    }

    public EventList getEventList() {
        return eventList;
    }

    public void setEventList(EventList eventList) {
        this.eventList = eventList;
    }

    public Collection<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(Collection<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    public UserList getUserList() {
        return userList;
    }

    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvitationList getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(InvitationList invitationList) {
        this.invitationList = invitationList;
    }

    public Collection<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Collection<Invitation> invitations) {
        this.invitations = invitations;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordEncrypter.encryptPassword(password);
    }

}
