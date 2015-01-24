package meteocal.lazyviewbeans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import meteocal.bean.EventBean;
import meteocal.bean.UserBean;
import meteocal.boundary.EventFacade;
import meteocal.entity.Event;
import meteocal.lazydatamodel.EventLazyDataModel;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Nemanja
 */
@Named(value="eventLazyView")
@RequestScoped
public class EventLazyView implements Serializable {
    
    @EJB
    EventFacade ef;
    
    @Inject
    UserBean userData;
    @Inject
    EventBean eventData;
    
    private EventLazyDataModel lazyModel;
     
    private Event selectedEvent;
     
    @PostConstruct
    public void init() {
        try{
            lazyModel = new EventLazyDataModel(ef.findAll(userData.getUser().getMyCalendar()));
        }
        catch(Exception e){
            lazyModel = new EventLazyDataModel(new ArrayList<>());
        }
    }
 
    public EventLazyDataModel getLazyModel() {
        return lazyModel;
    }
 
    public Event getSelectedEvent() {
        return selectedEvent;
    }
 
    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
     
    
     
    public void onRowSelect(SelectEvent event) {
       this.eventData.setCurrent(this.selectedEvent);
    }
}
