/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
 * @author Milos
 */
@Named(value = "registerBean")
@SessionScoped
public class RegisterBean implements Serializable {
    
    @EJB
    UserFacade uf;
    
    @Inject
    UserBeanInterface userData;
    @Inject
    LoginBean loginData;
    
    
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String re_pasword;
    private boolean usernameValid;
    private boolean emailValid;
    private boolean passwordValid;
    
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
   
    
    
    public RegisterBean() {
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        //this.email = "";
        this.emailValid = false;
        //this.name = "";
        //this.password = "";
        //this.re_pasword = "";
        //this.surname = "";
        //this.username = "";
        this.usernameValid = false;
        this.passwordValid = false;
    }
    
    public String register(){
        if(this.emailValid&&this.passwordValid&&this.usernameValid){
            this.userData.createNew();
            this.userData.setName(this.name);
            this.userData.setSurname(this.surname);
            this.userData.setUsername(this.username);
            this.userData.setEmail(this.email);
            this.userData.setPassword(password);
            this.userData.saveUser();
            if(this.loginData.tryLogIn(this.username, PasswordEncrypter.encryptPassword(this.password))){
                               
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = 
                        (HttpServletRequest) context.getExternalContext().getRequest(); 
                try {
                    Principal tmpPrincipal = request.getUserPrincipal();
                    if (tmpPrincipal != null) {
                    request.logout();
                    }
                    request.login(this.username, this.password);
                    tmpPrincipal = request.getUserPrincipal();
                    this.userData.selectUser(tmpPrincipal.getName());
                    this.init();
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
        return "";
    }
    
    
    public void validateUsername(FacesContext context, UIComponent toValidate, Object value) {
       if(value!=null)
           this.username = value.toString();
       else 
           this.username = "";
       if(this.usernameValid!=true){
           if(uf.checkUsername(this.username)){
               FacesContext.getCurrentInstance()
                        .addMessage(null, 
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        "Username OK.",
                                        "Usename OK."));
               this.usernameValid = true;
           }
           else{
               FacesContext.getCurrentInstance()
                        .addMessage(null, 
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Error! Username not available. Please select different username.",
                                        "Invalid Username!"));
               this.usernameValid = false;
           }
       }
    }
    
    public void validateEmail(FacesContext context, UIComponent toValidate, Object value) {
       String tmpValue;
       if(this.emailValid!=true){
           if(value!=null)
               tmpValue = value.toString();
           else
               tmpValue = "";
           Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(tmpValue);
           if(matcher.find()){ 
               this.email = tmpValue;
               if(uf.checkEmail(this.email)){
                   FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Email OK.",
                                            "Email OK."));
                   this.emailValid = true;
               }
               else{
                   FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Error! Email not available. Please select different email address.",
                                            "Invalid Username!"));
                   emailValid = false;
               }
           }
           else{
               FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Error! Email not formed well.",
                                            "Invalid email!"));
               emailValid = false;
           }
        }
    }
    
    public void validateRePassword(FacesContext context, UIComponent toValidate, Object value) {
        if(value!=null)
           this.re_pasword = value.toString();
        else
           this.re_pasword = "";
        if(this.re_pasword.equals(this.password)){
           FacesContext.getCurrentInstance()
                        .addMessage(null, 
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        "Passwords MATCH.",
                                        "Passwords MATCH."));
           this.passwordValid = true;
        }
        else{
           FacesContext.getCurrentInstance()
                        .addMessage(null, 
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Error! Passwords do not match.",
                                        "Error! Passwords do not match."));
           this.passwordValid = false;
        }
    }
    
    public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
       if(value!=null)
           this.password = value.toString();
       else
           this.password = "";
    }
    
    public void checkAllDataOK(){
        if(this.emailValid && this.passwordValid && this.usernameValid){
            FacesContext.getCurrentInstance()
                        .addMessage(null, 
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        "All data valid. Please proceed.",
                                        "All data valid!"));
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_pasword() {
        return re_pasword;
    }

    public void setRe_pasword(String re_pasword) {
        this.re_pasword = re_pasword;
    }

    public boolean isUsernameValid() {
        return usernameValid;
    }

    public void setUsernameValid(boolean usernameValid) {
        this.usernameValid = usernameValid;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }

    public boolean isPasswordValid() {
        return passwordValid;
    }

    public void setPasswordValid(boolean passwordValid) {
        this.passwordValid = passwordValid;
    }
    
}
