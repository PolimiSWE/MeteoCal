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
    
    @Inject
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
    
    public void checkWeatherMainFun(){//String city, java.sql.Date dt, Time tm
        //String city = "London";
        //java.sql.Date dt = Date.valueOf("2015-01-28");
        java.sql.Date dt = new java.sql.Date(this.dateChosen.getTime());
        java.sql.Time tt = this.timeChosen;
        this.weatherHelper.setCity(this.city);
        //if there is record WeatherData(City, date) in database return it
        this.wdList = wf.getWeatherDataListFromDB(dt, city);
        if(wdList.isEmpty()){
            //call weather api
            int diff = wf.getDateDiff(dt);
            if(diff == 5){
                //if date is in next 5 days call checkweather5days
                this.weatherHelper.checkWeather5days(city);
                this.wdList = this.weatherHelper.getWdList();
            }
            else if(diff == 16){
                //if date is in next 16 days call checkweather16days
                this.weatherHelper.checkWeather16days(city);
                this.wdList = this.weatherHelper.getWdList();
            }
        } else {//else leave it to scheduler (make scheduler that runs once a day)
               
        }
        
    }
    
}
