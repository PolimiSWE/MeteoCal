package meteocal.lazyviewbeans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
    
    private EventLazyDataModel lazyModel;
     
    private Event selectedEvent;
     
    @PostConstruct
    public void init() {
        lazyModel = new EventLazyDataModel(ef.findAll());
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
        FacesMessage msg;
        msg = new FacesMessage("Event Selected", this.selectedEvent.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
