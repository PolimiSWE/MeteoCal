/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import java.util.Collection;
import javax.persistence.EntityManager;
import meteocal.boundary.EventStatusFacade;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Nemanja
 */
public class EventStatusTest {
    
    private EventStatusFacade esf;
    
    public EventStatusTest() {
    }
    
    @Before
    public void setUp() {
        esf = new EventStatusFacade();
        esf.setEntityManager(mock(EntityManager.class));
    }
    
    @After
    public void tearDown() {
    }

    
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object object = null;
        EventStatus instance = new EventStatus();
        boolean expResult = false;
        boolean result = instance.equals(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        EventStatus o = null;
        EventStatus instance = new EventStatus();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
