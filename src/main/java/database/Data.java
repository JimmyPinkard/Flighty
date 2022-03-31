package database;

import com.mongodb.DBCursor;
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
        System.out.println(flights.get(0));
        System.exit(0);
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
        DBCursor cursor = db.getAll("Flights");
        while(cursor.hasNext()) {
            flights.add(new Flight(cursor.next()));
        }
    }

    private void loadHotels() {
        DBCursor cursor = db.getAll("Hotels");
        while(cursor.hasNext()) {
            hotels.add(new Hotel(cursor.next()));
        }
    }

    private void loadUsers() {
        DBCursor cursor = db.getAll("Users");
        while(cursor.hasNext()) {
            users.add(new User(cursor.next()));
        }
    }

    private void loadBookings() {
        DBCursor cursor = db.getAll("Bookings");
        while(cursor.hasNext()) {
            bookings.add(new Booking(cursor.next()));
        }
    }

    private void saveUsers() {
        for(User user : users) {
            db.update("Users", user.getId(), user);
        }
    }

    private void saveFlights() {
        for(Flight flight : getFlights()) {
            db.update("Flights", flight.getId(), flight);
        }
    }

    private void saveHotels() {
        for(Hotel hotel : getHotels()) {
            db.update("Hotels", hotel.getId(), hotel);
        }
    }

    private void saveBookings() {
        for(Booking booking : bookings) {
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
