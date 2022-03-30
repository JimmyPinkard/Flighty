package model;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.flight.Flight;
import model.bookables.flight.Seat;
import model.bookables.hotel.Hotel;
import model.bookables.hotel.Room;
import model.users.User;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Stores a user booking
 */
public class Booking {
    private String id;
    private Bookable booked;
    private User user;
    private LocalDate from;
    private LocalDate to;

    public Booking(final User user, final Seat booked) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.booked = booked;
    }

    public Booking(final User user, final Room booked, final LocalDate from, final LocalDate to) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.booked = booked;
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    /**
     * Bogus constructor for Booking. for testing purposes only
     * 
     * @author rengotap
     */
    public Booking(String type) {
        if (type.equals("r"))
            booked = new Room(12, "", new Hotel());
        if (type.equals("s"))
            booked = new Seat(4, "A", new Flight());
    }

    public Booking(final DBObject object) {
        // TODO update to account for from and to date
        this.id = (String) object.get("id");
        this.user = new User(object);
        this.booked = (Bookable) object.get("booked");
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

    // TODO update to account for from and to date
    @Override
    public String toString() {
        return "{" + "booked:" + booked + ", user:" + user + '}';
    }
}
