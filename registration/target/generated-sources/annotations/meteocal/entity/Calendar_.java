package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;
import meteocal.entity.EventList;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-25T13:13:03")
@StaticMetamodel(Calendar.class)
public class Calendar_ { 

    public static volatile CollectionAttribute<Calendar, EventList> eventLists;
    public static volatile SingularAttribute<Calendar, Long> id;
    public static volatile CollectionAttribute<Calendar, Event> events;

}