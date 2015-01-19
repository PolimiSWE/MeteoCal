package meteocal.sorter;

import java.lang.reflect.Field;
import java.util.Comparator;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.helper.DayHelper;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Nemanja
 */
public class DayHelperLazySorter implements Comparator<Event> {
 
    private String sortField;
     
    private SortOrder sortOrder;
    
    public DayHelperLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
    
    @Override
    public int compare(Event o1, Event o2)  {
        try {
            Field usedField = Event.class.getDeclaredField(this.sortField);
            usedField.setAccessible(true);
            Object value1 = usedField.get(o1);
            Object value2 = usedField.get(o2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

}
