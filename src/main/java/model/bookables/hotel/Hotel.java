package model.bookables.hotel;
import javax.persistence.*;
import model.bookables.BookingLayout;
import model.bookables.TravelObject;
import org.json.JSONObject;

import java.util.List;
/**
 * Bookables.Hotel.Hotel object
 * @author rengotap
 */
@Entity
@Table
public class Hotel extends TravelObject {

    @Column(name = "features")
    private List<String> features;
    @Column(name = "location")
    private String location;
    @Column(name = "numBeds")
    private int numBeds;

    /**
     * Creates a new hotel object
     */
    public Hotel(List<String> features, String location, int numBeds, BookingLayout layout) {
        super(layout);
        this.features = features;
        this.location = location;
        this.numBeds = numBeds;
    }

    public Hotel(final JSONObject json) {
        super(json);
        /*
        this.features = json.get("features");
        this.location = json.get("location");
        this.numBeds = json.get("numBeds");
        */
    }

    public Hotel() {
        super();
    }

    /**
     * Gets the hotel's features
     * @return List of features
     */
    public List<String> getFeatures() {
        return features;
    }

    /**
     * Gets the hotel's location
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the number of available beds
     * @return numBeds
     */
    public int getBeds() {
        return numBeds;
    }

}