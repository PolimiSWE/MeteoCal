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
import meteocal.entity.User;
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
public class UserFacadeIT {
    
    @EJB
    UserFacade usf;
    
    @EJB
    PrivacyTypeFacade ptf;

    @PersistenceContext
    EntityManager em;

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
    public void testCreate() {
        try {
            PrivacyType pt1 = new PrivacyType();
            PrivacyType pt2 = new PrivacyType();
            pt2.setPrivacy(true);
            ptf.save(pt1);
            ptf.save(pt2);
            
            assertNotNull(usf.createNew());
        } catch (Exception e) {
            Logger.getLogger(UserFacadeIT.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void testSave() {
            PrivacyType pt1 = new PrivacyType();
            PrivacyType pt2 = new PrivacyType();
            pt2.setPrivacy(true);
            ptf.save(pt1);
            ptf.save(pt2);
            User usr = usf.createNew();
            usf.save(usr);
            User usr2 = usf.find(usr.getId());
            assertNotNull(usr2);
    }
    
    @Test
    public void testEdit(){
            PrivacyType pt1 = new PrivacyType();
            PrivacyType pt2 = new PrivacyType();
            pt2.setPrivacy(true);
            ptf.save(pt1);
            ptf.save(pt2);
            User usr = usf.createNew();
            User usr2 = usf.find(usr.getId());
            usr2.setName("opala");
            usf.save(usr2);
            User usr3 = usf.find(usr2.getId());
            assertEquals("opala",usr2.getName());
    }

    @Test
    public void testRemove() {
            PrivacyType pt1 = new PrivacyType();
            PrivacyType pt2 = new PrivacyType();
            pt2.setPrivacy(true);
            ptf.save(pt1);
            ptf.save(pt2);
            User usr = usf.createNew();
            User usr2 = usf.find(usr.getId());
            usf.remove(usr2);
            User usr3 = usf.find(usr.getId());
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
