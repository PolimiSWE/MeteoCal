/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import meteocal.entity.EventType;
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
public class EventTypeFacadeIT {
    
    @EJB
    EventTypeFacade etf;
    
    @PersistenceContext
    EntityManager em;

    @Deployment
    public static WebArchive createArchiveAndDeploy() {

        return ShrinkWrap.create(WebArchive.class)
                .addClass(EventTypeFacade.class)
                .addPackage(EventType.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/beans.xml"));
    }

    @Test
    public void EventTypeFacadeShouldBeInjected() {
        assertNotNull(etf);
    }

    @Test
    public void EntityManagerShouldBeInjected() {
        assertNotNull(em);
    }
    private Long intToLong(int number) {
        return Integer.toUnsignedLong(number);
    }
    @Test
    public void testCreate() {
        EventType et = new EventType();
        etf.save(et);
        try {
            assertNotNull(etf.find(et.getId()));
        } catch (Exception e) {
            Logger.getLogger(EventTypeFacadeIT.class.getName()).log(Level.SEVERE, null, e);
            fail();
        }
    }

    @Test
    public void testUpdate(){
        EventType et = new EventType();
        et.setEventList(new ArrayList<>());
        et.setType(false);
        etf.save(et);
        EventType et2 = etf.find(et.getId());
        et2.setType(true);
        etf.save(et2);
        try {
            assertTrue(etf.find(et2.getId()).getType());
        } catch (Exception e) {
            Logger.getLogger(EventTypeFacadeIT.class.getName()).log(Level.SEVERE, null, e);
            fail();
        }
    }

    @Test
    public void testDelete() {
        EventType et = new EventType();
        et.setEventList(new ArrayList<>());
        et.setType(false);
        etf.save(et);
        int id = et.getId().intValue();
        etf.delete(id);
        try {
            EventType et2 = etf.find(id);
            assertNull(et2);
        } catch (Exception e) {
            Logger.getLogger(EventTypeFacadeIT.class.getName()).log(Level.SEVERE, null, e);
            fail();
        }
    }

    @Test
    public void testGetDB_Table() {
        EventType et1 = new EventType();
        et1.setEventList(new ArrayList<>());
        et1.setType(false);
        etf.save(et1);
        EventType et2 = new EventType();
        et2.setEventList(new ArrayList<>());
        et2.setType(true);
        etf.save(et2);
        try {
            assertEquals(2, etf.getDB_Table().size());
        } catch (Exception e) {
            Logger.getLogger(EventTypeFacadeIT.class.getName()).log(Level.SEVERE, null, e);
            fail();
        }
    }
    
}
