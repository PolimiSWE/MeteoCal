package meteocal.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import meteocal.entity.WeatherData;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import sun.tools.jstat.Scale;




/**
 *
 * @author Nemanja
 */
@Startup
@Singleton
public class WeatherHelper {

    private final String API_KEY = "9729dca762d8bae8edbd698333eb40d4";
    private final String API_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private String city;
    private String cnt;
    private String forecastType;
    //current
    //api.openweathermap.org/data/2.5/weather?q=London,uk
    //3 hour in next 5 days
    //api.openweathermap.org/data/2.5/forecast?q=London,us
    //dayly, next 16 days
    //api.openweathermap.org/data/2.5/forecast/daily?q=London&cnt=16
    @Resource
    private TimerService timerService;
    
    private Client client;
    private List<WeatherData> wdList;

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
        this.city = "Milan";
        this.cnt = "16";
        ScheduleExpression everyDay = new ScheduleExpression().second("*/5").minute("*").hour("*");
        timerService.createCalendarTimer(everyDay, new TimerConfig("", false));
    }
    
    
    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void checkWeatherAutoTimer() {
        
        //api.openweathermap.org/data/2.5/forecast/daily?q=London&cnt=16
        String url = getAbsoluteUrl("forecast/daily");
        //?APPID=<key>&q={search-term}
        url = prepareURLParameters(url);
        url = addToUrlApiKey(url);
        url = addToUrlSearchTerm(url, this.city);
        url = addToUrlCountedDays(url, this.cnt);
                
        try {
            String responseString = client.target(url).request().get().readEntity(String.class);
            JSONObject body = new JSONObject(responseString);
            //Get the movies json array
            JSONArray items = body.getJSONArray("list");
	        //Parse json array into array of model objects
            this.wdList = new ArrayList<>(0);
            for (int i = 0; i < items.length(); i++) {
                JSONObject jobj = items.getJSONObject(i);
                WeatherData wd = this.fromJsonDaily(jobj);
                this.wdList.add(wd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void checkWeather5days(String city) {
        this.city = city;
        //api.openweathermap.org/data/2.5/forecast/daily?q=London&cnt=16
        String url = getAbsoluteUrl("forecast");
        //?APPID=<key>&q={search-term}
        url = prepareURLParameters(url);
        url = addToUrlApiKey(url);
        url = addToUrlSearchTerm(url, this.city);
                
        try {
            client = ClientBuilder.newClient();
            String responseString = client.target(url).request().get().readEntity(String.class);
            JSONObject body = new JSONObject(responseString);
            //Get the movies json array
            JSONArray items = body.getJSONArray("list");
	        //Parse json array into array of model objects
            this.wdList = new ArrayList<>(0);
            for (int i = 0; i < items.length(); i++) {
                JSONObject jobj = items.getJSONObject(i);
                WeatherData wd = this.fromJson3hours(jobj);
                this.wdList.add(wd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static WeatherData getClosestWeatherData(List<WeatherData> wdl, Date date, Time time){
        //take only weatherdata for given date "date"
        List<WeatherData> wdlForTheDay = new ArrayList<WeatherData>();
        for(WeatherData wd : wdl){
            Date wddate = wd.getDate();
            if(wddate.equals(date))//
                wdlForTheDay.add(wd);
        }
        if(wdlForTheDay.size() == 1)//probably from daily forecast request
            return wdlForTheDay.get(0);
        else if(wdlForTheDay.size() == 0)
            return null;
        else{//3hour forecast, find the best match
            WeatherData minDiffWd = new WeatherData();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            int hour = calendar.get(Calendar.HOUR);//hour of an event
            //0-23
            for (WeatherData wd : wdlForTheDay) {
            Time wdtime = wd.getHour();
            calendar.setTime(wdtime);
            int wdhour = calendar.get(Calendar.HOUR);
            if(wdhour<=hour)
                minDiffWd = wd;
            }
            return minDiffWd;
        }
        
        
    }

    private String addToUrl(String url, String addedText) {
        return url + addedText;
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    private String prepareURLParameters(String url) {
        return url + "?";
    }

    private String addToUrlApiKey(String url) {
        return url + "APPID=" + API_KEY;
    }

    private String addToUrlSearchTerm(String url, String searchTerm) {
        try {
            return url + "&q=" + URLEncoder.encode(searchTerm, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "UnsupportedEncodingException";
        }
    }
    private String addToUrlCountedDays(String url, String countedDays) {
        return url + "&cnt="+countedDays;
    }
    
    public static WeatherData fromJsonDaily(JSONObject jsonObject) {
        WeatherData wd = new WeatherData();
        try {
            wd.setTemperature(jsonObject.getJSONObject("temp").getDouble("day")-273.15);
        } catch (Exception e) {
            wd.setTemperature(-9999.9);
        }
        try {
            wd.setCloudPercentage(Double.valueOf(Integer.toString(jsonObject.getInt("clouds"))+".0"));
        } catch (Exception e) {
            wd.setCloudPercentage(-9999.9);
        }
        try {
            wd.setPreasure(jsonObject.getDouble("pressure"));
        } catch (Exception e) {
            wd.setPreasure(-9999.9);
        }
        try {
            wd.setWindSpeed(jsonObject.getDouble("speed"));
        } catch (Exception e) {
            wd.setWindSpeed(-9999.9);
        }
        try {
            long unixSeconds = new Timestamp(jsonObject.getInt("dt")).getTime();
            Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // the format of your date
            String formattedTime = sdf.format(date);
            Time time = Time.valueOf(formattedTime);
            sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
            String formattedDate = sdf.format(date);
            date = Date.valueOf(formattedDate);
            wd.setDate(date);
            wd.setHour(new Time(date.getTime()));
        } catch (Exception e) {
            wd.setDate(Date.valueOf("1970-1-1 22:22:22"));
            wd.setHour(Time.valueOf("22:22:22"));
        }
        // Return new object
        return wd;
    }
    
    public static WeatherData fromJson3hours(JSONObject jsonObject){
        WeatherData wd = new WeatherData();
        try {
            wd.setTemperature(jsonObject.getJSONObject("main").getDouble("temp")-273.15);
        } catch (Exception e) {
            wd.setTemperature(-9999.9);
        }
        try {
            wd.setCloudPercentage(Double.valueOf(Integer.toString(jsonObject.getJSONObject("clouds").getInt("all"))+".0"));
        } catch (Exception e) {
            wd.setCloudPercentage(-9999.9);
        }
        try {
            wd.setPreasure(jsonObject.getJSONObject("main").getDouble("pressure"));
        } catch (Exception e) {
            wd.setPreasure(-9999.9);
        }
        try {
            wd.setWindSpeed(jsonObject.getJSONObject("wind").getDouble("speed"));
        } catch (Exception e) {
            wd.setWindSpeed(-9999.9);
        }
        try {
            long unixSeconds = new Timestamp(jsonObject.getInt("dt")).getTime();
            Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // the format of your date
            String formattedTime = sdf.format(date);
            Time time = Time.valueOf(formattedTime);
            sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
            String formattedDate = sdf.format(date);
            date = Date.valueOf(formattedDate);
            wd.setDate(date);
            wd.setHour(new Time(date.getTime()));
        } catch (Exception e) {
            wd.setDate(Date.valueOf("1970-1-1 22:22:22"));
            wd.setHour(Time.valueOf("22:22:22"));
        }
        // Return new object
        return wd;
    }

    

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public List<WeatherData> getWdList() {
        return wdList;
    }

    public void setWdList(List<WeatherData> wdList) {
        this.wdList = wdList;
    }
    
    public static void main(String[] args) {
     WeatherHelper helper = new WeatherHelper();
     helper.checkWeather5days("London");
     List<WeatherData> dataList = helper.getWdList();
     System.out.println("London weather:");
        for (WeatherData wd : dataList) {
            System.out.println(wd.getDate().toString()+"hr:"+wd.getHour().toString()+" cl:"+
                    wd.getCloudPercentage().toString()+" pr:"+wd.getPreasure().toString()+" tmp:"+
                    wd.getTemperature()+" wnd:"+wd.getWindSpeed());
        }
     WeatherData wd = helper.getClosestWeatherData(dataList, new Date(2015, 1, 18), new Time(19,0,0));
     
     System.out.println("Closest to 18.1.2015 19h is:");
            System.out.println(wd.getDate().toString()+"hr:"+wd.getHour().toString()+" cl:"+
                    wd.getCloudPercentage().toString()+" pr:"+wd.getPreasure()+" tmp:"+
                    wd.getTemperature()+" wnd:"+wd.getWindSpeed());
     System.exit(0);
     }
}
