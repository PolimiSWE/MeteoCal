/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="WEATHER_DATA")
public class WeatherData implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE , generator="wd_gen")
    @TableGenerator(name="wd_gen", table="ID_GEN",
            pkColumnName="ID_NAME", valueColumnName="ID_VAL",
            pkColumnValue="WD_GEN", initialValue = 220000000)
    @Column(name = "id_weather_data")
    private Long id;
    
    @Column(name = "cloud_percentage")
    private Double cloudPercentage;
    
    @NotNull(message = "May not be empty")
    @Column(name = "date")
    @Temporal(DATE)
    private Date date;
    
    @NotNull(message = "May not be empty")
    @Column(name = "w_hour")
    private Time hour;
   
    @Column(name = "preasure")
    private Double preasure;
    
    @Column(name = "temperature")
    private Double temperature;
    
    @Column(name = "wind_speed")
    private Double windSpeed;

    @NotNull(message = "May not be empty")
    @Column(name = "city")
    private String city;
    
    
    @Column(name = "description")
    private String description;
    
    
    @Column(name = "icon")
    private String icon;
    
    @Column(name = "code")
    private Integer code;
    
    
    //Getters and Setters 
    public Double getCloudPercentage() {
        return cloudPercentage;
    }

    public void setCloudPercentage(Double cloudPercentage) {
        this.cloudPercentage = cloudPercentage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    public Double getPreasure() {
        return preasure;
    }

    public void setPreasure(Double preasure) {
        this.preasure = preasure;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeatherData)) {
            return false;
        }
        WeatherData other = (WeatherData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meteocal.entity.WeatherData[ id=" + id + " ]";
    }
    
}
