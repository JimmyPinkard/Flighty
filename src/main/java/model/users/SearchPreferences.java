package model.users;

import java.util.EnumMap;

import search.filters.FlightFilter;
import search.filters.HotelFilter;

/**
 * Stores a user's prefrences
 * @author rengotap
 */
public class SearchPreferences {

    public EnumMap<FlightFilter, String> fpref;
    public EnumMap<HotelFilter, String> hPref;

    /**
     * Generates default SearchPrefrences with all set to 'none'
     */
    public SearchPreferences() {
        fpref = new EnumMap<>(FlightFilter.class);
        fpref.put(FlightFilter.FLIGHT, "none");
        fpref.put(FlightFilter.AIRPORT_FROM, "none");
        fpref.put(FlightFilter.AIRPORT_TO, "none");
        fpref.put(FlightFilter.COMPANY, "none");
        fpref.put(FlightFilter.PRICE, "none");
        fpref.put(FlightFilter.DURATION, "none");
        fpref.put(FlightFilter.TIME_EARLIEST, "none");
        fpref.put(FlightFilter.TIME_LATEST, "none");
        fpref.put(FlightFilter.PETS_ALLOWED, "none");
        fpref.put(FlightFilter.FLIGHTS_LAYOVER, "none");
        
        hPref = new EnumMap<>(HotelFilter.class);
        hPref.put(HotelFilter.HOTEL, "none");
        hPref.put(HotelFilter.COMPANY, "none");
        hPref.put(HotelFilter.PRICE, "none");
        hPref.put(HotelFilter.TIME_DEPART, "none");
        hPref.put(HotelFilter.TIME_END, "none");
        hPref.put(HotelFilter.PETS_ALLOWED, "none");
    }
    /**
     * Generates SearchPrefrences based on what was passed from the user profile
     */
    public SearchPreferences(String data) {  //TODO: import search prefrences from data
        this();
    }

    /**
     * Returns the User's Flight prefrences
     * @return fPref
     */
    public EnumMap<FlightFilter, String> getFPref(){
        return fpref;
    }

    /**
     * Returns the User's Hotel prefrences
     * @return
     */
    public EnumMap<HotelFilter, String> getHPref() {
        return hPref;
    }

    @Override
    public String toString() {
        return "{" +
                "fpref:" + fpref +
                ", hPref:" + hPref +
                '}';
    }
}
