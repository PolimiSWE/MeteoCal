package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;
import meteocal.entity.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-03T19:02:42")
@StaticMetamodel(UserList.class)
public class UserList_ { 

    public static volatile SingularAttribute<UserList, Long> id;
    public static volatile SingularAttribute<UserList, Event> event;
    public static volatile CollectionAttribute<UserList, User> users;

}