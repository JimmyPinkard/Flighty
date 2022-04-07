package database;

import java.util.List;
import model.Booking;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import model.users.User;

public abstract class Data {
    protected static Data instance;

    public static Data getInstance() {
        return instance;
    }

    public static void setInstance(Data newInstance) {
        instance = newInstance;
    }

    public abstract List<Flight> getFlights();

    public abstract List<Hotel> getHotels();

    public abstract List<User> getUsers();

    public abstract List<Booking> getBookings();

    public abstract void saveAll();
}
