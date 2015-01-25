/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.io.File;
import java.net.URL;
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
    EventStatusFacade ef;
    
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
        assertNotNull(ef);
    }
    
    @Test
    public void EntityManagerShouldBeInjected() {
        assertNotNull(em);
    }
    
}
