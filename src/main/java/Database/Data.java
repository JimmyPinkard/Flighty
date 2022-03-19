package Database;

import Bookables.Booking;
import Bookables.Flight.Flight;
import Bookables.Hotel.Hotel;
import Users.User;

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
