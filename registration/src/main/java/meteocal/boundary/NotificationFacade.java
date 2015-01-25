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
import meteocal.entity.Notification;
import meteocal.entity.User;

/**
 *
 * @author Milos
 */
@Stateless
public class NotificationFacade extends AbstractFacade<Notification> {
    @PersistenceContext(unitName = "authPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Notification> findAll(User u){
        TypedQuery<Notification> query = em.createQuery("SELECT notf FROM Notification AS notf "
                + "WHERE notf.owner.id=:ownerParam", Notification.class);
        query.setParameter("ownerParam", u.getId());
        return query.getResultList();
    }

    public NotificationFacade() {
        super(Notification.class);
    }

   

    public void removeByID(long notf_id) {
       Notification notf = em.find(Notification.class, notf_id);
       if(notf!=null)
            em.remove(notf);
       em.flush();
    }
    
}
