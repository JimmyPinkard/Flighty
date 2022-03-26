package model.bookables.hotel;

import com.mongodb.DBObject;
import model.bookables.BookingLayout;
import model.bookables.TravelObject;
import org.json.JSONObject;

import java.util.ArrayList;
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

    public Hotel(DBObject json) {
        super(json);
        this.features = new ArrayList<>();
        this.location = (String) json.get("location");
        this.numBeds = 2;//Integer.parseInt((String) json.get("numBeds"));
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

    @Override
    public String toString() {
        return "{" +
                "features:" + features +
                ", location:'" + location + '\'' +
                ", numBeds:" + numBeds +
                "} " + super.toString();
    }
}