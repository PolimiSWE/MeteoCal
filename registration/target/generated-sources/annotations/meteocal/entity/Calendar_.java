package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-24T14:54:54")
@StaticMetamodel(Calendar.class)
public class Calendar_ { 

    public static volatile SingularAttribute<Calendar, Long> id;
    public static volatile CollectionAttribute<Calendar, Event> events;

}