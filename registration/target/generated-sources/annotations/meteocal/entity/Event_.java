package meteocal.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Calendar;
import meteocal.entity.EventList;
import meteocal.entity.EventType;
import meteocal.entity.Invitation;
import meteocal.entity.InvitationList;
import meteocal.entity.PrivacyType;
import meteocal.entity.User;
import meteocal.entity.UserList;
import meteocal.entity.WeatherDataList;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-29T00:45:11")
@StaticMetamodel(Event.class)
public class Event_ { 

    public static volatile SingularAttribute<Event, User> owner;
    public static volatile SingularAttribute<Event, EventList> eventList;
    public static volatile SingularAttribute<Event, WeatherDataList> weatherDataList;
    public static volatile SingularAttribute<Event, Integer> beginHour;
    public static volatile SingularAttribute<Event, String> city;
    public static volatile SingularAttribute<Event, Calendar> includedInCalendar;
    public static volatile SingularAttribute<Event, Timestamp> dateModified;
    public static volatile SingularAttribute<Event, PrivacyType> eventPrivacy;
    public static volatile SingularAttribute<Event, EventType> eventType;
    public static volatile SingularAttribute<Event, InvitationList> invitationList;
    public static volatile SingularAttribute<Event, Integer> duration;
    public static volatile SingularAttribute<Event, String> streetAndNumber;
    public static volatile SingularAttribute<Event, Timestamp> dateCreated;
    public static volatile SingularAttribute<Event, UserList> userList;
    public static volatile SingularAttribute<Event, Timestamp> dateOfEvent;
    public static volatile CollectionAttribute<Event, Invitation> invitations;
    public static volatile SingularAttribute<Event, Timestamp> dateRescheduled;
    public static volatile SingularAttribute<Event, String> name;
    public static volatile SingularAttribute<Event, Boolean> modified;
    public static volatile SingularAttribute<Event, Long> id;

}