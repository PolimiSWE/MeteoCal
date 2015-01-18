package meteocal.lazydatamodel;
 
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
        this.datasource = datasource;
    }
     
    @Override
    public User getRowData(String rowKey) {
        for(User usr : datasource) {
            if(usr.getId().toString().equals(rowKey))
                return usr;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(User car) {
        return car.getId();
    }
 
    @Override
    public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<User> data = new ArrayList<User>();
 
        //filter
        for(User usr : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field userField = usr.getClass().getDeclaredField(filterProperty);
                        userField.setAccessible(true);
                        String fieldValue = String.valueOf(userField.get(usr));
 
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
                data.add(usr);
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
