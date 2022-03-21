package database;

import controller.Booking;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import model.users.User;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static Data instance;
    public List<User> users;
    public List<Hotel> hotels;
    public List<Flight> flights;
    public List<Booking> bookings;

    /**
     * Constructor
     */
    private Data() {
        this.users = new ArrayList<>();
        this.hotels = new ArrayList<>();
        this.flights = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    /**
     * Singleton
     * @author Jimmy
     * @return
     */
    public static Data getInstance() {
        if(instance == null) {
            instance = new Data();
        }
        return instance;
    }
}
