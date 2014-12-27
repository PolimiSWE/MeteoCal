package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;
import meteocal.entity.Invitation;
import meteocal.entity.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-27T20:30:38")
@StaticMetamodel(InvitationList.class)
public class InvitationList_ { 

    public static volatile CollectionAttribute<InvitationList, Invitation> invitations;
    public static volatile SingularAttribute<InvitationList, Long> id;
    public static volatile SingularAttribute<InvitationList, Event> event;
    public static volatile SingularAttribute<InvitationList, User> invitedUser;

}