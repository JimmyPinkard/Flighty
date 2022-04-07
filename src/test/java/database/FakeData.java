package database;

import java.util.ArrayList;
import java.util.List;
import model.Booking;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import model.users.User;

public class FakeData extends Data {
    public List<User> users;
    public List<Flight> flights;
    public List<Hotel> hotels;
    public List<Booking> bookings;

    public FakeData() {
        users = new ArrayList<>();
        flights = new ArrayList<>();
        hotels = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void saveAll() {}
}
