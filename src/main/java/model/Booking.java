package model;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.hotel.Room;
import model.bookables.flight.Seat;
import model.users.User;

/**
 * Stores a user booking
 */
public class Booking {
    private Bookable booked;
    private User user;

    public Booking(final User user, final Bookable booked) {
        this.user = user;
        this.booked = booked;
    }

    public Booking(final DBObject object) {
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
            booked = new Room();
        if (type.equals("s"))
            booked = new Seat(4,"A");
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