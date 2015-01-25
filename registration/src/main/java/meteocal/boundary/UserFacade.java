/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import meteocal.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import meteocal.entity.Calendar;
import meteocal.entity.PrivacyType;

/**
 *
 * @author milos
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "authPU")
    EntityManager em;

    
    public User createNew(){
        User tmp = new User();
        TypedQuery<PrivacyType> query = em.createQuery("SELECT pt FROM PrivacyType AS pt WHERE pt.privacy=:privacyParam", PrivacyType.class);
        query.setParameter("privacyParam", false);
        PrivacyType pt = query.getResultList().get(0);
        Calendar tmp_cal = tmp.getMyCalendar();
        tmp_cal.setCalendarPrivacy(pt);
        tmp.setMyCalendar(tmp_cal);
        return tmp;
    }
    
    public void save(User usr) {
        User tmp;
        if(usr.getId() != null) 
        {
            tmp = em.find(User.class, (long)usr.getId());
            if(tmp!=null)
            {
                em.merge(usr);
                em.flush();
            }
        }
        else
        {
            em.persist(usr);
            em.flush();
        }
    }
    
    public void delete(User user) {
        User usr;
        usr = em.find(User.class, (long)user.getId());
        if(usr != null)
        {
            em.remove(usr);
        }
        em.flush();
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User AS usr", User.class);
        return query.getResultList();
    }

    public User find(int id) {
        User usr;
        usr = em.find(User.class, (long) id);
        if (usr != null) {
            return usr;
        }
        else
            return null;
    }

    public List<User> getDB_Table() {
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User AS usr", User.class);
        return query.getResultList();
    }
    
    public boolean checkUsername(User current) {
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User AS usr WHERE usr.username=:usernameParam", User.class);
        query.setParameter("usernameParam", current.getUsername());
        List<User> queryRes = query.getResultList();
        return queryRes.isEmpty();
    }
    
     public boolean checkUsername(String username) {
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User AS usr WHERE usr.username=:usernameParam", User.class);
        query.setParameter("usernameParam", username);
        List<User> queryRes = query.getResultList();
        return queryRes.isEmpty();
    }

    public boolean checkEmail(User current) {
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User AS usr WHERE usr.email=:emailParam", User.class);
        query.setParameter("emailParam", current.getEmail());
        List<User> queryRes = query.getResultList();
        return queryRes.isEmpty();
    }
    
    public boolean checkEmail(String email) {
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User AS usr WHERE usr.email=:emailParam", User.class);
        query.setParameter("emailParam", email);
        List<User> queryRes = query.getResultList();
        return queryRes.isEmpty();
    }
    
    public Boolean tryLogIn(String usrnm, String pass) {
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User AS usr WHERE usr.username=:usernameParam AND usr.password=:passwordParam", User.class);
        query.setParameter("usernameParam", usrnm);
        query.setParameter("passwordParam", pass);
        List<User> queryRes = query.getResultList();
        return !queryRes.isEmpty();
    }
    
    public User getUser(String usrnm) {
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User AS usr WHERE usr.username=:usernameParam", User.class);
        query.setParameter("usernameParam", usrnm);
        User queryRes = query.getSingleResult();
        return queryRes;
    }
    public User getUserByEmail(String email){
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User AS usr WHERE usr.email=:emailParam", User.class);
        query.setParameter("emailParam", email);
        User queryRes = query.getSingleResult();
        return queryRes;
    }

    public void unregister() {
        em.remove(getLoggedUser());
    }

    public User getLoggedUser() {
        return new User();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UserFacade(){
       super(User.class);
    }
    
}
