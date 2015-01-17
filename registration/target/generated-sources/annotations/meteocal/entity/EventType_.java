package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-17T20:23:45")
@StaticMetamodel(EventType.class)
public class EventType_ { 

    public static volatile CollectionAttribute<EventType, Event> eventList;
    public static volatile SingularAttribute<EventType, Long> id;
    public static volatile SingularAttribute<EventType, Boolean> type;

}