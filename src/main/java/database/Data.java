package database;

import controller.Booking;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import model.users.User;

import java.util.List;

public class Data {
    private Data instance;
    public List<User> users;
    public List<Hotel> hotels;
    public List<Flight> flights;
    public List<Booking> bookings;

    private Data() {

    }

    public static Data getInstance() {
        return new Data();
    }
}
