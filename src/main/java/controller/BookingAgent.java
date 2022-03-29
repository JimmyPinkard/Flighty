package controller;

import model.Booking;
import model.bookables.Bookable;
import model.bookables.flight.Seat;
import database.Data;
import model.users.User;
import model.users.info.Passport;

/**
 * Handes booking
 */
public class BookingAgent {
    private final Data data;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

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
        if(bookable.book()) {
            final Booking booking = new Booking(user, bookable);
            data.getBookings().add(booking);
            user.addBooking(bookable);
        } else {
            System.out.println(ANSI_YELLOW+"WARN: Bookable already booked."+ANSI_RESET);
        }
    }

    public void bookListing(Seat bookable, User user, Passport owner) {
        if(bookable.book()) {
            bookable.setOwner(owner);
            final Booking booking = new Booking(user, bookable);
            data.getBookings().add(booking);
            user.addBooking(bookable);
        } else {
            System.out.println(ANSI_YELLOW+"WARN: Bookable already booked."+ANSI_RESET);
        }
    }

    public void unbookListing(Booking booking) {
        if(booking.getBooked().unbook()) {
            data.getBookings().remove(booking);
            booking.getUser().removeBooking(booking.getBooked());
        } else {
            System.out.println(ANSI_YELLOW+"WARN: Bookable already unbooked."+ANSI_RESET);
        }
    }
}
