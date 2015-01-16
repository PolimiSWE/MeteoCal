package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Invitation;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-16T13:51:19")
@StaticMetamodel(EventStatus.class)
public class EventStatus_ { 

    public static volatile SingularAttribute<EventStatus, Long> id;
    public static volatile CollectionAttribute<EventStatus, Invitation> invitationList;
    public static volatile SingularAttribute<EventStatus, Integer> status;

}