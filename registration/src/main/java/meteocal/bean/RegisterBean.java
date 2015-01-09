/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.bean;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import meteocal.boundary.UserFacade;
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
    
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String re_pasword;
    private String usernameMsg;
    private boolean usernameValid;
    private boolean emailValid;
    private String emailMsg;
    private boolean passwordValid;
    
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private String passwordMsg;
    
    
    public RegisterBean() {
    }
    
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        this.email = "";
        this.emailMsg = "";
        this.emailValid = false;
        this.name = "";
        this.password = "";
        this.re_pasword = "";
        this.surname = "";
        this.username = "";
        this.usernameMsg = "";
        this.usernameValid = false;
    }
    
    public void register(){
        if(this.emailValid&&this.passwordValid&&this.usernameValid){
            this.userData.createNew();
            this.userData.setName(this.name);
            this.userData.setSurname(this.surname);
            this.userData.setUsername(this.username);
            this.userData.setEmail(this.email);
            this.userData.setPassword(password);
            this.userData.saveUser();
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
            try {
                context.redirect("userAdminPage.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public void validateUsername(FacesContext context, UIComponent toValidate, Object value) {
       if(value!=null)
           this.username = value.toString();
       else 
           this.username = "";
       if(uf.checkUsername(this.username)){
           this.usernameMsg = "Username not taken.";
           this.usernameValid = true;
       }
       else{
           this.usernameMsg = "Username TAKEN!!! Please select different username";
           this.usernameValid = false;
       }
    }
    
    public void validateEmail(FacesContext context, UIComponent toValidate, Object value) {
       String tmpValue;
       if(value!=null)
           tmpValue = value.toString();
       else
           tmpValue = "";
       Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(tmpValue);
       if(matcher.find()){ 
           this.email = tmpValue;
           if(uf.checkEmail(this.email)){
               this.emailMsg = "Email not taken.";
               this.emailValid = true;
           }
           else{
               this.emailMsg = "Email Address TAKEN!!! Please select different email address";
               emailValid = false;
           }
       }
       else{
           this.emailMsg = "Email address is not well formed!!!";
           emailValid = false;
       }
    }
    
    public void validateRePassword(FacesContext context, UIComponent toValidate, Object value) {
        if(value!=null)
           this.re_pasword = value.toString();
       else
           this.re_pasword = "";
       if(this.re_pasword.equals(this.password)){
           this.passwordMsg = "Password Match";
           this.passwordValid = true;
       }
       else{
           this.passwordMsg = "Password not Matched!!! Please reenter pasword.";
           this.passwordValid = false;
       }
    }
    
    public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
       if(value!=null)
           this.password = value.toString();
       else
           this.password = "";
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

    public String getUsernameMsg() {
        return usernameMsg;
    }

    public void setUsernameMsg(String usernameMsg) {
        this.usernameMsg = usernameMsg;
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

    public String getEmailMsg() {
        return emailMsg;
    }

    public void setEmailMsg(String emailMsg) {
        this.emailMsg = emailMsg;
    }

    public boolean isPasswordValid() {
        return passwordValid;
    }

    public void setPasswordValid(boolean passwordValid) {
        this.passwordValid = passwordValid;
    }

    public String getPasswordMsg() {
        return passwordMsg;
    }

    public void setPasswordMsg(String passwordMsg) {
        this.passwordMsg = passwordMsg;
    }
    
    
    
}
