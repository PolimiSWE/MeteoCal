/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import meteocal.entity.WeatherData;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author Nemanja
 */
@RunWith(Arquillian.class)
public class WeatherDataFacadeIT {
    
    @EJB
    WeatherDataFacade wdf;
    
    @PersistenceContext
    EntityManager em;

    @Deployment
    public static WebArchive createArchiveAndDeploy() {

        return ShrinkWrap.create(WebArchive.class)
                .addClass(WeatherDataFacade.class)
                .addPackage(WeatherData.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/beans.xml"));
    }

    @Test
    public void WeatherDataFacadeShouldBeInjected() {
        assertNotNull(wdf);
    }

    @Test
    public void EntityManagerShouldBeInjected() {
        assertNotNull(em);
    }

    private Long intToLong(int number) {
        return Integer.toUnsignedLong(number);
    }

    @Test
    public void testSaveWdList() {
        List<WeatherData> wdList = new ArrayList<>();
        WeatherData wd1 = new WeatherData();
        wd1.setId(intToLong(220000000));
        wd1.setCloudPercentage(34.5);
        wd1.setDate(Date.valueOf("2015-01-25"));
        wd1.setHour(Time.valueOf("00:00:00"));
        wd1.setPreasure(998.5);
        wd1.setTemperature(28.7);
        wd1.setWindSpeed(4.0);
        wd1.setCity("Milan");
        wd1.setDescription("sky is clear");
        wd1.setIcon("01d");
        wd1.setCode(800);
        wdList.add(wd1);
        WeatherData wd2 = new WeatherData();
        wd2.setId(intToLong(220000001));
        wd2.setCloudPercentage(49.5);
        wd2.setDate(Date.valueOf("2015-01-26"));
        wd2.setHour(Time.valueOf("00:00:00"));
        wd2.setPreasure(997.6);
        wd2.setTemperature(29.2);
        wd2.setWindSpeed(3.0);
        wd2.setCity("Belgrade");
        wd2.setDescription("sky is clear");
        wd2.setIcon("01d");
        wd2.setCode(800);
        wdList.add(wd2);
        wdf.saveWdList(wdList);
        
        try {
            WeatherData wdt1 = wdf.find(intToLong(220000000));
            WeatherData wdt2 = wdf.find(intToLong(220000001));
            assertTrue((wdt1!=null) && (wdt2!=null));
            //assertEquals(2, wdf.getDbOutput().size());
        } catch (Exception e) {
            Logger.getLogger(WeatherDataFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void testGetDbOutput() {
        assertNotNull(wdf.getDbOutput());
    }

    @Test
    public void testGetWeatherDataListFromDB() {
        List<WeatherData> wdList = new ArrayList<>();
        WeatherData wd1 = new WeatherData();
        wd1.setId(intToLong(220000000));
        wd1.setCloudPercentage(34.5);
        wd1.setDate(Date.valueOf("2015-01-25"));
        wd1.setHour(Time.valueOf("00:00:00"));
        wd1.setPreasure(998.5);
        wd1.setTemperature(28.7);
        wd1.setWindSpeed(4.0);
        wd1.setCity("Milan");
        wd1.setDescription("sky is clear");
        wd1.setIcon("01d");
        wd1.setCode(800);
        wdList.add(wd1);
        WeatherData wd2 = new WeatherData();
        wd2.setId(intToLong(220000001));
        wd2.setCloudPercentage(49.5);
        wd2.setDate(Date.valueOf("2015-01-25"));
        wd2.setHour(Time.valueOf("00:00:00"));
        wd2.setPreasure(997.6);
        wd2.setTemperature(29.2);
        wd2.setWindSpeed(3.0);
        wd2.setCity("Belgrade");
        wd2.setDescription("sky is clear");
        wd2.setIcon("01d");
        wd2.setCode(800);
        wdList.add(wd2);
        wdf.saveWdList(wdList);
        
        try {
            assertEquals(1, wdf.getWeatherDataListFromDB("Milan", Date.valueOf("2015-01-25")).size());
        } catch (Exception e) {
            Logger.getLogger(WeatherDataFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void testDeleteWdByCityAndDate() {
        List<WeatherData> wdList = new ArrayList<>();
        WeatherData wd1 = new WeatherData();
        wd1.setId(intToLong(220000000));
        wd1.setCloudPercentage(34.5);
        wd1.setDate(Date.valueOf("2015-01-25"));
        wd1.setHour(Time.valueOf("00:00:00"));
        wd1.setPreasure(998.5);
        wd1.setTemperature(28.7);
        wd1.setWindSpeed(4.0);
        wd1.setCity("Milan");
        wd1.setDescription("sky is clear");
        wd1.setIcon("01d");
        wd1.setCode(800);
        wdList.add(wd1);
        WeatherData wd2 = new WeatherData();
        wd2.setId(intToLong(220000001));
        wd2.setCloudPercentage(49.5);
        wd2.setDate(Date.valueOf("2015-01-25"));
        wd2.setHour(Time.valueOf("00:00:00"));
        wd2.setPreasure(997.6);
        wd2.setTemperature(29.2);
        wd2.setWindSpeed(3.0);
        wd2.setCity("Barcelona");
        wd2.setDescription("sky is clear");
        wd2.setIcon("01d");
        wd2.setCode(800);
        wdList.add(wd2);
        wdf.saveWdList(wdList);
        
        try {
            wdf.deleteWdByCityAndDate("Milan", Date.valueOf("2015-01-25"));
            assertEquals(0, wdf.getWeatherDataListFromDB("Milan", Date.valueOf("2015-01-25")).size());
            
        } catch (Exception e) {
            Logger.getLogger(WeatherDataFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void testDeleteWdByDate() {
        List<WeatherData> wdList = new ArrayList<>();
        WeatherData wd1 = new WeatherData();
        wd1.setId(intToLong(220000000));
        wd1.setCloudPercentage(34.5);
        wd1.setDate(Date.valueOf("2015-01-25"));
        wd1.setHour(Time.valueOf("00:00:00"));
        wd1.setPreasure(998.5);
        wd1.setTemperature(28.7);
        wd1.setWindSpeed(4.0);
        wd1.setCity("Milan");
        wd1.setDescription("sky is clear");
        wd1.setIcon("01d");
        wd1.setCode(800);
        wdList.add(wd1);
        WeatherData wd2 = new WeatherData();
        wd2.setId(intToLong(220000001));
        wd2.setCloudPercentage(49.5);
        wd2.setDate(Date.valueOf("2015-01-25"));
        wd2.setHour(Time.valueOf("00:00:00"));
        wd2.setPreasure(997.6);
        wd2.setTemperature(29.2);
        wd2.setWindSpeed(3.0);
        wd2.setCity("Barcelona");
        wd2.setDescription("sky is clear");
        wd2.setIcon("01d");
        wd2.setCode(800);
        wdList.add(wd2);
        wdf.saveWdList(wdList);
        
        try {
            wdf.deleteWdByDate(Date.valueOf("2015-01-25"));
            int wdMilan = wdf.getWeatherDataListFromDB("Milan", Date.valueOf("2015-01-25")).size();
            int wdBarcelona = wdf.getWeatherDataListFromDB("Barcelona", Date.valueOf("2015-01-25")).size();
            assertEquals(0, wdMilan+wdBarcelona);
            
        } catch (Exception e) {
            Logger.getLogger(WeatherDataFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}