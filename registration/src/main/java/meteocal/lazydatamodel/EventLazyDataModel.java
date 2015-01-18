package meteocal.lazydatamodel;
 
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import meteocal.entity.Event;
import meteocal.sorter.EventLazySorter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

 
/**
 * 
 *  * @author Milos
 */
public class EventLazyDataModel extends LazyDataModel<Event> {
     
    private List<Event> datasource;
     
    public EventLazyDataModel(List<Event> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Event getRowData(String rowKey) {
        for(Event obj : datasource) {
            if(obj.getId().toString().equals(rowKey))
                return obj;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Event obj) {
        return obj.getId();
    }
 
    @Override
    public List<Event> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Event> data = new ArrayList<Event>();
 
        //filter
        for(Event obj : datasource) {
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
            Collections.sort(data, new EventLazySorter(sortField, sortOrder));
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
