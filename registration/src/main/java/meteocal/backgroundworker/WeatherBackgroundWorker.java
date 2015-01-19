package meteocal.backgroundworker;

import java.sql.Time;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import meteocal.helper.WeatherHelper;

/**
 *
 * @author Nemanja
 */
@Startup
@Singleton
public class WeatherBackgroundWorker {
    
    private WeatherHelper weatherHelper;
    
    @PostConstruct
    public void init() {
        if(this.weatherHelper == null){
            this.weatherHelper = new WeatherHelper();
            this.weatherHelper.setCity("Milan");
        }
        //ScheduleExpression everyDay = new ScheduleExpression().second("*/5").minute("*").hour("*");
        //timerService.createCalendarTimer(everyDay, new TimerConfig("", false));
    }

    public WeatherHelper getWeatherHelper() {
        if(this.weatherHelper == null){
            this.weatherHelper = new WeatherHelper();
            this.weatherHelper.setCity("Milan");
            return this.weatherHelper;
        } else
            return weatherHelper;
    }
    
    @Schedule(second = "*", minute = "*", hour = "*/24", persistent = false)
    @SuppressWarnings("CallToPrintStackTrace")
    public void checkWeatherAutoTimer() {
        Calendar cal = Calendar.getInstance();
        //gets current date from calendar
        java.sql.Date dt = new java.sql.Date(cal.getTimeInMillis());
        java.sql.Time tt = Time.valueOf("12:00:00");
        weatherHelper.checkWeatherMainFun(weatherHelper.getCity(), dt, tt);
    }
}
