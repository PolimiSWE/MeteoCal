package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Calendar;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-27T20:30:38")
@StaticMetamodel(CalendarList.class)
public class CalendarList_ { 

    public static volatile SingularAttribute<CalendarList, Long> id;
    public static volatile CollectionAttribute<CalendarList, Calendar> calendarList;

}