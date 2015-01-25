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
import meteocal.entity.PrivacyType;
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
public class PrivacyTypeFacadeIT {
    
    @EJB
    PrivacyTypeFacade ptf;
    
    @PersistenceContext
    EntityManager em;

    @Deployment
    public static WebArchive createArchiveAndDeploy() {

        return ShrinkWrap.create(WebArchive.class)
                .addClass(PrivacyTypeFacade.class)
                .addPackage(PrivacyType.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/beans.xml"));
    }

    @Test
    public void PrivacyTypeFacadeShouldBeInjected() {
        assertNotNull(ptf);
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
        PrivacyType pt = new PrivacyType();
        ptf.save(pt);
        try {
            assertNotNull(ptf.find(pt.getId()));
        } catch (Exception e) {
            Logger.getLogger(PrivacyTypeFacadeIT.class.getName()).log(Level.SEVERE, null, e);
            fail();
        }
    }

    @Test
    public void testUpdate() {
        PrivacyType pt = new PrivacyType();
        pt.setPrivacy(false);
        ptf.save(pt);
        PrivacyType pt2 = ptf.find(pt.getId());
        pt2.setPrivacy(true);
        ptf.save(pt2);
        try {
            assertTrue(ptf.find(pt.getId()).getPrivacy());
        } catch (Exception e) {
            Logger.getLogger(PrivacyTypeFacadeIT.class.getName()).log(Level.SEVERE, null, e);
            fail();
        }
    }
    
    @Test
    public void testDelete(){
        PrivacyType pt = new PrivacyType();
        ptf.save(pt);
        ptf.delete(pt.getId().intValue());
        try {
            assertNull(ptf.find(pt.getId()));
        } catch (Exception e) {
            Logger.getLogger(PrivacyTypeFacadeIT.class.getName()).log(Level.SEVERE, null, e);
            fail();
        }
    }

    

    
}
