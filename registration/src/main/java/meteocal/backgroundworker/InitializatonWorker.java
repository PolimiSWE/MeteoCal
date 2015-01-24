package meteocal.backgroundworker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import meteocal.boundary.PrivacyTypeFacade;
import meteocal.entity.PrivacyType;

/**
 *
 * @author Nemanja
 */
@Startup
@Singleton
public class InitializatonWorker {
    
    
@EJB
PrivacyTypeFacade pf;

@PersistenceContext(unitName = "authPU")
EntityManager em;
    
@PostConstruct
    public void init() {
        em.getEntityManagerFactory().getCache().evictAll();
        em.flush();
        //see if some privacy type table is empty
        //if no do call the function init
        if (!isDatabaseInitialized()) {
            this.executeSqlInit();
        }
            em.getEntityManagerFactory().getCache().evictAll();
            em.flush();
    }
    //@Schedule(minute = "*/1", hour = "*", persistent = false)
    //@SuppressWarnings("CallToPrintStackTrace")
    /*public void debugInit() {
        executeSqlInit();
     }*/
    
    
    private void executeSqlInit() {
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("META-INF/sql_init.sql");
        String sqlScript = processInputStream(in);
        try {
            //EntityManagerFactory emf = Persistence.createEntityManagerFactory("authPU");
            //EntityManager manager = emf.createEntityManager();
            String[] lines = sqlScript.split("\n");
            //Query q = manager.createNativeQuery(sqlScript);
            //q.executeUpdate();
            for(String cmd : lines){
                Query q = em.createNativeQuery(cmd);
                q.executeUpdate();
            }
            em.flush();
            //PrivacyType pt = manager.find(meteocal.entity.PrivacyType.class, 190000000);
            String nista = "";
        } catch (Exception e) {

        }
    }

    private boolean isDatabaseInitialized() {
        try {
            List<PrivacyType> ptList = pf.getDB_Table();
            if (ptList.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private String processInputStream(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                out.append(line).append("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(BackgroundWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out.toString();
    }
}
