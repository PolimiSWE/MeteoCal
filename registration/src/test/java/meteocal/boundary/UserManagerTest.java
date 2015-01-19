/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import meteocal.boundary.UserManager;
import meteocal.entity.Group;
import meteocal.entity.User;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author miglie
 */
public class UserManagerTest {
    
    private UserManager cut;
    
    @Before
    public void setUp() {
        cut = new UserManager();
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