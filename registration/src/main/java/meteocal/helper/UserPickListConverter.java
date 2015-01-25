package meteocal.helper;
 
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import meteocal.bean.CommonBean;
import meteocal.entity.User;
 
 
@FacesConverter("userPickListConverter")
public class UserPickListConverter implements Converter {
    
@Inject
CommonBean commonData;    
 
@Override
public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
    if(value != null && value.trim().length() > 0) {
        return  commonData.getUserByString(value);
    }
    return null;
}
 
@Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((User) object).toString());
        }
        else {
            return null;
        }
    }   
} 