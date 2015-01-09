/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.interfaces;

import java.util.List;
import meteocal.entity.Calendar;
import meteocal.entity.User;

/**
 *
 * @author Milos
 */
public interface CommonBeanInterface {
    public List<Calendar> getAllCalendars();
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
}
