package model.bookables.hotel;

import com.mongodb.DBObject;

import model.bookables.Bookable;
import model.bookables.TravelObject;
import utils.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    /*
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
    */
    public Hotel(DBObject object) {
        super(object);
        this.features = (ArrayList<String>) object.get("features");
        this.location = (String) object.get("location");
        this.bookables = new ArrayList<>();
        var rooms = (List<DBObject>) object.get("bookables");
        for(DBObject room : rooms) {
            this.bookables.add(new Room(room, this));
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
        return "{" + "\"id\": \"" + id + "\", "
                + "\"location\": \"" + location + "\", "
                + "\"bookables\": " + bookables + ", "
                + "\"company\": \"" + company + "\", "
                + "\"rating\": " + rating + ", "
                + "\"features\": " + CollectionUtils.stringArray(features.toArray()) + ", "
                + "\"filters\": " + CollectionUtils.stringArray(filters.toArray()) + "}";
    }
}