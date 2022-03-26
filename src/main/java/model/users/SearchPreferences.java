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
        fpref.put(FlightFilter.FLIGHT, "");
        fpref.put(FlightFilter.AIRPORT_FROM, "");
        fpref.put(FlightFilter.AIRPORT_TO, "");
        fpref.put(FlightFilter.COMPANY, "");
        fpref.put(FlightFilter.PRICE, "");
        fpref.put(FlightFilter.DURATION, "");
        fpref.put(FlightFilter.TIME_DEPART, "");
        fpref.put(FlightFilter.TIME_ARRIVE, "");
        fpref.put(FlightFilter.PETS_ALLOWED, "");
        fpref.put(FlightFilter.FLIGHTS_LAYOVER, "");
        
        hPref = new EnumMap<>(HotelFilter.class);
        hPref.put(HotelFilter.HOTEL, "");
        hPref.put(HotelFilter.COMPANY, "");
        hPref.put(HotelFilter.PRICE, "");
        hPref.put(HotelFilter.TIME_START, "");
        hPref.put(HotelFilter.TIME_END, "");
        hPref.put(HotelFilter.PETS_ALLOWED, "");
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
