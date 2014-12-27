/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import static com.ctc.wstx.util.DataUtil.Integer;
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
        TypedQuery<PrivacyType> query = em.createQuery("SELECT pt FROM PrivacyType AS pt WHERE pt.id=:idParam", PrivacyType.class);
        query.setParameter("idParam", pt.getId());
        if(query.getResultList().iterator().hasNext()) 
            em.merge(pt);
        else
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

    public void delete(int ptId) {
        TypedQuery<PrivacyType> query = em.createQuery("SELECT pt FROM PrivacyType AS pt WHERE pt.id=:idParam", PrivacyType.class);
        query.setParameter("idParam", ptId);
        PrivacyType pt;
        if(query.getResultList().iterator().hasNext()) 
        {
            pt = query.getResultList().iterator().next();
            em.remove(pt);
        }
    }

    public PrivacyType getPrivacyType(int ptId) {
        TypedQuery<PrivacyType> query = em.createQuery("SELECT pt FROM PrivacyType AS pt WHERE pt.id=:idParam", PrivacyType.class);
        query.setParameter("idParam", ptId);
        if(query.getResultList().iterator().hasNext()) 
            return query.getResultList().iterator().next();
        else 
            return new PrivacyType();
    }

}
