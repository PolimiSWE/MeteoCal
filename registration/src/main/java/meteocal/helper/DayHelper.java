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

    public DayHelper() {
        this.todaysEvents = new ArrayList<>();
        this.today = new Date(System.currentTimeMillis());
    }

    public  List<Event> DayHelper() {
        return todaysEvents;
    }
    
    public boolean checkTimeSlot(Event evt){
        boolean indicator = true;
        Time evt_endTime;
        Time e_endTime;
        for(Event e : this.todaysEvents){
            e_endTime = new Time(e.getBeginHour().getTime() + (long)e.getDuration()*60*1000);
            evt_endTime = new Time(evt.getBeginHour().getTime() + (long)evt.getDuration()*60*1000);
            if(evt.getBeginHour().before(e.getBeginHour()) &&
                    evt_endTime.after(e.getBeginHour()) &&
                    evt_endTime.before(e_endTime))
                indicator = false;
            if(e.getBeginHour().before(evt.getBeginHour()) &&
                    e_endTime.after(evt.getBeginHour()) &&
                    e_endTime.before(evt_endTime))
                indicator = false;
        }
        return indicator;
    }
    

    public void setTodaysEvents( List<Event> todaysEvents) {
        this.todaysEvents = todaysEvents;
    }

    public List<Event> getTodaysEvents() {
        return todaysEvents;
    }
    
    public Event getEvent(Time time){
        int index=-1;
        for(Event e : this.todaysEvents){
            if(e.getBeginHour().compareTo(time)==0)
                index=this.todaysEvents.indexOf(e);
        }
        if(index==-1)
            return null;
        else
            return this.todaysEvents.get(index);
    }
    
    public Event getEvent(long id){
        int index=-1;
        for(Event e : this.todaysEvents){
            if(e.getId()==id)
                index=this.todaysEvents.indexOf(e);
        }
        if(index==-1)
            return null;
        else
            return this.todaysEvents.get(index);
    }
    
    public void addEvent(Event e){
        this.todaysEvents.add(e);
    }
    
    public void removeEvent(Event e){
        this.todaysEvents.remove(e);
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }
    
}
