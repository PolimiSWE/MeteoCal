/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.boundary;

import java.util.Calendar;
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

    public List<WeatherData> getDbOutput(){
        TypedQuery<WeatherData> query = em.createQuery("SELECT wd FROM WeatherData AS wd", WeatherData.class);
        return query.getResultList();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeatherDataFacade() {
        super(WeatherData.class);
    }
    public List<WeatherData> getWeatherDataListFromDB(java.sql.Date dt, String city){
        TypedQuery<WeatherData> query = em.createQuery("SELECT wd FROM WeatherData AS wd WHERE  wd.date = :dateParam and wd.city = :cityParam", WeatherData.class);
        query.setParameter("dateParam", dt, TemporalType.DATE);
        query.setParameter("cityParam", city);
        //query.setParameter("date", new java.util.Date(), TemporalType.DATE);
        return query.getResultList();
    }

    public void saveWdList(List<WeatherData> wdList) {
        for(WeatherData wd: wdList){
            if(wd!=null){
                if(wd.getId()==null){
                    em.persist(wd);
                }
                else{
                    em.merge(wd);
                }
            }
        }
        em.flush();
    }
    
}
