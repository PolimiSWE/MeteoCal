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

    public String processInputStream(InputStream in) {
        try {

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            return responseStrBuilder.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "InputStream error";
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
    
    
}
