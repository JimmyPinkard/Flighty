package model.bookables.hotel;

import com.mongodb.DBObject;
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

    /**
     * Creates a new hotel object
     */
    public Hotel(List<String> features, String location, int numBeds, BookingLayout layout) {
        super(layout);
        this.features = features;
        this.location = location;

    }

    public Hotel(DBObject object) {
        super(object);
        this.features = new ArrayList<>();
        this.location = (String) object.get("location");
        this.bookables = new ArrayList<>();
        var rooms = (List<DBObject>) object.get("rooms");
        for(DBObject room : rooms) {
            int row = (int)room.get("floor");
            String column = (String) room.get("num");
            this.bookables.add(new Room(row, column, room));
        }
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

    @Override
    public String toString() {
        return "{" +
                "features:" + features +
                ", location:'" + location + '\'' +
                ", travelObject:" + super.toString() +
                "}";
    }
}