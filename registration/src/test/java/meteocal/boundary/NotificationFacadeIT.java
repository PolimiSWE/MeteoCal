/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.io.File;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import meteocal.entity.Group;
import meteocal.entity.Notification;
import meteocal.entity.PrivacyType;
import meteocal.entity.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 *
 * @author Milos
 */
@RunWith(Arquillian.class)
public class NotificationFacadeIT {
    
    public NotificationFacadeIT() {
    }
    
    @EJB 
    NotificationFacade notfFacade;
    
    @EJB
    PrivacyTypeFacade ptFacade;
    
    @EJB
    UserFacade userFacade;
    
    @PersistenceContext
    EntityManager em;
    
    static User testUser;
    static User testUser2;
    
    
    
    
   
    @Deployment
    public static WebArchive createArchiveAndDeploy() {

        return ShrinkWrap.create(WebArchive.class)
                .addClass(NotificationFacade.class)
                .addClass(PrivacyTypeFacade.class)
                .addClass(UserFacade.class)
                .addPackage(Notification.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/beans.xml"));
    }
    
    @BeforeClass
    public static void setUp() {
        NotificationFacadeIT.testUser = new User();
        NotificationFacadeIT.testUser.setEmail("jeej@jeej.com");
        NotificationFacadeIT.testUser.setGroupName(Group.USERS);
        NotificationFacadeIT.testUser.setName("tester");
        NotificationFacadeIT.testUser.setSurname("testing");
        NotificationFacadeIT.testUser.setUsername("jeej");
        NotificationFacadeIT.testUser.setPassword("password");
        NotificationFacadeIT.testUser2 = new User();
        NotificationFacadeIT.testUser2.setEmail("jeej2@jeej.com");
        NotificationFacadeIT.testUser2.setGroupName(Group.USERS);
        NotificationFacadeIT.testUser2.setName("tester");
        NotificationFacadeIT.testUser2.setSurname("testing");
        NotificationFacadeIT.testUser2.setUsername("jeej2");
        NotificationFacadeIT.testUser2.setPassword("password");
    }
    
    @Before
    public void setUpTest(){
        if(NotificationFacadeIT.testUser.getId()==null)
            this.userFacade.save(NotificationFacadeIT.testUser);
        if(NotificationFacadeIT.testUser2.getId()==null)
            this.userFacade.save(NotificationFacadeIT.testUser2);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void notificationFacadeShouldBeInjected(){
        assertNotNull(notfFacade);
    }
    
    @Test
    public void privacyTypeFacadeShouldBeInjected(){
        assertNotNull(ptFacade);
    }
    
    @Test
    public void userFacadeShouldBeInjected(){
        assertNotNull(userFacade);
    }

    @Test
    public void testCreate() throws Exception {        
        Notification notf = new Notification();
        notf.setDescription("Test Notification!");
        notf.setOwner(NotificationFacadeIT.testUser);
        notfFacade.create(notf);
        Notification notf2 = notfFacade.find(notf.getId());
        assertEquals(notf, notf2);
    }

    @Test
    public void testRemove() throws Exception {
        Notification notf = new Notification();
        notf.setDescription("Test Notification!");
        notf.setOwner(NotificationFacadeIT.testUser);
        notfFacade.create(notf);
        Notification notf2 = notfFacade.find(notf.getId());
        assertNotNull(notf2);
        notfFacade.remove(notf);
        notf2 = notfFacade.find(notf.getId());
        assertNull(notf2);
    }

    @Test
    public void testFind() throws Exception {
        Notification notf = new Notification();
        notf.setDescription("Test Notification!");
        notf.setOwner(NotificationFacadeIT.testUser);
        notfFacade.create(notf);
        Notification notf2 = notfFacade.find(notf.getId());
        assertNotNull(notf2);
    }

    @Test
    public void testFindAll_0args() throws Exception {
        List<Notification> notificationList1 = this.notfFacade.findAll();
        Notification notf = new Notification();
        notf.setDescription("Test Notification!");
        notf.setOwner(NotificationFacadeIT.testUser2);
        notfFacade.create(notf);
        List<Notification> notificationList2 = this.notfFacade.findAll();
        assertEquals(notificationList1.size() + 1, notificationList2.size());
    }

    @Test
    public void testFindAll_User() throws Exception {
        Notification notf ;
        for(int i=0;i<10;i++){        
            notf = new Notification();
            notf.setDescription("Test Notification!");
            notf.setOwner(NotificationFacadeIT.testUser2);
            notfFacade.create(notf);
        }
        List<Notification> notificationList = this.notfFacade.findAll(NotificationFacadeIT.testUser2);
        assertEquals(10, notificationList.size());
    }

    @Test
    public void testRemoveByID() throws Exception {
        Notification notf = new Notification();
        notf.setDescription("Test Notification!");
        notf.setOwner(NotificationFacadeIT.testUser);
        notfFacade.create(notf);
        Notification notf2 = notfFacade.find(notf.getId());
        assertNotNull(notf2);
        long id = notf2.getId();
        notfFacade.removeByID(id);
        notf2 = notfFacade.find(notf.getId());
        assertNull(notf2);
    }
    
}
