package meteocal.lazydatamodel;
 
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import meteocal.entity.User;
import meteocal.sorter.UserLazySorter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

 
/**
 * 
 *  * @author Milos
 */
public class UserLazyDataModel extends LazyDataModel<User> {
     
    private List<User> datasource;
     
    public UserLazyDataModel(List<User> datasource) {
        this.datasource = new ArrayList<>();
        for(User u : datasource){
            if(u.getMyCalendar().getCalendarPrivacy().getPrivacy())
                this.datasource.add(u);
        }
    }
    
    public void remove(User u){
        int index = -1; 
        int i=0;
        for(User us: this.datasource){
            if(Objects.equals(us.getId(), u.getId()))
                index = i;
            i++;
        }    
        if(index!=-1)
            this.datasource.remove(index);
    }
    
    public User getAt(int index){
        return this.datasource.get(index);
    }
     
    @Override
    public User getRowData(String rowKey) {
        for(User obj : datasource) {
            if(obj.getId().toString().equals(rowKey))
                return obj;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(User obj) {
        return obj.getId();
    }
 
    @Override
    public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<User> data = new ArrayList<User>();
 
        //filter
        for(User obj : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field objField = obj.getClass().getDeclaredField(filterProperty);
                        objField.setAccessible(true);
                        String fieldValue = String.valueOf(objField.get(obj));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(obj);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new UserLazySorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}
