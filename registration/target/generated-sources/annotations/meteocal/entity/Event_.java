package meteocal.entity;

import java.sql.Time;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Calendar;
import meteocal.entity.EventType;
import meteocal.entity.Invitation;
import meteocal.entity.PrivacyType;
import meteocal.entity.WeatherDataList;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-08T17:52:18")
@StaticMetamodel(Event.class)
public class Event_ { 

    public static volatile SingularAttribute<Event, WeatherDataList> weatherDataList;
    public static volatile SingularAttribute<Event, Time> beginHour;
    public static volatile SingularAttribute<Event, String> city;
    public static volatile SingularAttribute<Event, Calendar> includedInCalendar;
    public static volatile SingularAttribute<Event, Date> dateModified;
    public static volatile SingularAttribute<Event, PrivacyType> eventPrivacy;
    public static volatile SingularAttribute<Event, EventType> eventType;
    public static volatile SingularAttribute<Event, Integer> duration;
    public static volatile SingularAttribute<Event, String> streetAndNumber;
    public static volatile SingularAttribute<Event, Date> dateCreated;
    public static volatile SingularAttribute<Event, Date> dateOfEvent;
    public static volatile CollectionAttribute<Event, Invitation> invitations;
    public static volatile SingularAttribute<Event, Date> dateRescheduled;
    public static volatile SingularAttribute<Event, String> name;
    public static volatile SingularAttribute<Event, Boolean> modified;
    public static volatile SingularAttribute<Event, Long> id;

}