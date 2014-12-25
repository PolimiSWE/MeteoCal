/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Milos
 */
public class CalendarTest {
    
    public CalendarTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Calendar.
     */
    @Ignore
    @Test
    public void testGetId() {
        try {
            System.out.println("getId");
            Calendar instance = new Calendar();
            Long expResult = null;
            Long result = instance.getId();
            assertEquals(expResult, result);
            System.out.println("Test(testGetId) executed successfully");
        }
        catch(AssertionError ae)
        {
            System.out.println("Test(testGetId) resulted in assertion error: " + ae.getMessage().toUpperCase());
            fail("Test failed!");
        }
    }

    /**
     * Test of setId method, of class Calendar.
     */
    @Ignore
    @Test
    public void testSetId() {
        try {
            System.out.println("setId");
            Long id = null;
            Calendar instance = new Calendar();
            instance.setId(id);
            System.out.println("Test(testSetId) resulted in null pointer error");
            fail("Test failed!");
        }
        catch(NullPointerException npe)
        {
            System.out.println("Test(testSetId) executed successfully");
        }
    }

    /**
     * Test of getCalendarPrivacy method, of class Calendar.
     */
    @Ignore
    @Test
    public void testGetCalendarPrivacy() {
        try {
            System.out.println("getCalendarPrivacy");
            Calendar instance = new Calendar();
            PrivacyType expResult = null;
            PrivacyType result = instance.getCalendarPrivacy();
            assertEquals(expResult, result);
            System.out.println("Test(testGetCalendarPrivacy) executed successfully");
        }
        catch(AssertionError ae)
        {
            System.out.println("Test(testGetCalendarPrivacy) resulted in assertion error: " + ae.getMessage().toUpperCase());
            fail("Test failed!");
        }
    }

    /**
     * Test of setCalendarPrivacy method, of class Calendar.
     */
    @Ignore
    @Test
    public void testSetCalendarPrivacy() {
        try {
            System.out.println("setCalendarPrivacy");
            PrivacyType calendarPrivacy = null;
            Calendar instance = new Calendar();
            instance.setCalendarPrivacy(calendarPrivacy);
            System.out.println("Test(testSetCalendarPrivacy) resulted in null pointer error");
            fail("Test failed!");
        }
        catch(NullPointerException npe)
        {
             System.out.println("Test(testSetCalendarPrivacy) executed successfully");
        }
    }

    /**
     * Test of getOwner method, of class Calendar.
     */
    @Ignore
    @Test
    public void testGetOwner() {
        try {
            System.out.println("getOwner");
            Calendar instance = new Calendar();
            User expResult = null;
            User result = instance.getOwner();
            assertEquals(expResult, result);
            System.out.println("Test(testGetOwner) executed successfully");
        }
        catch(AssertionError ae)
        {
            System.out.println("Test(testGetOwner) resulted in assertion error: " + ae.getMessage().toUpperCase());
            fail("Test failed!");
        }
    }

    /**
     * Test of setOwner method, of class Calendar.
     */
    @Ignore
    @Test
    public void testSetOwner() {
        try {
            System.out.println("setOwner");
            User owner = null;
            Calendar instance = new Calendar();
            instance.setOwner(owner);
            System.out.println("Test(testSetOwner) resulted in assertion error: ");
            fail("Test failed!");
        }
        catch(NullPointerException ae)
        {
            System.out.println("Test(testSetOwner) executed successfully");
        }
    }

    /**
     * Test of getCalendarList method, of class Calendar.
     */
    @Ignore
    @Test
    public void testGetCalendarList() {
        try {
            System.out.println("getCalendarList");
            Calendar instance = new Calendar();
            CalendarList expResult = null;
            CalendarList result = instance.getCalendarList();
            assertEquals(expResult, result);
            System.out.println("Test(testGetCalendarList) executed successfully");
        }
        catch(AssertionError ae)
        {
            System.out.println("Test(testGetCalendarList) resulted in assertion error: " + ae.getMessage().toUpperCase());
            fail("Test failed!");
        }
    }

    /**
     * Test of setCalendarList method, of class Calendar.
     */
    @Ignore
    @Test
    public void testSetCalendarList() {
        try {
            System.out.println("setCalendarList");
            CalendarList calendarList = null;
            Calendar instance = new Calendar();
            instance.setCalendarList(calendarList);
            System.out.println("Test(testSetCalendarList) resulted in null pointer error");
            fail("Test failed!");
        }
        catch(NullPointerException npe)
        {
            System.out.println("Test(testSetCalendarList) executed successfully"); 
        }
    }

    /**
     * Test of getEventLists method, of class Calendar.
     */
    @Ignore
    @Test
    public void testGetEventLists() {
        try {
            System.out.println("getEventLists");
            Calendar instance = new Calendar();
            Collection<EventList> expResult = null;
            Collection<EventList> result = instance.getEventLists();
            assertEquals(expResult, result);
            System.out.println("Test(testGetEventLists) executed successfully");
        }
        catch(AssertionError ae)
        {
            System.out.println("Test(testGetEventLists) resulted in assertion error: " + ae.getMessage().toUpperCase());
            fail("Test failed!");
        }
    }

    /**
     * Test of setEventLists method, of class Calendar.
     */
    @Ignore
    @Test
    public void testSetEventLists() {
        try {
            System.out.println("setEventLists");
            Collection<EventList> eventLists = null;
            Calendar instance = new Calendar();
            instance.setEventLists(eventLists);
            System.out.println("Test(testSetEventLists) resulted in null pointer error");
            fail("Test failed!");
        }
        catch(NullPointerException npe)
        {
            System.out.println("Test(testSetEventLists) executed successfully");
        }
    }

    /**
     * Test of getEvents method, of class Calendar.
     */
    @Ignore
    @Test
    public void testGetEvents() {
        try {
            System.out.println("getEvents");
            Calendar instance = new Calendar();
            Collection<Event> expResult = null;
            Collection<Event> result = instance.getEvents();
            assertEquals(expResult, result);
            System.out.println("Test(testGetEvents) executed successfully");
        }
        catch(AssertionError ae)
        {
            System.out.println("Test(testGetEvents) resulted in assertion error: " + ae.getMessage().toUpperCase());
            fail("Test failed!");
        }
    }

    /**
     * Test of setEvents method, of class Calendar.
     */
    @Ignore
    @Test
    public void testSetEvents() {
        try {
            System.out.println("setEvents");
            Collection<Event> events = null;
            Calendar instance = new Calendar();
            instance.setEvents(events);
            System.out.println("Test(testSetEvents) resulted in null pointer error");
            fail("Test failed!");
        }
        catch(NullPointerException npe)
        {
            System.out.println("Test(testSetEvents) executed successfully");
        }
    }

    /**
     * Test of hashCode method, of class Calendar.
     */
    @Ignore
    @Test
    public void testHashCode() {
        try {
            System.out.println("hashCode");
            Calendar instance = new Calendar();
            int expResult = 0;
            int result = instance.hashCode();
            assertEquals(expResult, result);
            System.out.println("Test(testHashCode) executed successfully");
        }
        catch(AssertionError ae)
        {
            System.out.println("Test(testHashCode) resulted in assertion error: " + ae.getMessage().toUpperCase());
            fail("Test failed!");
        }
    }

    /**
     * Test of equals method, of class Calendar.
     */
    @Ignore
    @Test
    public void testEquals() {
        try {
            System.out.println("equals");
            Object object = null;
            Calendar instance = new Calendar();
            boolean expResult = false;
            boolean result = instance.equals(object);
            assertEquals(expResult, result);
            System.out.println("Test(testEquals) executed successfully");
        }
        catch(AssertionError ae)
        {
            System.out.println("Test(testEquals) resulted in assertion error: " + ae.getMessage().toUpperCase());
            fail("Test failed!");
        }
    }

    /**
     * Test of toString method, of class Calendar.
     */
    @Ignore
    @Test
    public void testToString() {
        try {
            System.out.println("toString");
            Calendar instance = new Calendar();
            String expResult = "meteocal.entity.Calendar[ id=0 ]";
            String result = instance.toString();
            assertEquals(expResult, result);
            System.out.println("Test(testEquals) executed successfully");
        }
        catch(AssertionError ae)
        {
            System.out.println("Test(testEquals) resulted in assertion error: " + ae.getMessage().toUpperCase());
            fail("Test failed!");
        }
        catch(NullPointerException npe)
        {
            System.out.println("Test(testEquals) executed successfully, the ID was never set so its value is NULL");
        }
    }
    
}
