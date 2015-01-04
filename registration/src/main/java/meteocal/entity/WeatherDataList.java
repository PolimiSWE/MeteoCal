/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author Milos
 */
@Entity
@Table(name="WEATHER_DATA_LISTS")
public class WeatherDataList implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE , generator="wdl_gen")
    @TableGenerator(name="wdl_gen", table="ID_GEN",
            pkColumnName="ID_NAME", valueColumnName="ID_VAL",
            pkColumnValue="WDL_GEN", initialValue = 230000000)
    @Column(name = "id_weather_data_list")
    private Long id;
    
    
    //Relationship Entities
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerList", orphanRemoval = true, targetEntity = meteocal.entity.WeatherData.class)
    private Collection<WeatherData> weatherDataList;
    
    @OneToOne(optional = false, targetEntity = meteocal.entity.Event.class)
    @JoinColumn(name="event", referencedColumnName = "id_event")
    private Event event;

    
    //Getters and Setters 
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Collection<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(Collection<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof WeatherDataList)) {
            return false;
        }
        WeatherDataList other = (WeatherDataList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "meteocal.entity.WeatherDataList[ id=" + id + " ]";
    }
    
}
