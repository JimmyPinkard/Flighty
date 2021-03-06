package database;

import com.mongodb.DBCursor;
import model.Booking;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import model.users.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseData extends Data {
    private final Database db;
    private List<User> users;
    private List<Flight> flights;
    private List<Hotel> hotels;
    private List<Booking> bookings;

    /**
     * Constructor
     */
    private DatabaseData() {
        this.db = Database.getInstance();
        this.users = new ArrayList<>();
        this.flights = new ArrayList<>();
        this.hotels = new ArrayList<>();
        this.bookings = new ArrayList<>();
        loadAll();
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new DatabaseData();
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
        DBCursor cursor = db.getAll("Flights");
        while (cursor.hasNext()) {
            flights.add(new Flight(cursor.next()));
        }
    }

    private void loadHotels() {
        DBCursor cursor = db.getAll("Hotels");
        while (cursor.hasNext()) {
            hotels.add(new Hotel(cursor.next()));
        }
    }

    private void loadUsers() {
        DBCursor cursor = db.getAll("Users");
        while (cursor.hasNext()) {
            users.add(new User(cursor.next()));
        }
    }

    private void loadBookings() {
        DBCursor cursor = db.getAll("Bookings");
        while (cursor.hasNext()) {
            bookings.add(new Booking(cursor.next()));
        }
    }

    private void saveUsers() {
        for (User user : users) {
            db.update("Users", user.getId(), user);
        }
    }

    private void saveFlights() {
        for (Flight flight : getFlights()) {
            db.update("Flights", flight.getId(), flight);
        }
    }

    private void saveHotels() {
        for (Hotel hotel : getHotels()) {
            db.update("Hotels", hotel.getId(), hotel);
        }
    }

    private void saveBookings() {
        for (Booking booking : bookings) {
            db.update("Bookings", booking.getId(), booking);
        }
    }

    private void loadAll() {
        loadFlights();
        loadHotels();
        loadUsers();
        loadBookings();
    }

    public void saveAll() {
        saveFlights();
        saveHotels();
        saveUsers();
        saveBookings();
    }
}
