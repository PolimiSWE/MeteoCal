/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import meteocal.entity.EventStatus;
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
public class EventStatusFacadeIT {

    @EJB
    EventStatusFacade esf;

    @PersistenceContext
    EntityManager em;

    @Deployment
    public static WebArchive createArchiveAndDeploy() {

        return ShrinkWrap.create(WebArchive.class)
                .addClass(EventStatusFacade.class)
                .addPackage(EventStatus.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/beans.xml"));
    }

    @Test
    public void EventStatusFacadeShouldBeInjected() {
        assertNotNull(esf);
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
        EventStatus es = new EventStatus();
        es.setId(intToLong(150000000));
        esf.save(es);
        try {
            assertNotNull(esf.find(intToLong(150000000)));
        } catch (Exception e) {
            Logger.getLogger(EventStatusFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void testUpdate() {
        EventStatus es = new EventStatus();
        es.setId(intToLong(150000000));
        es.setStatus(0);
        esf.save(es);
        EventStatus es2 = esf.find(intToLong(150000000));
        es2.setStatus(1);
        esf.save(es2);
        try {
            assertTrue(esf.find(intToLong(150000000)).getStatus().equals(1));
        } catch (Exception e) {
            Logger.getLogger(EventStatusFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void testDelete() {
        EventStatus es = new EventStatus();
        es.setId(intToLong(150000000));
        es.setStatus(0);
        esf.save(es);
        esf.delete(150000000);
        try {
            assertNull(esf.find(intToLong(150000000)));
        } catch (Exception e) {
            Logger.getLogger(EventStatusFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Test
    public void testGetDBTable(){
        EventStatus es1 = new EventStatus();
        es1.setId(intToLong(150000000));
        es1.setStatus(0);
        esf.save(es1);
        EventStatus es2 = new EventStatus();
        es2.setId(intToLong(150000001));
        es2.setStatus(0);
        esf.save(es2);
        try {
            assertEquals(2, esf.getDB_Table().size());
        } catch (Exception e) {
            Logger.getLogger(EventStatusFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
