/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.helper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import meteocal.entity.Event;

/**
 *
 * @author Milos
 */
public class DayHelper {
    
    List<Event> todaysEvents;
    Date today;

    DayHelper(Date firstDay) {
        this.today = firstDay;
        this.todaysEvents = new ArrayList<>();
    }

    public  List<Event> DayHelper() {
        return todaysEvents;
    }

    public void setTodaysEvents( List<Event> todaysEvents) {
        this.todaysEvents = todaysEvents;
    }
    
    public Event getEvent(Time time){
        int index=-1;
        for(Event e : this.todaysEvents){
            if(e.getBeginHour().equals(time))
                index=this.todaysEvents.indexOf(e);
        }
        if(index==-1)
            return null;
        else
            return this.todaysEvents.get(index);
    }
    
}
