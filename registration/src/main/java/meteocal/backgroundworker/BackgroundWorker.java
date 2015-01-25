package meteocal.backgroundworker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import meteocal.boundary.EventFacade;
import meteocal.boundary.WeatherDataFacade;
import meteocal.entity.Event;
import meteocal.entity.WeatherData;
import meteocal.helper.WeatherHelper;

/**
 *
 * @author Nemanja
 */
@Startup
@Singleton
public class BackgroundWorker {

    @EJB
    EventFacade em;

    @EJB
    WeatherDataFacade wm;


    //@PersistenceContext(unitName = "authPU")
    //EntityManager manager;


    private WeatherHelper weatherHelper;
    private List<String> citiesList;
    private List<String> citiesList2;
    private boolean startDailyProcedure;
    private boolean startDailyProcedure2;
    private java.sql.Date dt1;
    private java.sql.Date dt2;
    private java.sql.Date dt3;
    private java.sql.Date dt4;
    private java.sql.Date dt5;
    private java.sql.Date dt6;

    @PostConstruct
    public void init() {
        if (this.weatherHelper == null) {
            this.weatherHelper = new WeatherHelper();
            this.weatherHelper.setCity("Milan");
            this.citiesList = new ArrayList<>();
            this.citiesList2 = new ArrayList<>();
            this.startDailyProcedure = false;
            this.startDailyProcedure2 = false;
            dt1 = new java.sql.Date(System.currentTimeMillis());
            dt1 = cutOffTheTime(dt1);
            dt2 = addDays(dt1, 1);
            dt3 = addDays(dt2, 1);
            dt4 = addDays(dt3, 1);
            dt5 = addDays(dt4, 1);
            dt6 = addDays(dt5, 1);
        }
    }

    public WeatherHelper getWeatherHelper() {
        if (this.weatherHelper == null) {
            this.weatherHelper = new WeatherHelper();
            this.weatherHelper.setCity("Milan");
            return this.weatherHelper;
        } else {
            return weatherHelper;
        }
    }
    @Schedule(minute = "*/2", hour = "5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23", persistent = false)
    @SuppressWarnings("CallToPrintStackTrace")
    public void debugTimerMethod() {
     setCityList();
     deleteWDHistory(1);
     //clearNotifyOwnerHistory(3);
     for (String ct : citiesList) {
     checkWDforEvents(ct);/////////////////////////////
     }
     citiesList.clear();
     }
    
    //http://docs.oracle.com/javaee/6/tutorial/doc/bnboy.html
    @Schedule(hour = "0", persistent = false)
    @SuppressWarnings("CallToPrintStackTrace")
    public void saveCityList() {
        if (this.startDailyProcedure == false) {
            setCityList();
            deleteWDHistory(1);
            clearNotifyOwnerHistory(1);
            startDailyProcedure = true;
            startDailyProcedure2 = true;
        }
    }

    public void deleteWDHistory(int days) {
        java.sql.Date day = new java.sql.Date(System.currentTimeMillis());
        day = cutOffTheTime(day);//today
        for (int i = 1; i <= days; i++) {
            day = addDays(day, -1);//starts from yesterday
            wm.deleteWdByDate(day);
        }
    }

    public void setCityList() {
        //picks up all the events for the next three days
        dt1 = new java.sql.Date(System.currentTimeMillis());
        dt1 = cutOffTheTime(dt1);
        dt2 = addDays(dt1, 1);
        dt3 = addDays(dt2, 1);
        dt4 = addDays(dt3, 1);
        dt5 = addDays(dt4, 1);
        dt6 = addDays(dt5, 1);
        List<Event> eventsDay1 = em.findEventsForTheDay(dt1);
        List<Event> eventsDay2 = em.findEventsForTheDay(dt2);
        List<Event> eventsDay3 = em.findEventsForTheDay(dt3);
        //pick up all the cities from events. do remove_duplicates
        citiesList = new ArrayList<>();
        for (Event evt : eventsDay1) {
            citiesList.add(evt.getCity());
        }
        for (Event evt : eventsDay2) {
            citiesList.add(evt.getCity());
        }
        for (Event evt : eventsDay3) {
            citiesList.add(evt.getCity());
        }
        //remove duplicates
        HashSet hs = new HashSet();
        hs.addAll(citiesList);
        citiesList.clear();
        citiesList.addAll(hs);
        citiesList2.clear();
        citiesList2.addAll(hs);
        //duplicates removed..
    }

    //http://docs.oracle.com/javaee/6/tutorial/doc/bnboy.html
    @Schedule(minute = "*/10", hour = "0,1,2", persistent = false)//second = "*", minute = "*", hour = "*/23", persistent = false)
    @SuppressWarnings("CallToPrintStackTrace")
    public void updateOneCityWeather() {
        ////////////////////////////////////////////////
        //do the update checkWeather5days (WeatherData available on three hours) for each city on 10 minutes
        if (this.startDailyProcedure == true) {
            if (!this.citiesList.isEmpty()) {
                String ct = citiesList.get(0);
                doWDupdateForCity(ct);
                checkWDforEvents(ct);
                this.citiesList.remove(ct);
            } else {
                this.startDailyProcedure = false;
            }
        }
    }

    public void doWDupdateForCity(String ct) {
        this.weatherHelper.setCity(ct);
        this.weatherHelper.checkWeather5days(ct);
        List<WeatherData> wdListForCt = this.weatherHelper.getWdList();
        //clear the data for 4th, 5th and possibly 6th day in weather data list
        wdListForCt = clearListForTheDay(wdListForCt, dt4);
        wdListForCt = clearListForTheDay(wdListForCt, dt5);
        wdListForCt = clearListForTheDay(wdListForCt, dt6);
        if (!wdListForCt.isEmpty()) {
            //if we got fresh data, we can delete the old ones from db
            wm.deleteWdByCityAndDate(ct, dt1);
            wm.deleteWdByCityAndDate(ct, dt2);
            wm.deleteWdByCityAndDate(ct, dt3);
            //deleted, now we can add the new ones to the db
            wm.saveWdList(wdListForCt);
        }
    }

    public void checkWDforEvents(String ct) {
        List<Event> toPersist = new ArrayList<>();
        //for  city get the weather data for exact day  and events for exact day and city

        List<WeatherData> wdListDay1 = wm.getWeatherDataListFromDB(ct, dt1);//weatherHelper.getWDLforTheDay(wdListForCt, dt1);
        List<WeatherData> wdListDay2 = wm.getWeatherDataListFromDB(ct, dt2);
        List<WeatherData> wdListDay3 = wm.getWeatherDataListFromDB(ct, dt3);

        List<Event> eventsDay1 = em.findEventsForTheDayAndTheCity(ct, dt1);
        List<Event> eventsDay2 = em.findEventsForTheDayAndTheCity(ct, dt2);
        List<Event> eventsDay3 = em.findEventsForTheDayAndTheCity(ct, dt3);
        //for every event get closest wd from obtained weather data list
        for (Event evt : eventsDay1) {
            WeatherData wdc = this.weatherHelper.getClosestWeatherData(wdListDay1, dt1, evt.getBeginHour());
            if (wdc != null) {
                //check if the weather code is bad, if it is set event.notifyOwner to true
                Integer code = wdc.getCode();
                if (code != null) {
                    if (this.weatherHelper.isBadWeatherCode(code)) {
                        evt.setNotifyOwner(true);
                        //and add taht event to persist list
                        toPersist.add(evt);
                    }
                }
            }
        }
        for (Event evt : eventsDay2) {
            WeatherData wdc = this.weatherHelper.getClosestWeatherData(wdListDay2, dt2, evt.getBeginHour());
            if (wdc != null) {
                //check if the weather code is bad, if it is set event.notifyOwner to true
                Integer code = wdc.getCode();
                if (code != null) {
                    if (this.weatherHelper.isBadWeatherCode(code)) {
                        evt.setNotifyOwner(true);
                        //and add taht event to persist list
                        toPersist.add(evt);
                    }
                }
            }
        }
        for (Event evt : eventsDay3) {
            WeatherData wdc = this.weatherHelper.getClosestWeatherData(wdListDay3, dt3, evt.getBeginHour());
            if (wdc != null) {
                //check if the weather code is bad, if it is set event.notifyOwner to true
                Integer code = wdc.getCode();
                if (code != null) {
                    if (this.weatherHelper.isBadWeatherCode(code)) {
                        evt.setNotifyOwner(true);
                        //and add taht event to persist list
                        toPersist.add(evt);
                    }
                }
            }
        }
        //persist the events
        for (Event evt : toPersist) {
            em.save(evt);
        }
    }

    @Schedule(minute = "*/10", hour = "3,4", persistent = false)
    @SuppressWarnings("CallToPrintStackTrace")
    public void checkWeatherAutoTimer() {
        //do the update checkWeathe16days for each city on 10 minutes between 3 and 4 in the morning
        if (this.startDailyProcedure2 == true) {
            if (!this.citiesList2.isEmpty()) {
                java.sql.Date dt = new java.sql.Date(System.currentTimeMillis());
                dt = cutOffTheTime(dt);//today
                dt = addDays(dt, 10);
                java.sql.Time tt = Time.valueOf("12:00:00");
                //call the function for one city
                String ct = citiesList2.get(0);
                this.weatherHelper.setCity(ct);
                //we get weather data for specified date, for given city
                this.weatherHelper.setWdList(wm.getWeatherDataListFromDB(ct, dt));
                //main fun first checks if there are records in db, if not calls the api
                this.weatherHelper.checkWeatherMainFun(ct, dt, tt);
                this.wm.saveWdList(this.weatherHelper.getWdList());
                this.citiesList2.remove(ct);
            } else {
                this.startDailyProcedure2 = false;
            }
        }
    }

    public List<WeatherData> clearListForTheDay(List<WeatherData> wdl, java.sql.Date dt) {
        List<WeatherData> tmp = new ArrayList<>();
        for (WeatherData wd : wdl) {
            int diff = this.weatherHelper.getDateDiff(new java.sql.Date(wd.getDate().getTime()), dt);
            if (diff != 0) {
                tmp.add(wd);
            }
        }
        return tmp;
    }

    public void clearNotifyOwnerHistory(int days) {
        java.sql.Date day = new java.sql.Date(System.currentTimeMillis());
        day = cutOffTheTime(day);//today
        for (int i = 1; i <= days; i++) {
            day = addDays(day, -1);//starts from yesterday
            List<Event> toPersist = em.findDirtyEventsForTheDay(day);
            for (Event evt : toPersist) {
                evt.setNotifyOwner(false);
                em.save(evt);
            }
        }
    }

    public java.sql.Date cutOffTheTime(java.sql.Date dtOld) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dtOld);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        dtOld.setTime(cal.getTimeInMillis());
        return dtOld;
    }

    public java.sql.Date addDays(java.sql.Date dtOld, int days) {
        return new java.sql.Date(dtOld.getTime() + (days) * 24 * 60 * 60 * 1000);
    }

}
