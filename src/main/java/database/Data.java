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
    }

    /**
     * Bogus constructor for Data.
     * for testing purposes only
     * @author rengotap
     */
    public Data(List<User> users, List<Flight> flights, List<Hotel> hotels, List<Booking> bookings) {
        this.db = null;
        this.users = users;
        this.flights = flights;
        this.hotels = hotels;
        this.bookings = bookings;
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
            /*
            System.out.println(cursor.curr());
            System.out.println(flights.get(0));
            System.exit(1);
            */
        }
    }

    private void loadHotels() {
        DBCursor cursor = db.getAll("Hotels");
        while(cursor.hasNext()) {
            hotels.add(new Hotel(cursor.next()));
            /*
            System.out.println(cursor.curr());
            System.out.println(hotels.get(0));
            System.exit(1);
            */
        }
    }

    private void loadUsers() {
        DBCursor cursor = db.getAll("Users");
        while(cursor.hasNext()) {
            users.add(new User(cursor.next()));
            /*
            System.out.println(cursor.curr());
            System.out.println(users.get(0));
            System.exit(1);
            */
        }
    }

    private void loadBookings() {
        DBCursor cursor = db.getAll("Bookings");
        while(cursor.hasNext()) {
            bookings.add(new Booking(cursor.next()));
            /*
            System.out.println(cursor.curr());
            System.out.println(users.get(0));
            System.exit(1);
            */
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

    private void loadAll() {
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
