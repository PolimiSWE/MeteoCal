/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import meteocal.boundary.WeatherDataFacade;
import meteocal.entity.Event;
import meteocal.entity.WeatherData;
import meteocal.helper.WeatherHelper;

/**
 *
 * @author Milos
 */
@Named(value="weatherDataBean")
@SessionScoped
public class WeatherDataBean implements Serializable{
    
    @EJB
    WeatherDataFacade wf;
    
    
    private WeatherHelper weatherHelper;
    
    private List<WeatherData> wdList;
    private Date dateChosen;
    private Time timeChosen;
    private Date timeAsDateChosen;
    private String city;
    
    public WeatherDataBean() {
    }
    
    @PostConstruct
    public void init() {
        if(this.weatherHelper == null)
            this.weatherHelper = new WeatherHelper();
        this.wdList = this.weatherHelper.getWdList();
        this.dateChosen = new Date(Calendar.getInstance().getTimeInMillis());
        this.timeChosen = new Time(Calendar.getInstance().getTimeInMillis());
        this.timeAsDateChosen = new Date(Calendar.getInstance().getTimeInMillis());
    }
    
    public void testMethod(){
        this.weatherHelper.testFun();
    }

    public WeatherHelper getWeatherHelper() {
        return weatherHelper;
    }

    public void setWeatherHelper(WeatherHelper weatherHelper) {
        this.weatherHelper = weatherHelper;
    }

    public List<WeatherData> getWdList() {
        return wdList;
    }

    public void setWdList(List<WeatherData> wdList) {
        this.wdList = wdList;
    }

    public Date getDateChosen() {
        return dateChosen;
    }

    public void setDateChosen(Date dateChosen) {
        this.dateChosen = dateChosen;
    }

    public Date getTimeAsDateChosen() {
        timeAsDateChosen.setTime(this.timeChosen.getTime());
        return timeAsDateChosen;
    }

    public void setTimeAsDateChosen(Date timeAsDateChosen) {
        this.timeAsDateChosen = timeAsDateChosen;
        this.timeChosen.setTime(timeAsDateChosen.getTime());
    }
    
    

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public void checkWeatherMainFun(){
        //String city = "London";
        //java.sql.Date dt = Date.valueOf("2015-01-28");
        java.sql.Date dt = new java.sql.Date(this.dateChosen.getTime());
        java.sql.Time tt = this.timeChosen;
        this.weatherHelper.setCity(this.city);
        this.weatherHelper.setWdList(wf.getWeatherDataListFromDB(city, dt));
        this.weatherHelper.checkWeatherMainFun(this.city, dt, tt);
        this.setWdList(this.weatherHelper.getWdList());
        this.saveWdList();
    }

    private void saveWdList() {
        this.wf.saveWdList(this.wdList);
    }
    
    public java.sql.Date getBestDate(java.util.Date dtToConvert, String city){
        java.sql.Date dtEvent = new java.sql.Date(dtToConvert.getTime());
        dtEvent = this.weatherHelper.cutOffTheTime(dtEvent);
        //java.sql.Time ttEvent = evt.getBeginHour();
        for(int i=1; i<=16; i++){
            java.sql.Date dtPossible = this.weatherHelper.addDays(dtEvent, i);
            List<WeatherData> wdl = wf.getWeatherDataListFromDB(city, dtEvent);
            if(this.weatherHelper.dayHasGoodWeatherData(wdl))
                return dtPossible;
        }
        return null;//or dtEvent
    }
}
