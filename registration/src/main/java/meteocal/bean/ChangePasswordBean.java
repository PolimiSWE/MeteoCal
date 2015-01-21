package meteocal.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String re_pasword;
    private boolean passwordValid;
    private String code;
    private String re_code;
    private String codeMsg;
    private boolean codeValid;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private String passwordMsg;
    private User usr;

    // </editor-fold> 
    @PostConstruct
    public void init() {
        // In @PostConstruct (will be invoked immediately after construction and dependency/property injection).
        this.email = "";
        this.name = "";
        this.password = "";
        this.re_pasword = "";
        this.surname = "";
        this.username = "";
        this.passwordMsg = "";
        this.passwordValid = false;
        this.codeMsg = "";
        this.codeValid = false;
        this.usr = new User();
    }

    public void validateRePassword(FacesContext context, UIComponent toValidate, Object value) {
        if (value != null) {
            this.re_pasword = value.toString();
        } else {
            this.re_pasword = "";
        }
        if (this.re_pasword.equals(this.password)) {
            this.passwordMsg = "Password Match";
            this.passwordValid = true;
        } else {
            this.passwordMsg = "Password not Matched!!! Please reenter pasword.";
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
            this.codeMsg = "Code Match";
            this.codeValid = true;
        } else {
            this.codeMsg = "Code not valid!!! Please reenter the code.";
            this.codeValid = false;
        }
    }

    public void sendEmail(String emailAddress, String subject, String content) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        MailSendingBean msb = (MailSendingBean) context.getBean("mailSendingBean");
        msb.sendMail("meteocaltester@gmail.com", emailAddress, subject, content);
    }

    public void sendRandomCode() {
        if (this.passwordValid) {
            code = UUID.randomUUID().toString();
            try{
                usr = um.getUserByEmail(this.email);
                sendEmail(this.email, "MeteoCal--verify your identity", "Copy provided code back into the code field. Code=" + code);
            } catch(Exception e){
                
            }
        }
    }

    public void changePassword() {
        if (this.passwordValid && this.codeValid) {
            usr.setPassword(password);
            String email = usr.getEmail();
            um.save(usr);
            sendEmail(usr.getEmail(), "MeteoCal--password changed", "Dear user, your password has been changed successfully.");
            this.password = "";
            this.re_pasword = "";
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            try {
                context.redirect("userAdminPage.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ChangePasswordBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

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
    // </editor-fold> 

}
