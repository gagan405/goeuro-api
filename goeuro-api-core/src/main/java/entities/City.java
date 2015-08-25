package entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import utils.CityDeserializer;

/**
 * Created by gbm on 24/08/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = CityDeserializer.class)
public class City {

    private Long _id;
    private String name, type;

    private Double latitude;
    private Double longitude;

    public City(Long id, String name, String type, Double latitude, Double longitude){
        this._id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
