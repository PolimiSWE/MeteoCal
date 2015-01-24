/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import meteocal.entity.User;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author miglie
 */
public class UserManagerTest {
    
    private UserFacade cut;
    
    @Before
    public void setUp() {
        cut = new UserFacade();
        cut.em = mock(EntityManager.class);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void newUsersShouldBelongToUserGroupAndSavedOnce() {
        User newUser = new User();
        cut.save(newUser);
        verify(cut.em,times(1)).persist(newUser);
    }
}
