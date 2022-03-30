package controller;

import model.Booking;
import model.bookables.Bookable;
import model.bookables.flight.Seat;
import model.bookables.hotel.Room;
import java.time.LocalDate;
import database.Data;
import model.users.User;
import model.users.info.Passport;

/**
 * Handes booking
 */
public class BookingAgent {
    private final Data data;

    public BookingAgent() {
        this.data = Data.getInstance();
    }

    public Booking bookListing(Room bookable, User user, LocalDate from, LocalDate to) {
        bookable.bookRange(from, to);
        final Booking booking = new Booking(user, bookable, from, to);
        addLinks(bookable, user, booking);
        return booking;
    }

    public Booking bookListing(Seat bookable, User user, Passport owner) {
        bookable.book();
        final Booking booking = new Booking(user, bookable);
        bookable.setOwner(owner);
        addLinks(bookable, user, booking);
        return booking;
    }

    private void addLinks(Bookable bookable, User user, Booking booking) {
        data.getBookings().add(booking);
        user.addBooking(booking);
    }

    public void unbookListing(Booking booking) {
        Bookable booked = booking.getBooked();

        if (booked instanceof Seat) {
            ((Seat) booked).unbook();
        } else {
            ((Room) booked).unbookRange(booking.getFrom(), booking.getTo());
        }

        data.getBookings().remove(booking);
        booking.getUser().removeBooking(booking);
    }
}
