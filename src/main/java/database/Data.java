package database;

import com.mongodb.DBObject;
import model.Booking;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import model.users.User;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static Data instance;
    private final Database db;
    public List<User> users;
    public List<Flight> flights;
    public List<Hotel> hotels;
    public List<Booking> bookings;

    /**
     * Constructor
     */
    private Data() {
        this.db = Database.getInstance();
        this.users = new ArrayList<>();
        this.flights = new ArrayList<>();
        this.hotels = new ArrayList<>();
        this.bookings = new ArrayList<>();
        loadAll();
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

    private void loadFlights() {
        for(DBObject object : db.getAll("Flights")) {
            flights.add(new Flight(object));
        }
    }

    private void loadHotels() {
        for(DBObject object : db.getAll("Hotels")) {
            hotels.add(new Hotel(object));
        }
    }

    private void loadUsers() {
        for(DBObject object : db.getAll("Users")) {
            users.add(new User(object));
        }
    }

    private void loadBookings() {
        for(DBObject object : db.getAll("Bookings")) {
            bookings.add(new Booking(object));
        }
    }

    private void saveUsers() {
        for(User user : users) {
            db.create("Users", user);
        }
    }

    private void saveFlights() {
        for(Flight flight : getFlights()) {
            db.create("Flights", flight);
        }
    }

    private void saveHotels() {
        for(Hotel hotel : getHotels()) {
            db.create("Hotels", hotel);
        }
    }

    private void saveBookings() {
        for(Booking booking : bookings) {
            db.create("Bookings", booking);
        }
    }

    public void loadAll() {
        loadFlights();
        loadHotels();
        loadUsers();
        loadBookings();
    }

    public void saveAll() {
        saveUsers();
        saveFlights();
        saveHotels();
        saveBookings();
    }
}
