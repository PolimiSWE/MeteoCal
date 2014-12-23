package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-23T15:31:15")
@StaticMetamodel(PrivacyType.class)
public class PrivacyType_ { 

    public static volatile CollectionAttribute<PrivacyType, Event> eventList;
    public static volatile SingularAttribute<PrivacyType, Boolean> privacy;
    public static volatile SingularAttribute<PrivacyType, Long> id;

}