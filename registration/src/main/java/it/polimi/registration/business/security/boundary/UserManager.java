/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import meteocal.entity.Group;
import meteocal.entity.User;
import java.security.Principal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author miglie
 */
@Stateless
public class UserManager {

    @PersistenceContext(unitName = "authPU")
    EntityManager em;
    
    @Inject
    Principal principal;

    public void save(User user) {
        user.setGroupName(Group.USERS);
        em.persist(user);
    }

    public void unregister() {
        em.remove(getLoggedUser());
    }

    public User getLoggedUser() {
        return em.find(User.class, principal.getName());
    }
    
}
