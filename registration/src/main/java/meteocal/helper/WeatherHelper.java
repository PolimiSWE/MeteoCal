package meteocal.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TimerService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import meteocal.bean.MailSendingBean;
import meteocal.boundary.WeatherDataFacade;
import meteocal.entity.WeatherData;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Nemanja
 */
public class WeatherHelper {
    @EJB
    WeatherDataFacade wf;
    
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
        //ScheduleExpression everyDay = new ScheduleExpression().second("*/5").minute("*").hour("*");
        //timerService.createCalendarTimer(everyDay, new TimerConfig("", false));
    }

    public void checkWeather16days(String city){
        this.city = city;
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
            sortWeatherList();
        } catch (Exception e) {
            this.wdList = new ArrayList<>(); // server returned empty JSON
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
            sortWeatherList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WeatherData getClosestWeatherData(List<WeatherData> wdl, Date date, Time time) {
        //take only weatherdata for given date "date"
        List<WeatherData> wdlForTheDay = new ArrayList<>();
        for (WeatherData wd : wdl) {
            java.util.Date wddate = wd.getDate();
            if (wddate.equals(date))//
            {
                wdlForTheDay.add(wd);
            }
        }
        if (wdlForTheDay.size() == 1)//probably from daily forecast request
        {
            return wdlForTheDay.get(0);
        } else if (wdlForTheDay.isEmpty()) {
            return null;
        } else {//3hour forecast, find the best match
            WeatherData minDiffWd = new WeatherData();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);//hour of an event
            //0-23
            for (WeatherData wd : wdlForTheDay) {
                Time wdtime = wd.getHour();
                calendar.setTime(wdtime);
                int wdhour = calendar.get(Calendar.HOUR_OF_DAY);
                if (wdhour <= hour) {
                    minDiffWd = wd;
                }
            }
            return minDiffWd;
        }

    }

    public void sortWeatherList() {
        Comparator<WeatherData> customComparator = new Comparator<WeatherData>() {

            @Override
            public int compare(WeatherData o1, WeatherData o2) {
                Calendar calendar = Calendar.getInstance();
                if (o1.getDate().equals(o2.getDate())) {
                    calendar.setTime(o1.getHour());
                    int hour1 = calendar.get(Calendar.HOUR_OF_DAY);
                    calendar.setTime(o2.getHour());
                    int hour2 = calendar.get(Calendar.HOUR_OF_DAY);
                    return hour1 - hour2;
                } else {
                    return o1.getDate().compareTo(o2.getDate());
                }
            }

        };
        Collections.sort(wdList, customComparator);
        wdList.removeAll(Collections.singleton(null));
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
        return url + "&cnt=" + countedDays;
    }

    public WeatherData fromJsonDaily(JSONObject jsonObject) {
        WeatherData wd = new WeatherData();
        try {
            double temp = jsonObject.getJSONObject("temp").getDouble("day") - 273.15;
            temp = (double) Math.round(temp * 100) / 100;
            wd.setTemperature(temp);
        } catch (Exception e) {
            wd.setTemperature(-9999.9);
        }
        try {
            wd.setCloudPercentage(Double.valueOf(Integer.toString(jsonObject.getInt("clouds")) + ".0"));
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
            java.util.Date date = new java.util.Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // the format of your date
            String formattedTime = sdf.format(date);
            Time time = Time.valueOf(formattedTime);
            sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
            String formattedDate = sdf.format(date);
            //DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");
            //Date date = formatter.parse(testDate);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = java.sql.Date.valueOf(formattedDate);
            //date = formatter.parse(formattedDate);
            wd.setDate(date);
            wd.setHour(time);
        } catch (Exception e) {
            wd.setDate(java.sql.Date.valueOf("1970-1-1 22:22:22"));
            wd.setHour(Time.valueOf("22:22:22"));
        }
        // Return new object
        try {
            wd.setCity(this.getCity());
        } catch (Exception e) {
            wd.setCity("Invalid");
        }
        return wd;
    }

    @SuppressWarnings("UseSpecificCatch")
    public WeatherData fromJson3hours(JSONObject jsonObject) {
        WeatherData wd = new WeatherData();
        try {
            double temp = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            temp = (double) Math.round(temp * 100) / 100;
            wd.setTemperature(temp);
        } catch (Exception e) {
            wd.setTemperature(-9999.9);
        }
        try {
            wd.setCloudPercentage(Double.valueOf(Integer.toString(jsonObject.getJSONObject("clouds").getInt("all")) + ".0"));
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
            Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // the format of your date
            String formattedTime = sdf.format(date);
            Time time = Time.valueOf(formattedTime);
            sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
            String formattedDate = sdf.format(date);
            date = java.sql.Date.valueOf(formattedDate);
            wd.setDate(date);
            wd.setHour(time);
        } catch (Exception e) {
            wd.setDate(java.sql.Date.valueOf("1970-1-1 22:22:22"));
            wd.setHour(Time.valueOf("22:22:22"));
        }
        
        try {
            wd.setCity(this.getCity());
        } catch (Exception e) {
            wd.setCity("Invalid");
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
    
    public void checkWeatherMainFun(String city, java.sql.Date dt, Time tt){
        this.setCity(this.city);
        //if there is record WeatherData(City, date) in database return it
        this.wdList = wf.getWeatherDataListFromDB(dt, city);
        if(wdList.isEmpty()){
            //call weather api
            int diff = wf.getDateDiff(dt);
            if(diff == 5){
                //if date is in next 5 days call checkweather5days
                this.checkWeather5days(city);
                this.wdList = this.getWdList();
            }
            else if(diff == 16){
                //if date is in next 16 days call checkweather16days
                this.checkWeather16days(city);
                this.wdList = this.getWdList();
            }
        } else {//else leave it to scheduler (make scheduler that runs once a day)
               
        }
        
    }
    
    public void testFun() {
        //WeatherHelper helper = new WeatherHelper();
        this.checkWeather5days("London");
        List<WeatherData> dataList = this.getWdList();
        System.out.println("London weather:");
        for (WeatherData wd : dataList) {
            System.out.println(wd.getDate().toString() + "hr:" + wd.getHour().toString() + " cl:"
                    + wd.getCloudPercentage().toString() + " pr:" + wd.getPreasure().toString() + " tmp:"
                    + wd.getTemperature() + " wnd:" + wd.getWindSpeed());
        }
        Date dt = java.sql.Date.valueOf("2015-1-18");
        Time tm = Time.valueOf("19:00:00");
        WeatherData wd = this.getClosestWeatherData(dataList, dt, tm);

        System.out.println("Closest to 18.1.2015 19h is:");
        System.out.println(wd.getDate().toString() + "hr:" + wd.getHour().toString() + " cl:"
                + wd.getCloudPercentage().toString() + " pr:" + wd.getPreasure() + " tmp:"
                + wd.getTemperature() + " wnd:" + wd.getWindSpeed());
        ///////////////////////////////////////////////////////////
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        MailSendingBean msb = (MailSendingBean) context.getBean("mailSendingBean");
        msb.sendMail("meteocaltester@gmail.com",
    		   "nemtajo@yahoo.com",
    		   "Subject-MeteoCal", 
    		   "Testing only \n\n Sent from Java project");

    }
    
    
}
