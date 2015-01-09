package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;
import meteocal.entity.PrivacyType;
import meteocal.entity.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-08T17:52:18")
@StaticMetamodel(Calendar.class)
public class Calendar_ { 

    public static volatile SingularAttribute<Calendar, User> owner;
    public static volatile SingularAttribute<Calendar, PrivacyType> calendarPrivacy;
    public static volatile SingularAttribute<Calendar, Long> id;
    public static volatile CollectionAttribute<Calendar, Event> events;

}