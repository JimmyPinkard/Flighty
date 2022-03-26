package model;

import com.mongodb.DBObject;
import model.bookables.Bookable;
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

    @Override
    public String toString() {
        return "{" +
                "booked:" + booked +
                ", user:" + user +
                '}';
    }
}