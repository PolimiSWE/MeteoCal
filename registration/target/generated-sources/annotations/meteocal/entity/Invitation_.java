package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;
import meteocal.entity.EventStatus;
import meteocal.entity.InvitationList;
import meteocal.entity.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-27T20:30:38")
@StaticMetamodel(Invitation.class)
public class Invitation_ { 

    public static volatile SingularAttribute<Invitation, EventStatus> eventStatus;
    public static volatile SingularAttribute<Invitation, Long> id;
    public static volatile SingularAttribute<Invitation, InvitationList> invitationList;
    public static volatile SingularAttribute<Invitation, Event> event;
    public static volatile SingularAttribute<Invitation, User> user;

}