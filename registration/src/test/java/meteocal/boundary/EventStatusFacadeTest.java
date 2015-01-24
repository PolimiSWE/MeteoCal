/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import meteocal.entity.EventStatus;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Nemanja
 */
public class EventStatusFacadeTest {
    
    private EventStatusFacade esf;
    
    public EventStatusFacadeTest() {
    }
    
    @Before
    public void setUp() {
        esf = new EventStatusFacade();
        esf.setEntityManager(mock(EntityManager.class));
    }
    
    @After
    public void tearDown() {
    }
    /*private EntityManager em;

    @Before
    public void setUp(){
        if (em == null) {
            em = (EntityManager) Persistence.createEntityManagerFactory("testingPU").createEntityManager();
            esf.setEntityManager(em);
        }
    }

    @After
    public void tearDown() {
    }*/

    /**
     * Test of create method, of class EventStatusFacade.
     */
    @Test
    public void testCreate() throws Exception {
        EventStatus es1 = new EventStatus();
        esf.save(es1);
        //assertThat(esf.getEventType(esId), is(Group.USERS));
        verify(esf.getEntityManager(),times(1)).persist(es1);
    }

    /**
     * Test of edit method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        EventStatus entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        instance.edit(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testRemove() throws Exception {
        System.out.println("remove");
        EventStatus entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        instance.remove(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testFind() throws Exception {
        System.out.println("find");
        Object id = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        EventStatus expResult = null;
        EventStatus result = instance.find(id);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        List<EventStatus> expResult = null;
        List<EventStatus> result = instance.findAll();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRange method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testFindRange() throws Exception {
        System.out.println("findRange");
        int[] range = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        List<EventStatus> expResult = null;
        List<EventStatus> result = instance.findRange(range);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testCount() throws Exception {
        System.out.println("count");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        int expResult = 0;
        int result = instance.count();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNew method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testCreateNew() throws Exception {
        System.out.println("createNew");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        EventStatus expResult = null;
        EventStatus result = instance.createNew();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        EventStatus es = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        instance.save(es);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        int esId = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        instance.delete(esId);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEventType method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testGetEventType() throws Exception {
        System.out.println("getEventType");
        int esId = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        EventStatus expResult = null;
        EventStatus result = instance.getEventType(esId);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDB_Table method, of class EventStatusFacade.
     */
    @Ignore
    @Test
    public void testGetDB_Table() throws Exception {
        System.out.println("getDB_Table");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EventStatusFacade instance = (EventStatusFacade)container.getContext().lookup("java:global/classes/EventStatusFacade");
        List<EventStatus> expResult = null;
        List<EventStatus> result = instance.getDB_Table();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


}
