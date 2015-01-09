/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import meteocal.boundary.PrivacyTypeFacade;
import meteocal.boundary.UserManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.spi.PersistenceProvider;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Milos
 */
public class PrivacyTypeTest {
    
    private PrivacyTypeFacade ptf;
    private EntityManager em;
    
    public PrivacyTypeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ptf = new PrivacyTypeFacade();
        em = mock(EntityManager.class);
        //ptf.setUserTransaction(mock(UserTransaction.class));
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAllOps(){
        
         //------------  Create a Privacy Type = Public ---------
        PrivacyType pt1 = new PrivacyType();
        pt1.setPrivacy(Boolean.TRUE);
         //------------  Create a Privacy Type = Private ---------
        PrivacyType pt2 = new PrivacyType();
        pt1.setPrivacy(Boolean.FALSE);        
        //------------ Create a List of Events that will be public -------
        Collection<Event> publicEvents = new ArrayList<>();
        Event e = new Event();
        e.setEventPrivacy(pt1);
        publicEvents.add(e);
        em.persist(e); // Persist the Entity
        e = new Event();
        e.setEventPrivacy(pt1);
        publicEvents.add(e);
        em.persist(e); // Persist the Entity
        e = new Event();
        e.setEventPrivacy(pt1);
        publicEvents.add(e);
        em.persist(e); // Persist the Entity
        e = new Event();
        e.setEventPrivacy(pt1);
        publicEvents.add(e);
        em.persist(e); // Persist the Entity
        //------------ Create a List of Events that will be private -------
        Collection<Event> privateEvents = new ArrayList<>();
        e = new Event();
        e.setEventPrivacy(pt2);
        privateEvents.add(e);
        em.persist(e); // Persist the Entity
        e = new Event();
        e.setEventPrivacy(pt2);
        privateEvents.add(e);
        em.persist(e); // Persist the Entity
        e = new Event();
        e.setEventPrivacy(pt2);
        privateEvents.add(e);
        em.persist(e); // Persist the Entity
        e = new Event();
        e.setEventPrivacy(pt2);
        privateEvents.add(e);
        em.persist(e); // Persist the Entity
        pt1.setEventList(publicEvents);
        pt1.setEventList(privateEvents);
        // At this Point the Entity does not have a
        // Persistent Identity and is not associated
        // with a persistent Context
        em.persist(pt1); // Persist the Entity
        em.persist(pt2); // Persist the Entity
        em.flush();
        // At this point the Entity has a Persistent
        // Identity and is associated witha Persistent
        // context.
        //----------- Print IDs of test objects ---------
        System.out.println("Privacy type 1 Id : " + pt1.getId());
        System.out.println("Privacy type 2 Id : " + pt2.getId());
        Iterator<Event> it = publicEvents.iterator();
        int counter = 1;
        System.out.println("Public Events:");
        while(it.hasNext())
        {
            Event e2 = it.next();
            System.out.println("Event e "+ counter +" Id : " + e2.getId());
            counter++;
        }
        it = privateEvents.iterator();
        System.out.println("Private Events:");
        while(it.hasNext())
        {
            Event e2 = it.next();
            System.out.println("Event e "+ counter +" Id : " + e2.getId());
            counter++;
        }
       
    }

    //Default tests for getter and setter methods, set to be ignored by @Ignore annotation
    /**
     * Test of getEventList method, of class PrivacyType.
     */
    @Ignore
    @Test
    public void testGetEventList() {
        System.out.println("getEventList");
        PrivacyType instance = new PrivacyType();
        Collection<Event> expResult = null;
        Collection<Event> result = instance.getEventList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEventList method, of class PrivacyType.
     */
    @Ignore
    @Test
    public void testSetEventList() {
        System.out.println("setEventList");
        Collection<Event> eventList = null;
        PrivacyType instance = new PrivacyType();
        instance.setEventList(eventList);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrivacy method, of class PrivacyType.
     */
    @Ignore
    @Test
    public void testGetPrivacy() {
        System.out.println("getPrivacy");
        PrivacyType instance = new PrivacyType();
        Boolean expResult = null;
        Boolean result = instance.getPrivacy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrivacy method, of class PrivacyType.
     */
    @Ignore
    @Test
    public void testSetPrivacy() {
        System.out.println("setPrivacy");
        Boolean privacy = null;
        PrivacyType instance = new PrivacyType();
        instance.setPrivacy(privacy);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class PrivacyType.
     */
    @Ignore
    @Test
    public void testGetId() {
        System.out.println("getId");
        PrivacyType instance = new PrivacyType();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class PrivacyType.
     */
    @Ignore
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        PrivacyType instance = new PrivacyType();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class PrivacyType.
     */
    @Ignore
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        PrivacyType instance = new PrivacyType();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class PrivacyType.
     */
    @Ignore
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object object = null;
        PrivacyType instance = new PrivacyType();
        boolean expResult = false;
        boolean result = instance.equals(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class PrivacyType.
     */
    @Ignore
    @Test
    public void testToString() {
        System.out.println("toString");
        PrivacyType instance = new PrivacyType();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
