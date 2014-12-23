package meteocal.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.EventStatus;
import meteocal.entity.EventType;
import meteocal.entity.PrivacyType;
import meteocal.entity.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-23T15:31:16")
@StaticMetamodel(Event.class)
public class Event_ { 

    public static volatile SingularAttribute<Event, Timestamp> date;
    public static volatile SingularAttribute<Event, User> owner;
    public static volatile SingularAttribute<Event, Integer> weatherDataList;
    public static volatile SingularAttribute<Event, Integer> beginHour;
    public static volatile SingularAttribute<Event, String> city;
    public static volatile ListAttribute<Event, User> invited;
    public static volatile SingularAttribute<Event, PrivacyType> privacy;
    public static volatile SingularAttribute<Event, Integer> includedInCalendar;
    public static volatile SingularAttribute<Event, Timestamp> dateModified;
    public static volatile SingularAttribute<Event, EventType> eventType;
    public static volatile SingularAttribute<Event, Integer> duration;
    public static volatile SingularAttribute<Event, String> streetAndNumber;
    public static volatile SingularAttribute<Event, Timestamp> dateCreated;
    public static volatile SingularAttribute<Event, Timestamp> dateRescheduled;
    public static volatile SingularAttribute<Event, String> name;
    public static volatile SingularAttribute<Event, Boolean> modified;
    public static volatile SingularAttribute<Event, Long> id;
    public static volatile SingularAttribute<Event, EventStatus> status;
    public static volatile ListAttribute<Event, User> participants;

}