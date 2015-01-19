package meteocal.sorter;

import java.lang.reflect.Field;
import java.util.Comparator;
import meteocal.entity.WeatherData;
import org.primefaces.model.SortOrder;
/**
 *
 * @author Nemanja
 */
public class WeatherDataLazySorter implements Comparator<WeatherData> {
 
    private String sortField;
     
    private SortOrder sortOrder;
    
    public WeatherDataLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
    
    @Override
    public int compare(WeatherData o1, WeatherData o2)  {
        try {
            Field userField = WeatherData.class.getDeclaredField(this.sortField);
            userField.setAccessible(true);
            Object value1 = userField.get(o1);
            Object value2 = userField.get(o2);
            ///////////
            int value = ((Comparable)value1).compareTo(value2);
            if(this.sortField.equals("date")){
                long diff = ((java.util.Date) value1).getTime() - ((java.util.Date) value2).getTime();
                if(diff<0)
                    value = -1;
                else
                    value = 1;
            }
             //////////////
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

}
