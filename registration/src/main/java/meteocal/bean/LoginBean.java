/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import java.io.Serializable;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import meteocal.boundary.UserFacade;
import meteocal.control.PasswordEncrypter;
import meteocal.interfaces.UserBeanInterface;

/**
 *
 * @author miglie
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable{
    
    @EJB
    UserFacade uf;
    
    @Inject
    UserBeanInterface userData;
    
    @Inject
    Principal userPrincipal;
    
    

    private String username;
    private String password;
    private Boolean usernameEntered;
    private Boolean passwordEntered;
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        this.username = "";
        this.usernameEntered = !this.username.isEmpty();
        this.password = "";
        this.passwordEntered = !this.password.isEmpty();
    }
    
    public String login() {
            if(this.password.isEmpty()){
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Password Not Entered!"));
                this.passwordEntered = false;
                return "";
            }
            if(this.username.isEmpty()){
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Usernane Not Entered!"));
                this.passwordEntered = false;
                return "";
            }
                
            if(this.tryLogIn(this.username, PasswordEncrypter.encryptPassword(this.password))){
                               
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = 
                        (HttpServletRequest) context.getExternalContext().getRequest(); 
                try {
                    Principal tmpPrincipal = request.getUserPrincipal();
                    if (tmpPrincipal != null) {
                    request.logout();
                    }
                    request.login(this.username, this.password);
                    this.userData.selectUser(this.userPrincipal.getName());
                    this.password ="";
                    this.passwordEntered = false;
                    this.username ="";
                    this.usernameEntered = false;
                    return "users/myCalendarPage?faces-redirect=true";
                } catch (ServletException e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Login Failed","Login Failed"));
                    
                    return null;
                } 
            }
            else{
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Invalid Credentials!"));
                return "";
            }
        
    }
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().invalidate();
        return "/logInPage?faces-redirect=true";
    }
    
    public Boolean tryLogIn(String usrnm, String pass){
        return uf.tryLogIn(usrnm, pass);
    }
    
    public void validateUsername(FacesContext context, UIComponent toValidate, Object value) {
        if(value!=null)
        {
            this.username = value.toString();
            this.usernameEntered = !this.username.isEmpty();
        }
        else
            this.usernameEntered = false;
    }
    
    public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
        if(value!=null) {
            this.password = value.toString();
            this.passwordEntered = !this.password.isEmpty();
        }
        else
            this.passwordEntered = false;
    }
    
    public LoginBean() {
    }

    public UserFacade getUf() {
        return uf;
    }

    public void setUf(UserFacade uf) {
        this.uf = uf;
    }

    public UserBeanInterface getUserData() {
        return userData;
    }

    public void setUserData(UserBeanInterface userData) {
        this.userData = userData;
    }

    public Boolean getUsernameEntered() {
        return usernameEntered;
    }

    public void setUsernameEntered(Boolean usernameEntered) {
        this.usernameEntered = usernameEntered;
    }

    public Boolean getPasswordEntered() {
        return passwordEntered;
    }

    public void setPasswordEntered(Boolean passwordEntered) {
        this.passwordEntered = passwordEntered;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
        if(this.username!=null){
            if(!this.username.isEmpty())
                this.validateUsername(FacesContext.getCurrentInstance(),
                        FacesContext.getCurrentInstance()
                                .getViewRoot().findComponent(":form_log_in:usernameInput"),
                        this.username);
        }
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
        if(this.username!=null){
            if(!this.password.isEmpty()){
                this.validatePassword(FacesContext.getCurrentInstance(),
                        FacesContext.getCurrentInstance()
                                .getViewRoot().findComponent(":form_log_in:passwordInput"),
                        this.password);
            }
        }
    }
}
