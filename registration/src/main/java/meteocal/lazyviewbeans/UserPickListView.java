package meteocal.lazyviewbeans;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import meteocal.bean.EventBean;
import meteocal.entity.User;
import org.primefaces.event.SelectEvent;
 
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

 
@Named(value="userPickListView")
@SessionScoped
public class UserPickListView implements Serializable {
     
    @Inject 
    EventBean eventData;
    
    
    private DualListModel<User> users;
     
    @PostConstruct
    public void init() {
        
        List<User> sourceUsers = eventData.getAll_users();
        List<User> destUsers = eventData.getCurrent_invited();
         
        users = new DualListModel<>(sourceUsers, destUsers);
          
    }
 
    
   
    
 
    
 
    public DualListModel<User> getUsers() {
        return users;
    }
 
    public void setUsers(DualListModel<User> users) {
        this.users = users;
    }
     
    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for(Object item : event.getItems()) {
            builder.append(((User) item).getName()).append("<br />");
        }
        
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("User Transferred");
        msg.setDetail(builder.toString());
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
    } 
 
    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "User selected for event.", 
                        event.getObject().toString()));
    }
     
    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"User unselected for event.", 
                event.getObject().toString()));
    }
     
    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    } 
}