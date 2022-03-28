package model;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.hotel.Room;
import model.users.User;

import java.util.UUID;

/**
 * Stores a user booking
 */
public class Booking {
    private String id;
    private Bookable booked;
    private User user;

    public Booking(final User user, final Bookable booked) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.booked = booked;
    }

    public Booking(final DBObject object) {
        this.id = (String) object.get("id");
        this.user = new User(object);
        this.booked = (Bookable) object.get("booked");
    }

    /**
     * Bogus constructor for Booking.
     * for testing purposes only
     * @author rengotap 
     */
    public Booking() {
        booked = new Room();
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Bookable getBooked() {
        return booked;
    }

    @Override
    public String toString() {
        return "{" +
                "booked:" + booked +
                ", user:" + user +
                '}';
    }
}