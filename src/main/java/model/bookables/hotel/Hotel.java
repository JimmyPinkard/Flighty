package model.bookables.hotel;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.BookingLayout;
import model.bookables.TravelObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Bookables.Hotel.Hotel object
 * @author rengotap
 */
public class Hotel extends TravelObject {

    protected String location;
    protected int numBeds;

    /**
     * Creates a new hotel object
     */
    public Hotel(List<String> features, String location, int numBeds, BookingLayout layout) {
        super(layout);
        this.features = features;
        this.location = location;
        this.numBeds = numBeds;
    }

    public Hotel(DBObject object) {
        super(object);
        this.features = new ArrayList<>();
        this.location = (String) object.get("location");
        this.bookables = (List<Bookable>) object.get("rooms");
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
                ", travelObject:" + super.toString() +
                "}";
    }
}