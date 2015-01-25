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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private Client client;
    private List<WeatherData> wdList;

    private static final Map<Integer, String> badWeatherCodes;

    static {
        Map<Integer, String> aMap = new HashMap<Integer, String>();
        addBadWeatherCodes(aMap);
        badWeatherCodes = Collections.unmodifiableMap(aMap);
    }

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
        this.city = "Milan";
        this.cnt = "16";
        wdList = new ArrayList<>();
        //ScheduleExpression everyDay = new ScheduleExpression().second("*/5").minute("*").hour("*");
        //timerService.createCalendarTimer(everyDay, new TimerConfig("", false));
    }

    public void checkWeather16days(String city) {
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
            this.wdList = new ArrayList<>(0);
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

    public List<WeatherData> getWDLforTheDay(List<WeatherData> wdl, Date date) {
        //take only weatherdata for given date "date"
        List<WeatherData> wdlForTheDay = new ArrayList<>();
        for (WeatherData wd : wdl) {
            java.util.Date wddate = wd.getDate();
            if (wddate.equals(date))//
            {
                wdlForTheDay.add(wd);
            }
        }
        return wdlForTheDay;
    }

    public Boolean dayHasGoodWeatherData(List<WeatherData> wdl) {
        for (WeatherData wd : wdl) {
            if (!isBadWeatherCode(wd.getCode())) {
                return true;
            }
        }
        return false;
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
        try {
            wd.setDescription(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
        } catch (Exception e) {
            wd.setDescription("No Description");
        }

        try {
            wd.setIcon(jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon"));
        } catch (Exception e) {
            wd.setIcon("No Icon");
        }

        try {
            wd.setCode(jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id"));
        } catch (Exception e) {
            wd.setCode(800);
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

        try {
            wd.setDescription(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
        } catch (Exception e) {
            wd.setDescription("No Description");
        }

        try {
            wd.setIcon(jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon"));
        } catch (Exception e) {
            wd.setIcon("No Icon");
        }

        try {
            wd.setCode(jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id"));
        } catch (Exception e) {
            wd.setCode(800);
        }
        // Return new object
        return wd;
    }

    public void checkWeatherMainFun(String city, java.sql.Date dt, Time tt) {
        this.setCity(this.city);
        //if there is record WeatherData(City, date) in database 
        if (wdList != null) {
            if (wdList.isEmpty()) {
                //call weather api
                int diff = this.getDateDiff(dt);
                if (diff == 5) {
                    //if date is in next 5 days call checkweather5days
                    this.checkWeather5days(city);
                    this.wdList = this.getWdList();
                } else if (diff == 16) {
                    //if date is in next 16 days call checkweather16days
                    this.checkWeather16days(city);
                    this.wdList = this.getWdList();
                }
            } else {//else leave it to scheduler (make scheduler that runs once a day)

            }
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

    private static void addBadWeatherCodes(Map<Integer, String> aMap) {
        //http://openweathermap.org/weather-conditions
        aMap.put(200, "thunderstorm with light rain");
        aMap.put(201, "thunderstorm with rain");
        aMap.put(202, "thunderstorm with heavy rain");
        aMap.put(210, "light thunderstorm");
        aMap.put(211, "thunderstorm");
        aMap.put(212, "heavy thunderstorm");
        aMap.put(221, "ragged thunderstorm");
        aMap.put(230, "thunderstorm with light drizzle");
        aMap.put(231, "thunderstorm with drizzle");
        aMap.put(232, "thunderstorm with heavy drizzle");
        aMap.put(300, "light intensity drizzle");
        aMap.put(301, "drizzle");
        aMap.put(302, "heavy intensity drizzle");
        aMap.put(310, "light intensity drizzle rain");
        aMap.put(311, "drizzle rain");
        aMap.put(312, "heavy intensity drizzle rain");
        aMap.put(313, "shower rain and drizzle");
        aMap.put(314, "heavy shower rain and drizzle");
        aMap.put(321, "shower drizzle");
        aMap.put(500, "light rain");
        aMap.put(501, "moderate rain");
        aMap.put(502, "heavy intensity rain");
        aMap.put(503, "very heavy rain");
        aMap.put(504, "extreme rain");
        aMap.put(511, "freezing rain");
        aMap.put(520, "light intensity shower rain");
        aMap.put(521, "shower rain");
        aMap.put(522, "heavy intensity shower rain");
        aMap.put(531, "ragged shower rain");
        aMap.put(600, "light snow");
        aMap.put(601, "snow");
        aMap.put(602, "heavy snow");
        aMap.put(611, "sleet");
        aMap.put(612, "shower sleet");
        aMap.put(615, "light rain and snow");
        aMap.put(616, "rain and snow");
        aMap.put(620, "light shower snow");
        aMap.put(621, "shower snow");
        aMap.put(622, "heavy shower snow");
        aMap.put(701, "mist");
        aMap.put(711, "smoke");
        aMap.put(721, "haze");
        aMap.put(731, "sand, dust whirls");
        aMap.put(741, "fog");
        aMap.put(751, "sand");
        aMap.put(761, "dust");
        aMap.put(762, "volcanic ash");
        aMap.put(771, "squalls");
        aMap.put(781, "tornado");
        aMap.put(900, "tornado");
        aMap.put(901, "tropical storm");
        aMap.put(902, "hurricane");
        aMap.put(903, "cold");
        aMap.put(904, "hot");
        aMap.put(905, "windy");
        aMap.put(906, "hail");
        aMap.put(957, "high wind, near gale");
        aMap.put(958, "gale");
        aMap.put(959, "severe gale");
        aMap.put(960, "storm");
        aMap.put(961, "violent storm");
        aMap.put(962, "hurricane");
    }

    public boolean isBadWeatherCode(Integer code) {
        return badWeatherCodes.containsKey(code);
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

    public int getDateDiff(java.sql.Date dt1) {
        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();

        long d1 = dt1.getTime();
        long d2 = cal.getTimeInMillis();

        int diff = (int) (d1 - d2) / (1000 * 60 * 60 * 24);//Math.abs(
        if (diff < 0) {
            return -1;
        } else if (diff <= 5) {
            return 5;
        } else if (diff <= 16) {
            return 16;
        } else {
            return -2;
        }
    }

    public int getDateDiff(java.sql.Date dt1, java.sql.Date dt2) {
        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();

        long d1 = dt1.getTime();
        long d2 = dt2.getTime();

        int diff = (int) (d1 - d2) / (1000 * 60 * 60 * 24);
        return diff;
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
        if (wdList != null) {
            this.wdList = wdList;
        } else {
            this.wdList = new ArrayList<>();
        }
    }
}
