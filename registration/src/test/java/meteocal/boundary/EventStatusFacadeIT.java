/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import meteocal.entity.EventStatus;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
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

    @PersistenceContext//(unitName = "authPU")
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
            assertTrue(esf.getDB_Table().contains(es));
        } catch (Exception e) {
            Logger.getLogger(EventStatusFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
