package meteocal.backgroundworker;

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
    
    @Schedule(second = "*", minute = "*/1", hour = "*", persistent = false)
    @SuppressWarnings("CallToPrintStackTrace")
    public void checkWeatherAutoTimer() {
        weatherHelper.checkWeather16days(weatherHelper.getCity());
    }
}
