package meteocal.lazyviewbeans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import meteocal.boundary.CalendarFacade;
import meteocal.entity.Calendar;
import meteocal.lazydatamodel.CalendarLazyDataModel;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Nemanja
 */
@Named(value="calendarLazyView")
@RequestScoped
public class CalendarLazyView implements Serializable {
    
    @EJB
    CalendarFacade cf;
    
    private CalendarLazyDataModel lazyModel;
     
    private Calendar selectedCalendar;
     
    @PostConstruct
    public void init() {
        lazyModel = new CalendarLazyDataModel(cf.findAll());
    }
 
    public CalendarLazyDataModel getLazyModel() {
        return lazyModel;
    }
 
    public Calendar getSelectedCalendar() {
        return selectedCalendar;
    }
 
    public void setSelectedCalendar(Calendar selectedCalendar) {
        this.selectedCalendar = selectedCalendar;
    }
     
    
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg;
        msg = new FacesMessage("Calendar Selected", this.selectedCalendar.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
