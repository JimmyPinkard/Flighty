package model.bookables.hotel;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Reference;
import model.bookables.BookingLayout;
import model.bookables.TravelObject;
import org.json.JSONObject;

import java.util.List;
/**
 * Bookables.Hotel.Hotel object
 * @author rengotap
 */
@Entity("Hotels")
public class Hotel extends TravelObject {

    @Property("features")
    private List<String> features;
    @Property("location")
    private String location;
    @Property("numBeds")
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