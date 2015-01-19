/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
            if(this.tryLogIn(this.username, PasswordEncrypter.encryptPassword(this.password))){
                this.userData.selectUser(username);
                return "myCalendarPage"; 
            }
            else
                return "logInPage";
        
    }
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().invalidate();
        return "/index?faces-redirect=true";
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
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
