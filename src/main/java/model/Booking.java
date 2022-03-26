package model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Property;
import model.bookables.Bookable;
import model.users.User;
import org.json.JSONObject;

/**
 * Stores a user booking
 */
@Entity("Bookings")
public class Booking {
    @Property("booked")
    private Bookable booked;
    @Property("user")
    private User user;

    public Booking(final User user, final Bookable booked) {
        this.user = user;
        this.booked = booked;
    }

    public Booking(final JSONObject object) {
        //TODO
    }
}