package meteocal.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import meteocal.boundary.UserFacade;
import meteocal.entity.User;
import meteocal.interfaces.CalendarBeanInterface;
import meteocal.interfaces.CommonBeanInterface;
import meteocal.interfaces.UserBeanInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Nemanja
 */
@Named(value = "changePasswordBean")
@SessionScoped
public class ChangePasswordBean implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="@EJB annotated elements">

    @EJB
    UserFacade um;
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Data Members and Managed Properties">
    @Inject
    CommonBeanInterface commonData;
    @Inject
    CalendarBeanInterface calendarData;
    @Inject
    UserBeanInterface userData;
    @Inject
    LoginBean logInData;

    private String email;
    private boolean emailValid;
    private String username;
    private String password;
    private String re_pasword;
    private boolean passwordValid;
    private String code;
    private String re_code;
    private String codeMsg;
    private boolean codeValid;
    private boolean codeSent;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private String passwordMsg;
    private User usr;

    // </editor-fold> 
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        this.email = "";
        this.password = "";
        this.re_pasword = "";
        this.username = "";
        this.passwordMsg = "";
        this.codeMsg = "";
        this.emailValid = false;
        this.passwordValid = false;
        this.codeValid = false;
        this.usr = new User();
        this.codeSent = false;
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
               if(um.checkEmail(this.email)){
                   FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Error! Email doesn't exist in our database.",
                                            "Invalid email."));
                   this.emailValid = false;
               }
               else{
                   FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Email found. Please proceed by typing in the rest of the data.",
                                            "Email found."));
                   emailValid = true;
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

    public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
       if(value!=null)
           this.password = value.toString();
       else
           this.password = "";
    }
    
    public void validateRePassword(FacesContext context, UIComponent toValidate, Object value) {
        if (value != null) {
            this.re_pasword = value.toString();
        } else {
            this.re_pasword = "";
        }
        if (this.re_pasword.equals(this.password)) {
               FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Passwords  matched. Please continue.",
                                            "Password matched."));
            this.passwordValid = true;
        } else {
               FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Passwords not Matched!!! Please reenter the repeated password.",
                                            "Invalid repeated password!"));
            this.passwordValid = false;
        }
    }

    public void validateReCode(FacesContext context, UIComponent toValidate, Object value) {
        if (value != null) {
            this.re_code = value.toString();
        } else {
            this.re_code = "";
        }
        if (this.re_code.equals(this.code)) {
            FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Code valid. Your password will be changed upon submit.",
                                            "Code valid."));
            this.codeValid = true;
        } else {
            FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Error : 'Code not valid!'",
                                            "Code not valid."));
            this.codeValid = false;
        }
    }

    public void sendEmail(String emailAddress, String subject, String content) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        MailSendingBean msb = (MailSendingBean) context.getBean("mailSendingBean");
        msb.sendMail("meteocaltester@gmail.com", emailAddress, subject, content);
    }

    public void sendRandomCode() {
        if (this.emailValid && this.passwordValid) {
            code = UUID.randomUUID().toString();
            try{
                usr = um.getUserByEmail(this.email);
                sendEmail(this.email, "MeteoCal--verify your identity", 
                        "Copy provided code back into the code field. Code= " + code);
                this.codeSent = true;
            } catch(Exception e){
                FacesContext.getCurrentInstance()
                            .addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Error : 'No such Email in the system!'",
                                            "Invalid email"));
            }
        }
    }

    public void changePassword() {
        if (this.emailValid && this.passwordValid && this.codeValid) {
            usr.setPassword(password);
            String email = usr.getEmail();
            um.save(usr);
            sendEmail(usr.getEmail(), "MeteoCal--password changed", "Dear user, your password has been changed successfully.");
            this.password = "";
            this.re_pasword = "";
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            try {
                this.logInData.setPassword(usr.getPassword());
                this.logInData.setUsername(usr.getUsername());
                this.init();
                context.redirect("logInPage.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ChangePasswordBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void checkAllDataOK(){
        if(this.emailValid && this.passwordValid && this.codeValid){
            FacesContext.getCurrentInstance()
                        .addMessage(null, 
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        "All data valid. Please proceed.",
                                        "All data valid!"));
        }
    }
    
    public String goToCPPage(){
        this.init();
        return "changePasswordPage?faces-redirect=true";
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    public boolean isCodeSent() {
        return codeSent;
    }

    public void setCodeSent(boolean codeSent) {
        this.codeSent = codeSent;
    }

    public UserFacade getUm() {
        return um;
    }

    public void setUm(UserFacade um) {
        this.um = um;
    }

    public CommonBeanInterface getCommonData() {
        return commonData;
    }

    public void setCommonData(CommonBeanInterface commonData) {
        this.commonData = commonData;
    }

    public CalendarBeanInterface getCalendarData() {
        return calendarData;
    }

    public void setCalendarData(CalendarBeanInterface calendarData) {
        this.calendarData = calendarData;
    }

    public UserBeanInterface getUserData() {
        return userData;
    }

    public void setUserData(UserBeanInterface userData) {
        this.userData = userData;
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

    public boolean isPasswordValid() {
        return passwordValid;
    }

    public void setPasswordValid(boolean passwordValid) {
        this.passwordValid = passwordValid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRe_code() {
        return re_code;
    }

    public void setRe_code(String re_code) {
        this.re_code = re_code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public boolean isCodeValid() {
        return codeValid;
    }

    public void setCodeValid(boolean codeValid) {
        this.codeValid = codeValid;
    }

    public String getPasswordMsg() {
        return passwordMsg;
    }

    public void setPasswordMsg(String passwordMsg) {
        this.passwordMsg = passwordMsg;
    }
    
    public boolean isEmailValid() {
        return emailValid;
    }

    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }
    // </editor-fold> 


}
