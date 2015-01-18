package meteocal.lazydatamodel;
 
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import meteocal.entity.Invitation;
import meteocal.sorter.InvitationLazySorter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

 
/**
 * 
 *  * @author Milos
 */
public class InvitationLazyDataModel extends LazyDataModel<Invitation> {
     
    private List<Invitation> datasource;
     
    public InvitationLazyDataModel(List<Invitation> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Invitation getRowData(String rowKey) {
        for(Invitation obj : datasource) {
            if(obj.getId().toString().equals(rowKey))
                return obj;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Invitation obj) {
        return obj.getId();
    }
 
    @Override
    public List<Invitation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Invitation> data = new ArrayList<Invitation>();
 
        //filter
        for(Invitation obj : datasource) {
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
            Collections.sort(data, new InvitationLazySorter(sortField, sortOrder));
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
