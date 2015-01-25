/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.interfaces;

import java.util.Date;
import java.util.List;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.entity.Invitation;
import meteocal.entity.User;

/**
 *
 * @author Milos
 */
public interface CommonBeanInterface {
    public List<Calendar> getAllCalendars();
    public UserBeanInterface getUserData();
    public void setAllCalendars(List<Calendar> allCalendars);
    public List<Calendar> getPublicCalendars();
    public void setPublicCalendars(List<Calendar> publicCalendars);
    public List<Calendar> getPrivateCalendars();
    public void setPrivateCalendars(List<Calendar> privateCalendars);
    public List<User> getAllUsers();
    public void setAllUsers(List<User> allUsers);
    public void populateCalendars();
    public void populateEvents();
    public void populateInvitations();
    public void populateUsers();
    public List<Event> getEventsForDay(Date day);

    public List<Invitation> getPendingInvites();

    public List<Invitation> getAllInvites();

    public void deleteEvent(Event evt);

    public void declineInvite(Event evt, User usr);

    public void acceptInvite(Invitation inv);

    public void declineInvite(Invitation inv);
    
    public List<User> fetchAllUser(User usr);

    public void update();
}
