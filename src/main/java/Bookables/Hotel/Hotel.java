package Bookables.Hotel;

import Bookables.BookingLayout;
import Bookables.TravelObject;

import java.util.List;
/**
 * Bookables.Hotel.Hotel object
 * @author rengotap
 */
public class Hotel extends TravelObject {

    private List<String> features;
    private String location;
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