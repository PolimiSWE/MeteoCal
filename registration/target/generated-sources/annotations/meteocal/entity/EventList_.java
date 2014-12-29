package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.entity.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-29T00:45:11")
@StaticMetamodel(EventList.class)
public class EventList_ { 

    public static volatile SingularAttribute<EventList, User> owner;
    public static volatile SingularAttribute<EventList, Calendar> includedInCalendar;
    public static volatile SingularAttribute<EventList, Long> id;
    public static volatile CollectionAttribute<EventList, Event> events;

}