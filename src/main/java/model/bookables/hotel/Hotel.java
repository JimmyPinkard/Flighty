package model.bookables.hotel;

import com.mongodb.DBObject;

import model.bookables.Bookable;
import model.bookables.TravelObject;
import java.time.LocalDate;
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
    public Hotel(List<String> features, String location) {
        super();
        this.features = features;
        this.location = location;

    }

    @SuppressWarnings("unchecked")
    public Hotel(DBObject object) {
        super(object);
        this.features = (ArrayList<String>) object.get("features");
        this.location = (String) object.get("location");
        this.bookables = new ArrayList<>();
        var rooms = (List<DBObject>) object.get("rooms");
        for(DBObject room : rooms) {
            this.bookables.add(new Room(room, this));
        }
    }

    /**
     * Bogus constructor for Hotel.
     * for testing purposes only
     * @author rengotap
     */
    public Hotel() {
        super();
        location = "Fiji";
        this.company = "Hilton";
        this.rating = 3.5;
        this.features = new ArrayList<>();
        this.features.add("Feature A");
        this.features.add("Feature B");
        this.features.add("Feature C");
        this.features.add("Feature D");
        this.features.add("Feature E");
        this.features.add("Feature F");
        this.features.add("Feature G");
        this.features.add("Feature H");
        bookables.add(new Room(4,"",this));
        bookables.add(new Room(9,"",this));
        bookables.add(new Room(12,"",this));
    }

    /**
     * Gets the hotel's features
     * @return List of features
     */
    public List<String> getFeatures() {
        return features;
    }

    /**
     * Gets the number of available rooms
     * @return
     */
    public int getNumRooms() {
        return bookables.size();
    }

    public int getNumAvailableRooms(LocalDate from, LocalDate to) {
        int num = 0;

        for (Room room : getOptions())
            if (!room.isBooked(from, to))
                num++;

        return num;
    }

    public List<Room> getOptions() {
        List<Room> rooms = new ArrayList<>();
        for (Bookable bookable : bookables)
            rooms.add((Room) bookable);

        return rooms;
    }

    public List<Room> getAvailableOptions(LocalDate from, LocalDate to) {
        List<Room> rooms = new ArrayList<>();
        for (Room room : getOptions())
            if (!room.isBooked(from, to))
                rooms.add(room);

        return rooms;
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