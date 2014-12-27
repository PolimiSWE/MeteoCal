/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import java.security.Principal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import meteocal.entity.PrivacyType;

/**
 *
 * @author Milos
 */
@Stateless
public class PrivacyTypeFacade extends AbstractFacade<PrivacyType> {

    @PersistenceContext(unitName = "authPU")
    private EntityManager em;
   
    
    public void save(PrivacyType pt) {
        em.persist(pt);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public PrivacyTypeFacade() {
        super(PrivacyType.class);
    }

    public List<PrivacyType> getDB_Table() {
        TypedQuery<PrivacyType> query = em.createQuery("SELECT pt FROM PrivacyType AS pt", PrivacyType.class);
        return query.getResultList();
    }

}
