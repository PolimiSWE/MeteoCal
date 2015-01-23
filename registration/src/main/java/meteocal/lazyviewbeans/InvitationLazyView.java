package meteocal.lazyviewbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import meteocal.boundary.InvitationFacade;
import meteocal.entity.Invitation;
import meteocal.lazydatamodel.InvitationLazyDataModel;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Nemanja
 */
@Named(value="invitationLazyView")
@SessionScoped
public class InvitationLazyView implements Serializable {
    
    @EJB
    InvitationFacade invf;
    
    private InvitationLazyDataModel lazyModel;
     
    private Invitation selectedInvitation;
     
    @PostConstruct
    public void init() {
        lazyModel = new InvitationLazyDataModel(new ArrayList<>());
    }
    
    public void initInvitationDataModel(List<Invitation> invites){
        lazyModel = new InvitationLazyDataModel(invites);
    }
 
    public InvitationLazyDataModel getLazyModel() {
        return lazyModel;
    }
 
    public Invitation getSelectedInvitation() {
        return selectedInvitation;
    }
 
    public void setSelectedInvitation(Invitation selectedInvitation) {
        this.selectedInvitation = selectedInvitation;
    }
     
    public void update(long userId){
        lazyModel = new InvitationLazyDataModel(invf.findAll(userId));
    }
    
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg;
        msg = new FacesMessage("Invitation Selected", this.selectedInvitation.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
