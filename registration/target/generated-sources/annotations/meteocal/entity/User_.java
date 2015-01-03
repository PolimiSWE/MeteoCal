package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Calendar;
import meteocal.entity.Event;
import meteocal.entity.EventList;
import meteocal.entity.Invitation;
import meteocal.entity.InvitationList;
import meteocal.entity.UserList;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-03T22:06:25")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, EventList> eventList;
    public static volatile SingularAttribute<User, InvitationList> invitationList;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> groupName;
    public static volatile SingularAttribute<User, UserList> userList;
    public static volatile CollectionAttribute<User, Invitation> invitations;
    public static volatile SingularAttribute<User, String> surname;
    public static volatile SingularAttribute<User, String> name;
    public static volatile CollectionAttribute<User, Event> ownedEvents;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, Calendar> myCalendar;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}