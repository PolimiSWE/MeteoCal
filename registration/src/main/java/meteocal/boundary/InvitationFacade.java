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
import meteocal.entity.Event;
import meteocal.entity.EventStatus;
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

    public void declineInvite(Event evt, User usr) {
        TypedQuery<Invitation> query = 
                em.createQuery("SELECT inv FROM Invitation AS inv "
                        + "WHERE inv.user.id=:userIdParam AND inv.event.id=:eventIdParam", Invitation.class);
        query.setParameter("userIdParam", usr.getId());
        query.setParameter("eventIdParam", evt.getId());
        Invitation inv = query.getSingleResult();
        TypedQuery<EventStatus> query2 = em.createQuery("SELECT es FROM EventStatus AS es "
                + "WHERE es.status=2", EventStatus.class);
        EventStatus decline = query2.getSingleResult();
        inv.setEventStatus(decline);
        em.merge(inv);
        em.flush();
    }

    public void acceptInvite(Invitation inv) {
        TypedQuery<EventStatus> query = em.createQuery("SELECT es FROM EventStatus AS es "
                + "WHERE es.status=1", EventStatus.class);
        EventStatus accepted = query.getSingleResult();
        inv.setEventStatus(accepted);
        em.merge(inv);
        em.flush();
    }

    public void declineInvite(Invitation inv) {
        TypedQuery<EventStatus> query = em.createQuery("SELECT es FROM EventStatus AS es "
                + "WHERE es.status=2", EventStatus.class);
        EventStatus decline = query.getSingleResult();
        inv.setEventStatus(decline);
        em.merge(inv);
        em.flush();
    }
    
}
