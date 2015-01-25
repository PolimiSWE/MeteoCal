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
import meteocal.control.PasswordEncrypter;
import meteocal.entity.Calendar;
import meteocal.entity.Group;
import meteocal.entity.PrivacyType;
import meteocal.entity.User;
import static org.hamcrest.CoreMatchers.is;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 *
 * @author Nemanja
 */
@RunWith(Arquillian.class)
public class UserFacadeIT {

    @EJB
    UserFacade usf;

    @EJB
    PrivacyTypeFacade ptf;

    @PersistenceContext
    EntityManager em;

    User user;
    PrivacyType ptfalse;
    PrivacyType pttrue;

    @Before
    public void setUp() {
        user = new User();
        user.setEmail("jeej@jeej.com");
        user.setGroupName(Group.USERS);
        user.setName("tester");
        user.setSurname("testing");
        user.setUsername("jeej");
        user.setPassword("password");
        ptfalse = new PrivacyType();
        ptfalse.setPrivacy(false);
        pttrue = new PrivacyType();
        pttrue.setPrivacy(true);
        ptf.save(ptfalse);
        ptf.save(pttrue);
    }

    @Deployment
    public static WebArchive createArchiveAndDeploy() {

        return ShrinkWrap.create(WebArchive.class)
                .addClass(UserFacade.class)
                .addClass(PrivacyTypeFacade.class)
                .addPackage(User.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/beans.xml"));
    }

    @Test
    public void UserFacadeShouldBeInjected() {
        assertNotNull(usf);
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
    public void testSave() {
        User usr = new User();
        usr.setEmail("jesdfsdf@jeej.com");
        usr.setGroupName(Group.USERS);
        usr.setName("tester");
        usr.setSurname("testing");
        usr.setUsername("jesdfsdf");
        usr.setPassword("password");
        Calendar cal = usr.getMyCalendar();
        cal.setCalendarPrivacy(this.ptfalse);
        usr.setMyCalendar(cal);
        usf.save(usr);
        User usr2 = usf.find(usr.getId());
        assertNotNull(usr2);
    }


    @Test
    public void testEdit() {
        User usr = new User();
        usr.setEmail("adfadfsa@jeej.com");
        usr.setGroupName(Group.USERS);
        usr.setName("tester");
        usr.setSurname("testing");
        usr.setUsername("adfadfsa");
        usr.setPassword("password");
        Calendar cal = usr.getMyCalendar();
        cal.setCalendarPrivacy(this.ptfalse);
        usr.setMyCalendar(cal);
        usf.save(usr);
        User usr2 = usf.find(usr.getId());
        usr2.setName("opala");
        usf.save(usr2);
        User usr3 = usf.find(usr2.getId());
        assertEquals(usr2.getName(), usr3.getName());
    }


     @Test
     public void testGetDB_Table() {
        User usr = new User();
        usr.setEmail("vgturi@jeej.com");
        usr.setGroupName(Group.USERS);
        usr.setName("tester");
        usr.setSurname("vgturi");
        usr.setUsername("ghjk");
        usr.setPassword("password");
        Calendar cal = usr.getMyCalendar();
        cal.setCalendarPrivacy(this.pttrue);
        usr.setMyCalendar(cal);
        usf.save(usr);
         assertTrue(!usf.getDB_Table().isEmpty());
     }

     @Test
     public void testCheckUsername() {
        User usr = new User();
        usr.setEmail("masala@jeej.com");
        usr.setGroupName(Group.USERS);
        usr.setName("tester");
        usr.setSurname("vgturi");
        usr.setUsername("masala");
        usr.setPassword("password");
        Calendar cal = usr.getMyCalendar();
        cal.setCalendarPrivacy(this.ptfalse);
        usr.setMyCalendar(cal);
        usf.save(usr);
        assertFalse(usf.checkUsername("masala"));
     }
         @Test
     public void testCheckEmail() {
        User usr = new User();
        usr.setEmail("tyuio@jeej.com");
        usr.setGroupName(Group.USERS);
        usr.setName("tester");
        usr.setSurname("vgturi");
        usr.setUsername("tyuio");
        usr.setPassword("password");
        Calendar cal = usr.getMyCalendar();
        cal.setCalendarPrivacy(this.ptfalse);
        usr.setMyCalendar(cal);
        usf.save(usr);
        assertFalse(usf.checkEmail("tyuio@jeej.com"));
     }

     @Test
     public void passwordsShouldBeEncryptedOnDB() {
        User usr = new User();
        usr.setEmail("tttttttt@jeej.com");
        usr.setGroupName(Group.USERS);
        usr.setName("tester");
        usr.setSurname("vgturi");
        usr.setUsername("tttttttt");
        usr.setPassword("password");
        Calendar cal = usr.getMyCalendar();
        cal.setCalendarPrivacy(this.ptfalse);
        usr.setMyCalendar(cal);
        usf.save(usr);
        User foundUser = usf.find(usr.getId());
        assertNotNull(foundUser);
        assertThat(foundUser.getPassword(),is(PasswordEncrypter.encryptPassword("password")));
     }
}
