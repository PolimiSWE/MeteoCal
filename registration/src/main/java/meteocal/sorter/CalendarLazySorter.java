package meteocal.sorter;

import java.lang.reflect.Field;
import java.util.Comparator;
import meteocal.entity.Calendar;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Nemanja
 */
public class CalendarLazySorter implements Comparator<Calendar> {
 
    private String sortField;
     
    private SortOrder sortOrder;
    
    public CalendarLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
    
    @Override
    public int compare(Calendar o1, Calendar o2)  {
        try {
            Field userField = Calendar.class.getDeclaredField(this.sortField);
            userField.setAccessible(true);
            Object value1 = userField.get(o1);
            Object value2 = userField.get(o2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

}
