package model;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.hotel.Hotel;
import model.bookables.hotel.Room;
import model.bookables.flight.Flight;
import model.bookables.flight.Seat;
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
    public Booking(String type) {
        if (type.equals("r"))
            booked = new Room(12,"",new Hotel());
        if (type.equals("s"))
            booked = new Seat(4,"A",new Flight());
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