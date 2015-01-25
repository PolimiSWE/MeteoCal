/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import meteocal.entity.WeatherData;

/**
 *
 * @author Milos
 */
@Stateless
public class WeatherDataFacade extends AbstractFacade<WeatherData> {

    @PersistenceContext(unitName = "authPU")
    private EntityManager em;

    public List<WeatherData> getDbOutput() {
        TypedQuery<WeatherData> query = em.createQuery("SELECT wd FROM WeatherData AS wd", WeatherData.class);
        List<WeatherData> result = query.getResultList();
        if (result != null) {
            return query.getResultList();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeatherDataFacade() {
        super(WeatherData.class);
    }

    public List<WeatherData> getWeatherDataListFromDB(String city, java.sql.Date dt) {
        TypedQuery<WeatherData> query = em.createQuery("SELECT wd FROM WeatherData AS wd"
                + " WHERE  wd.date = :dateParam and wd.city = :cityParam", WeatherData.class);
        query.setParameter("dateParam", dt, TemporalType.DATE);
        query.setParameter("cityParam", city);
        //query.setParameter("date", new java.util.Date(), TemporalType.DATE);
        List<WeatherData> result = query.getResultList();
        if (result != null) {
            return query.getResultList();
        } else {
            return new ArrayList<>();
        }
    }

    public void saveWdList(List<WeatherData> wdList) {
        for (WeatherData wd : wdList) {
            if (wd != null) {
                if (wd.getId() == null) {
                    em.persist(wd);
                    em.flush();
                } else {
                    WeatherData wdd = this.find(wd.getId());
                    if (wdd == null) {
                        em.persist(wd);
                        em.flush();
                    } else {
                        em.merge(wd);
                        em.flush();
                    }
                }
            }
        }
        em.flush();
    }

    //deletes weatherdata for given city and date
    public void deleteWdByCityAndDate(String city, java.sql.Date dt) {
        TypedQuery<WeatherData> query = em.createQuery("DELETE FROM WeatherData wd WHERE "
                + "wd.date = :dateParam and wd.city = :cityParam", WeatherData.class);
        query.setParameter("dateParam", dt, TemporalType.DATE);
        query.setParameter("cityParam", city);
        int deleted = query.executeUpdate();
        em.flush();
    }

    //deletes weatherdata for given date

    public void deleteWdByDate(java.sql.Date dt) {
        TypedQuery<WeatherData> query = em.createQuery("DELETE FROM WeatherData wd WHERE "
                + "wd.date = :dateParam", WeatherData.class);
        query.setParameter("dateParam", dt, TemporalType.DATE);
        int deleted = query.executeUpdate();
        em.flush();
    }

    @Override
    public List<WeatherData> findAll() {
        TypedQuery<WeatherData> query = em.createQuery("SELECT wd FROM WeatherData AS wd", WeatherData.class);
        return query.getResultList();
    }

    public WeatherData find(int id) {
        WeatherData wd;
        wd = em.find(WeatherData.class, (long) id);
        if (wd != null) {
            return wd;
        } else {
            return null;
        }
    }
}