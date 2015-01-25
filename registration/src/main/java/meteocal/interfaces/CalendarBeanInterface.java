/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.interfaces;

import meteocal.entity.Calendar;
import meteocal.helper.CalendarHelper;

/**
 *
 * @author Milos
 */
public interface CalendarBeanInterface {
    public Calendar getCalendar();
    public void selectCalendar(Calendar cal);
    public void populateCalHelper();
    public void populateInvitations();
    public CalendarHelper getCalHelper();

    public void update();
}
