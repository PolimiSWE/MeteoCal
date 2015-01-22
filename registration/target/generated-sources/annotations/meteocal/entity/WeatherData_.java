package meteocal.entity;

import java.sql.Time;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-21T23:12:40")
@StaticMetamodel(WeatherData.class)
public class WeatherData_ { 

    public static volatile SingularAttribute<WeatherData, Date> date;
    public static volatile SingularAttribute<WeatherData, Double> preasure;
    public static volatile SingularAttribute<WeatherData, Integer> code;
    public static volatile SingularAttribute<WeatherData, Time> hour;
    public static volatile SingularAttribute<WeatherData, String> city;
    public static volatile SingularAttribute<WeatherData, Double> temperature;
    public static volatile SingularAttribute<WeatherData, String> icon;
    public static volatile SingularAttribute<WeatherData, String> description;
    public static volatile SingularAttribute<WeatherData, Long> id;
    public static volatile SingularAttribute<WeatherData, Double> windSpeed;
    public static volatile SingularAttribute<WeatherData, Double> cloudPercentage;

}