package meteocal.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.Event;
import meteocal.entity.WeatherData;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-02T20:24:30")
@StaticMetamodel(WeatherDataList.class)
public class WeatherDataList_ { 

    public static volatile CollectionAttribute<WeatherDataList, WeatherData> weatherDataList;
    public static volatile SingularAttribute<WeatherDataList, Long> id;
    public static volatile SingularAttribute<WeatherDataList, Event> event;

}