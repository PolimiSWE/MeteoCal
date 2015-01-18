/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.sorter;
 
import java.lang.reflect.Field;
import java.util.Comparator;
import meteocal.entity.User;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Milos
 */
 
public class UserLazySorter implements Comparator<User> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public UserLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @Override
    public int compare(User usr1, User usr2) {
        try {
            Field userField = User.class.getDeclaredField(this.sortField);
            userField.setAccessible(true);
            Object value1 = userField.get(usr1);
            Object value2 = userField.get(usr2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}

