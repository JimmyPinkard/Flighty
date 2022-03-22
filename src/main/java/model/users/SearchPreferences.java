package model.users;

import java.util.EnumMap;

import search.filters.FlightFilter;
import search.filters.SearchFilter;

/**
 * Stores a user's prefrences
 */
public class SearchPreferences {

    public EnumMap<FlightFilter, String> preferences;

    /**
     * Generates default SearchPrefrences with all set to 'none'
     */
    public SearchPreferences() {
        preferences = new EnumMap<>(FlightFilter.class);
        preferences.put(FlightFilter.FLIGHT, "none");
        preferences.put(FlightFilter.AIRPORT, "none");
        preferences.put(FlightFilter.COMPANY, "none");
        preferences.put(FlightFilter.PRICE, "none");
        preferences.put(FlightFilter.DURATION, "none");
        preferences.put(FlightFilter.TIME_START, "none");
        preferences.put(FlightFilter.TIME_END, "none");
        preferences.put(FlightFilter.PETS_ALLOWED, "none");
        preferences.put(FlightFilter.FLIGHTS_LAYOVER, "none");
        
    }
    /**
     * Generates SearchPrefrences based on what was passed from the user profile    
     */
    public SearchPreferences(String data) {

    }
}
