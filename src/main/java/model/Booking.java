package model;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.flight.Seat;
import model.bookables.hotel.Room;
import model.users.User;
import utils.TimeUtils;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Stores a user booking
 */
public class Booking {
    private static final TimeUtils timeUtils = TimeUtils.getInstance();
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

    public Booking(final DBObject object) {
        this.id = (String) object.get("id");
        this.user = new User(object);
        this.booked = (Bookable) object.get("booked");
        this.to = timeUtils.generateDate((String) object.get("to"));
        this.from = timeUtils.generateDate((String) object.get("from"));
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
        return "{" + "\"id\": \"" + id + "\", "
                + "\"booked\": " + booked + ", "
                + "\"user\": " + user + ", "
                + "\"from\": " + from + ", "
                + "\"to\": " + to + "}";
    }
}
