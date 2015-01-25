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
import meteocal.entity.Calendar;
import meteocal.entity.Group;
import meteocal.entity.PrivacyType;
import meteocal.entity.User;
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
        usf.save(usr);
        User usr2 = usf.find(usr.getId());
        assertNotNull(usr2);
    }

    @Test
    public void testEdit() {
        User usr2 = usf.find(user.getId());
        usr2.setName("opala");
        usf.save(usr2);
        User usr3 = usf.find(usr2.getId());
        assertEquals(usr2.getName(), usr3.getName());
    }

    @Test
    public void testRemove() {
        User usr = new User();
        usr.setEmail("afasd@jeej.com");
        usr.setGroupName(Group.USERS);
        usr.setName("tester");
        usr.setSurname("testing");
        usr.setUsername("afasd");
        usr.setPassword("password");
        usf.save(usr);
        int id = user.getId().intValue();
        User usr2 = usf.find(id);
        usf.remove(usr2);
        User usr3 = usf.find(id);
        assertNull(usr3);
    }
    /*
     @Test
     public void testFind() throws Exception {
     }

     @Test
     public void testFindAll() throws Exception {
     }

     @Test
     public void testFindRange() throws Exception {
     }

     @Test
     public void testCount() throws Exception {
     }

     @Test
     public void testCreateNew() throws Exception {
     }

     @Test
     public void testSave() throws Exception {
     }

     @Test
     public void testDelete() throws Exception {
     }

     @Test
     public void testGetUser_int() throws Exception {
     }

     @Test
     public void testGetDB_Table() throws Exception {
     }

     @Test
     public void testCheckUsername_User() throws Exception {
     }

     @Test
     public void testCheckUsername_String() throws Exception {
     }

     @Test
     public void testCheckEmail_User() throws Exception {
     }

     @Test
     public void testCheckEmail_String() throws Exception {
     }

     @Test
     public void testTryLogIn() throws Exception {
     }

     @Test
     public void testGetUser_String() throws Exception {
     }

     @Test
     public void testGetUserByEmail() throws Exception {
     }

     @Test
     public void testUnregister() throws Exception {
     }

     @Test
     public void testGetLoggedUser() throws Exception {
     }
     @Test
     public void newUsersShouldBeValid() {
     User newUser = new User();
     newUser.setEmail("invalidemail");
     try {
     cut.save(newUser);
     } catch (Exception e) {
     assertTrue(e.getCause() instanceof ConstraintViolationException);
     }
     assertNull(em.find(User.class, "invalidemail"));
     }
    
     @Test
     public void passwordsShouldBeEncryptedOnDB() {
     User newUser = new User();
     String email = "marco@polimi.com";
     String password = "password";
     newUser.setEmail(email);
     newUser.setName("Marco");
     newUser.setPassword(password);
     cut.save(newUser);
     User foundUser = em.find(User.class, email);
     assertNotNull(foundUser);
     assertThat(foundUser.getPassword(),is(PasswordEncrypter.encryptPassword(password)));
     }
     */
}
