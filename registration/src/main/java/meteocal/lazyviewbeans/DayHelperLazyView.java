package meteocal.lazyviewbeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import meteocal.entity.Event;
import meteocal.helper.CalendarHelper;
import meteocal.lazydatamodel.DayHelperLazyDataModel;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Nemanja
 */
@Named(value="dayHelperLazyView")
@SessionScoped
public class DayHelperLazyView implements Serializable {
    
    
    private DayHelperLazyDataModel dayOnelazyModel;
    private DayHelperLazyDataModel dayTwolazyModel;
    private DayHelperLazyDataModel dayThreelazyModel;
    private DayHelperLazyDataModel dayFourlazyModel;
    private DayHelperLazyDataModel dayFivelazyModel;
    private DayHelperLazyDataModel daySixlazyModel;
    private DayHelperLazyDataModel daySevenlazyModel;
    
    private Date dayOne;
    private Date dayTwo;
    private Date dayThree;
    private Date dayFour;
    private Date dayFive;
    private Date daySix;
    private Date daySeven;
     
    private Event selectedEvent;
     
    @PostConstruct
    public void init() {
        this.dayOnelazyModel = new DayHelperLazyDataModel();
        this.dayTwolazyModel = new DayHelperLazyDataModel();
        this.dayThreelazyModel = new DayHelperLazyDataModel();
        this.dayFourlazyModel = new DayHelperLazyDataModel();
        this.dayFivelazyModel = new DayHelperLazyDataModel();
        this.daySixlazyModel = new DayHelperLazyDataModel();
        this.daySevenlazyModel = new DayHelperLazyDataModel();
    }
 
    public void initDayHelperLazyDataModel(CalendarHelper ch){
        this.dayOnelazyModel = new DayHelperLazyDataModel(ch.getCurrentWeek().get(0).getTodaysEvents());
        this.dayOne = ch.getCurrentWeek().get(0).getToday();
        this.dayTwolazyModel = new DayHelperLazyDataModel(ch.getCurrentWeek().get(1).getTodaysEvents());
        this.dayTwo = ch.getCurrentWeek().get(1).getToday();
        this.dayThreelazyModel = new DayHelperLazyDataModel(ch.getCurrentWeek().get(2).getTodaysEvents());
        this.dayThree = ch.getCurrentWeek().get(2).getToday();
        this.dayFourlazyModel = new DayHelperLazyDataModel(ch.getCurrentWeek().get(3).getTodaysEvents());
        this.dayFour = ch.getCurrentWeek().get(3).getToday();
        this.dayFivelazyModel = new DayHelperLazyDataModel(ch.getCurrentWeek().get(4).getTodaysEvents());
        this.dayFive = ch.getCurrentWeek().get(4).getToday();
        this.daySixlazyModel = new DayHelperLazyDataModel(ch.getCurrentWeek().get(5).getTodaysEvents());
        this.daySix = ch.getCurrentWeek().get(5).getToday();
        this.daySevenlazyModel = new DayHelperLazyDataModel(ch.getCurrentWeek().get(6).getTodaysEvents());
        this.daySeven = ch.getCurrentWeek().get(6).getToday();
    }

    public DayHelperLazyDataModel getDayOnelazyModel() {
        return dayOnelazyModel;
    }

    public void setDayOnelazyModel(DayHelperLazyDataModel dayOnelazyModel) {
        this.dayOnelazyModel = dayOnelazyModel;
    }

    public DayHelperLazyDataModel getDayTwolazyModel() {
        return dayTwolazyModel;
    }

    public void setDayTwolazyModel(DayHelperLazyDataModel dayTwolazyModel) {
        this.dayTwolazyModel = dayTwolazyModel;
    }

    public DayHelperLazyDataModel getDayThreelazyModel() {
        return dayThreelazyModel;
    }

    public void setDayThreelazyModel(DayHelperLazyDataModel dayThreelazyModel) {
        this.dayThreelazyModel = dayThreelazyModel;
    }

    public DayHelperLazyDataModel getDayFourlazyModel() {
        return dayFourlazyModel;
    }

    public void setDayFourlazyModel(DayHelperLazyDataModel dayFourlazyModel) {
        this.dayFourlazyModel = dayFourlazyModel;
    }

    public DayHelperLazyDataModel getDayFivelazyModel() {
        return dayFivelazyModel;
    }

    public void setDayFivelazyModel(DayHelperLazyDataModel dayFivelazyModel) {
        this.dayFivelazyModel = dayFivelazyModel;
    }

    public DayHelperLazyDataModel getDaySixlazyModel() {
        return daySixlazyModel;
    }

    public void setDaySixlazyModel(DayHelperLazyDataModel daySixlazyModel) {
        this.daySixlazyModel = daySixlazyModel;
    }

    public DayHelperLazyDataModel getDaySevenlazyModel() {
        return daySevenlazyModel;
    }

    public void setDaySevenlazyModel(DayHelperLazyDataModel daySevenlazyModel) {
        this.daySevenlazyModel = daySevenlazyModel;
    }
    
    
 
    public Event getSelectedEvent() {
        return selectedEvent;
    }
 
    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Date getDayOne() {
        return dayOne;
    }

    public void setDayOne(Date dayOne) {
        this.dayOne = dayOne;
    }

    public Date getDayTwo() {
        return dayTwo;
    }

    public void setDayTwo(Date dayTwo) {
        this.dayTwo = dayTwo;
    }

    public Date getDayThree() {
        return dayThree;
    }

    public void setDayThree(Date dayThree) {
        this.dayThree = dayThree;
    }

    public Date getDayFour() {
        return dayFour;
    }

    public void setDayFour(Date dayFour) {
        this.dayFour = dayFour;
    }

    public Date getDayFive() {
        return dayFive;
    }

    public void setDayFive(Date dayFive) {
        this.dayFive = dayFive;
    }

    public Date getDaySix() {
        return daySix;
    }

    public void setDaySix(Date daySix) {
        this.daySix = daySix;
    }

    public Date getDaySeven() {
        return daySeven;
    }

    public void setDaySeven(Date daySeven) {
        this.daySeven = daySeven;
    }
    
    public String parseDate(Integer index){
        Date tmp;
        if(index==0) tmp=this.dayOne;
        else if(index==1) tmp=this.dayTwo;
        else if(index==2) tmp=this.dayThree;
        else if(index==3) tmp=this.dayFour;
        else if(index==4) tmp=this.dayFive;
        else if(index==5) tmp=this.daySix;
        else if(index==6) tmp=this.daySeven;
        else tmp = new Date(System.currentTimeMillis());
        String output="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d/MMM/yyyy");
        output = dateFormat.format(tmp);
        return output;
    }
     
    
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg;
        msg = new FacesMessage("Event Selected", this.selectedEvent.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
