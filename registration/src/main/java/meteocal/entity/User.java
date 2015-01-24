/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import meteocal.control.PasswordEncrypter;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="USERS")
public class User implements Serializable,Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE , generator="usr_gen")
    @TableGenerator(name="usr_gen", table="ID_GEN",
            pkColumnName="ID_NAME", valueColumnName="ID_VAL",
            pkColumnValue="USR_GEN", initialValue = 200000000)
    @Column(name = "id_user")
    private Long id;
    
    //@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
    //        message = "invalid email")
    @NotNull(message = "May not be empty")
    @Column(name = "email", unique = true)
    private String email;
    
    @NotNull(message = "May not be empty")
    @Column(name = "groupName")
    private String groupName;
    
    @NotNull(message = "May not be empty")
    @Column(name = "password")
    private String password;
    
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true, targetEntity = meteocal.entity.Notification.class)
    private Collection<Notification> notifications;
    
    @NotNull(message = "May not be empty")
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity =  meteocal.entity.Calendar.class)
    private Calendar myCalendar;
    
    
    //Transient attributes
    @Transient
    private Collection<Event> ownedEvents;
    
    @Transient
    private Collection<Event> otherEvents;
    
    @Transient
    private Collection<Invitation> acceptedInvitations;
    
    @Transient
    private Collection<Invitation> pendingInvitations;
    
    //Constructor

    public User() {
        this.email = "to be entered";
        this.password = "to be entered";
        this.name = "to be entered";
        this.surname = "to be entered";
        this.username = "to be entered";
        this.groupName = Group.USERS;
        this.invitations = null;
        this.myCalendar = new Calendar(this);
    }
    
    
    //Getters and Setters 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
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

    public Collection<Event> getOtherEvents() {
        return otherEvents;
    }

    public void setOtherEvents(Collection<Event> otherEvents) {
        this.otherEvents = otherEvents;
    }

    public Collection<Invitation> getAcceptedInvitations() {
        return acceptedInvitations;
    }

    public void setAcceptedInvitations(Collection<Invitation> acceptedInvitations) {
        this.acceptedInvitations = acceptedInvitations;
    }

    public Collection<Invitation> getPendingInvitations() {
        return pendingInvitations;
    }

    public void setPendingInvitations(Collection<Invitation> pendingInvitations) {
        this.pendingInvitations = pendingInvitations;
    }

    public Calendar getMyCalendar() {
        return myCalendar;
    }

    public void setMyCalendar(Calendar myCalendar) {
        this.myCalendar = myCalendar;
    }

    public Collection<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(Collection<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    @Override
    public String toString() {
        return this.getName()+" "+this.getSurname()+" ("+this.getUsername()+")"; 
    }

    @Override
    public int compareTo(User o) {
        return this.toString().compareTo(o.toString());
    }
    
}
