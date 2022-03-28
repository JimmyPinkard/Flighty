package controller;

import model.Booking;
import model.bookables.Bookable;
import database.Data;
import model.users.User;

/**
 * Handes booking
 */
public class BookingAgent {
    private final Data data;

    public BookingAgent() {
        this.data = Data.getInstance();
    }

    /**
     * Bogus Testing constructor.
     * for testing purposes only
     * @param data
     * @author rengotap
     */
    public BookingAgent(Data data) {
        this.data = data;
    }

    public void bookListing(Bookable bookable, User user) {
        final Booking booking = new Booking(user, bookable);
        data.getBookings().add(booking);
        user.addBooking(bookable);
    }

    public void unbookListing(Booking booking) {
        data.getBookings().remove(booking);
        booking.getUser().removeBooking(booking.getBooked());
    }
}
