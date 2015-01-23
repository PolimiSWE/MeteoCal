package meteocal.lazyviewbeans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import meteocal.bean.EventBean;
import meteocal.boundary.WeatherDataFacade;
import meteocal.entity.WeatherData;
import meteocal.lazydatamodel.WeatherDataLazyDataModel;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Nemanja
 */
@Named(value="weatherDataLazyView")
@RequestScoped
public class WeatherDataLazyView implements Serializable {
    
    @EJB
    WeatherDataFacade wf;
    
    @Inject
    DayHelperLazyView calPreview;
    
    private WeatherDataLazyDataModel lazyModel;
     
    private WeatherData selectedWeatherData;
     
    @PostConstruct
    public void init() {
        try{
            lazyModel = new WeatherDataLazyDataModel(
                    wf.getWeatherDataListFromDB(this.calPreview.getSelectedEvent().getCity(),
                                        new  java.sql.Date(this.calPreview.getSelectedEvent().getDateOfEvent().getTime())));
        }
        catch(Exception npe){
            lazyModel = new WeatherDataLazyDataModel(new ArrayList<>());
        }
    }
 
    public WeatherDataLazyDataModel getLazyModel() {
        return lazyModel;
    }
 
    public WeatherData getSelectedWeatherData() {
        return selectedWeatherData;
    }
 
    public void setSelectedWeatherData(WeatherData selectedWeatherData) {
        this.selectedWeatherData = selectedWeatherData;
    }
     
    
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg;
        msg = new FacesMessage("WeatherData Selected", this.selectedWeatherData.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
