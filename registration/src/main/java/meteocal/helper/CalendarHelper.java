/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milos
 */
public class CalendarHelper {
    
    private List<DayHelper> currentWeek;

    public CalendarHelper(Date firstDay) {
        this.currentWeek = new ArrayList<>(0);
        Date tmp_date = firstDay;
        DayHelper tmp_day;
        for(int i=0;i<7;i++){
            tmp_date = this.getNextDay(tmp_date);
            tmp_day = new DayHelper(tmp_date);
            this.currentWeek.add(tmp_day);
        }
    }
    
    public void moveByOneDayUp(){
        this.currentWeek.remove(0);
        
        this.currentWeek.add(null);
    }
    
    public void moveByOneDayDown(){
    
    }

    private Date getNextDay(Date day){
        String dt = day.toString();  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException ex) {
            Logger.getLogger(CalendarHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        return c.getTime();  // dt is now the new date
    }
    
    public List<DayHelper> getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(List<DayHelper> currentWeek) {
        this.currentWeek = currentWeek;
    }
    
    
    
}
