package meteocal.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.WeatherDataList;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-06T03:49:06")
@StaticMetamodel(WeatherData.class)
public class WeatherData_ { 

    public static volatile SingularAttribute<WeatherData, Timestamp> date;
    public static volatile SingularAttribute<WeatherData, Double> preasure;
    public static volatile SingularAttribute<WeatherData, Integer> hour;
    public static volatile SingularAttribute<WeatherData, Integer> temperature;
    public static volatile SingularAttribute<WeatherData, WeatherDataList> ownerList;
    public static volatile SingularAttribute<WeatherData, Long> id;
    public static volatile SingularAttribute<WeatherData, Double> windSpeed;
    public static volatile SingularAttribute<WeatherData, Double> cloudPercentage;

}