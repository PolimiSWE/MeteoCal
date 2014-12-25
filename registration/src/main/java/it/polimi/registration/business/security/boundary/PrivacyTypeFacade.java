/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import java.security.Principal;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import meteocal.entity.PrivacyType;

/**
 *
 * @author Milos
 */
@Stateless
public class PrivacyTypeFacade extends AbstractFacade<PrivacyType> {

    @PersistenceContext(unitName = "authPU")
    private EntityManagerFactory emf;
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    @Inject
    Principal principal;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public PrivacyTypeFacade() {
        super(PrivacyType.class);
    }

    public UserTransaction getUserTransaction() {
        return utx;
    }

    public void setUserTransaction(UserTransaction utx) {
        this.utx = utx;
    }

}
