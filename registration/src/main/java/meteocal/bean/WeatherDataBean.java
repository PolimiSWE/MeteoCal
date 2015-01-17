/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import meteocal.entity.WeatherData;
import meteocal.helper.WeatherHelper;

/**
 *
 * @author Milos
 */
@Named(value="weatherDataBean")
@SessionScoped
public class WeatherDataBean implements Serializable{
    
    @Inject
    private WeatherHelper weatherHelper;
    
    private List<WeatherData> wdList;

    public WeatherDataBean() {
    }
    
    @PostConstruct
    public void init() {
        if(this.weatherHelper == null)
            this.weatherHelper = new WeatherHelper();
        this.wdList = this.weatherHelper.getWdList();
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
    
    
}
