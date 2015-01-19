package meteocal.lazyviewbeans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
    
    private WeatherDataLazyDataModel lazyModel;
     
    private WeatherData selectedWeatherData;
     
    @PostConstruct
    public void init() {
        lazyModel = new WeatherDataLazyDataModel(wf.findAll());
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
