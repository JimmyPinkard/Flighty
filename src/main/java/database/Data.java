package database;

import controller.Booking;
import model.bookables.TravelObject;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import model.users.User;
import search.filters.FlightFilter;
import search.filters.HotelFilter;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static Data instance;
    public List<User> users;
    public List<TravelObject> travelObjects;
    public List<Booking> bookings;

    /**
     * Constructor
     */
    private Data() {
        this.users = new ArrayList<>();
        this.travelObjects = new ArrayList<>();
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

    public List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        for(TravelObject object : travelObjects) {
            if(object.getFilters().contains(FlightFilter.FLIGHT)) {
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
}
