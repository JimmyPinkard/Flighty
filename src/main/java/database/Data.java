package database;

import model.Booking;
import model.bookables.TravelObject;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import model.users.User;
import search.filters.HotelFilter;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static Data instance;
    private final Database db;
    public List<User> users;
    public List<TravelObject> travelObjects;
    public List<Booking> bookings;

    /**
     * Constructor
     */
    private Data() {
        this.db = Database.getInstance();
        this.users = new ArrayList<>();
        this.travelObjects = new ArrayList<>();
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
        List<Flight> flights = new ArrayList<>();
        for(TravelObject object : travelObjects) {
            if(object.getFilters().contains(HotelFilter.HOTEL)) {
                flights.add((Flight) object);
            }
        }
        return flights;
    }

    public List<Hotel> getHotels() {
        List<Hotel> hotels = new ArrayList<>();
        for(TravelObject object : travelObjects) {
            if(object.getFilters().contains(HotelFilter.HOTEL)) {
                hotels.add((Hotel) object);
            }
        }
        return hotels;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    private void loadFlights() {
        for(Object object : db.getAll("Flights")) {
            travelObjects.add((Flight) object);
        }
    }

    private void loadHotels() {
        for(Object object : db.getAll("Hotels")) {
            travelObjects.add((Hotel) object);
        }
    }

    private void loadUsers() {
        for(Object object : db.getAll("Users")) {
            users.add((User) object);
        }
    }

    private void loadBookings() {
        for(Object object : db.getAll("Bookings")) {
            bookings.add((Booking) object);
        }
    }

    private void saveUsers() {
        for(User user : users) {
            db.create(user);
        }
    }

    private void saveFlights() {
        for(Flight flight : getFlights()) {
            db.create(flight);
        }
    }

    private void saveHotels() {
        for(Hotel hotel : getHotels()) {
            db.create(hotel);
        }
    }

    private void saveBookings() {
        for(Booking booking : bookings) {
            db.create(booking);
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
