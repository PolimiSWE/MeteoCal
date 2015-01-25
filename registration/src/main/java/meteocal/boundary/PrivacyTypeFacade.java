/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import meteocal.entity.PrivacyType;

/**
 *
 * @author Milos
 */
@Stateless
public class PrivacyTypeFacade extends AbstractFacade<PrivacyType> {

    @PersistenceContext(unitName = "authPU")
    EntityManager em;

    public PrivacyType createNew() {
        PrivacyType tmp = new PrivacyType();
        em.persist(tmp);
        em.flush();
        return tmp;
    }

    public void save(PrivacyType pt) {
        PrivacyType tmp;
        if (pt.getId() != null) {
            tmp = em.find(PrivacyType.class, (long) pt.getId());
            if (tmp != null) {
                em.merge(pt);
                em.flush();
            } 
        } else {
            em.persist(pt);
            em.flush();
        }
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
        PrivacyType pt;
        pt = em.find(PrivacyType.class, (long) ptId);
        if (pt != null) {
            em.remove(pt);
        }
        em.flush();
    }

    public PrivacyType getPrivacyType(int ptId) {
        PrivacyType pt;
        pt = em.find(PrivacyType.class, (long) ptId);
        if (pt != null) {
            return pt;
        } else {
            return new PrivacyType();
        }
    }

}
