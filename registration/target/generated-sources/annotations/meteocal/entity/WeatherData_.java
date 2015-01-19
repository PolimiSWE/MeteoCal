package meteocal.entity;

import java.sql.Time;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import meteocal.entity.WeatherDataList;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-19T20:45:07")
@StaticMetamodel(WeatherData.class)
public class WeatherData_ { 

    public static volatile SingularAttribute<WeatherData, Date> date;
    public static volatile SingularAttribute<WeatherData, Double> preasure;
    public static volatile SingularAttribute<WeatherData, Time> hour;
    public static volatile SingularAttribute<WeatherData, String> city;
    public static volatile SingularAttribute<WeatherData, Double> temperature;
    public static volatile SingularAttribute<WeatherData, WeatherDataList> ownerList;
    public static volatile SingularAttribute<WeatherData, Long> id;
    public static volatile SingularAttribute<WeatherData, Double> windSpeed;
    public static volatile SingularAttribute<WeatherData, Double> cloudPercentage;

}