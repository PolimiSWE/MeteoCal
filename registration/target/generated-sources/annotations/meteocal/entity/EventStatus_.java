package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-23T15:31:16")
@StaticMetamodel(EventStatus.class)
public class EventStatus_ { 

    public static volatile CollectionAttribute<EventStatus, Event> eventList;
    public static volatile SingularAttribute<EventStatus, Long> id;
    public static volatile SingularAttribute<EventStatus, Integer> status;

}