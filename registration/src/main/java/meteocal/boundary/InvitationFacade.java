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
import meteocal.entity.Invitation;
import meteocal.entity.User;

/**
 *
 * @author Milos
 */
@Stateless
public class InvitationFacade extends AbstractFacade<Invitation> {
    @PersistenceContext(unitName = "authPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvitationFacade() {
        super(Invitation.class);
    }
    
    public List<Invitation> findAll(User u){
        TypedQuery<Invitation> query = em.createQuery("SELECT inv FROM Invitation AS inv WHERE"
                + " inv.user.id=:userIdParam", Invitation.class);
        if(u!=null)
            query.setParameter("userIdParam", u.getId());
        else
            query.setParameter("userIdParam", 0);
        return query.getResultList();
    }

    public List<Invitation> findAll(long userId) {
        TypedQuery<Invitation> query = em.createQuery("SELECT inv FROM Invitation AS inv WHERE"
                + " inv.user.id=:userIdParam", Invitation.class);
        query.setParameter("userIdParam", userId);
        return query.getResultList();
    }

    public List<Invitation> findDirtyInvitations(Long id) {
        TypedQuery<Invitation> query = 
                em.createQuery("SELECT inv FROM Invitation AS inv WHERE"
                        + " inv.user.id=:userIdParam AND inv.notifyUser=:dirtyParam", Invitation.class);
        query.setParameter("userIdParam", id);
        query.setParameter("dirtyParam", true);
        return query.getResultList();
    }
    
}
