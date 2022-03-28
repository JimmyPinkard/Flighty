package model.users;

import java.util.EnumMap;

import com.mongodb.DBObject;
import search.filters.FlightFilter;
import search.filters.HotelFilter;

/**
 * Stores a user's prefrences
 * @author rengotap
 */
public class SearchPreferences implements Cloneable {

    public EnumMap<FlightFilter, String> fpref;
    public EnumMap<HotelFilter, String> hPref;
    public static final String EMPTY = "none";

    /**
     * Generates default SearchPrefrences with all set to 'none'
     */
    public SearchPreferences() {
        fpref = new EnumMap<>(FlightFilter.class);
        fpref.put(FlightFilter.AIRPORT_FROM, EMPTY);
        fpref.put(FlightFilter.AIRPORT_TO, EMPTY);
        fpref.put(FlightFilter.COMPANY, EMPTY);
        fpref.put(FlightFilter.PRICE, EMPTY);
        fpref.put(FlightFilter.DURATION, EMPTY);
        fpref.put(FlightFilter.TIME_DEPART, EMPTY);
        fpref.put(FlightFilter.TIME_ARRIVE, EMPTY);
        fpref.put(FlightFilter.DATE_DEPART, EMPTY);
        fpref.put(FlightFilter.DATE_ARRIVE, EMPTY);
        fpref.put(FlightFilter.PETS_ALLOWED, EMPTY);
        fpref.put(FlightFilter.LAYOVERS, EMPTY);
        
        hPref = new EnumMap<>(HotelFilter.class);
        hPref.put(HotelFilter.HOTEL, EMPTY);
        hPref.put(HotelFilter.COMPANY, EMPTY);
        hPref.put(HotelFilter.PRICE, EMPTY);
        hPref.put(HotelFilter.TIME_START, EMPTY);
        hPref.put(HotelFilter.TIME_END, EMPTY);
        hPref.put(HotelFilter.PETS_ALLOWED, EMPTY);
    }
    /**
     * Generates SearchPrefrences based on what was passed from the user profile
     */
    public SearchPreferences(String data) {  //TODO: import search prefrences from data
        this();
    }

    public SearchPreferences(DBObject object) {
        this();
        for(FlightFilter key : fpref.keySet()) {
            String value = (String) object.get(String.valueOf(key));
            if(value != null) {
                fpref.put(key, value);
            }
        }
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
    public SearchPreferences clone() {
        SearchPreferences newSearchPreferences = new SearchPreferences();

        newSearchPreferences.fpref = this.fpref.clone();
        newSearchPreferences.hPref = this.hPref.clone();

        return newSearchPreferences;
    }

    @Override
    public String toString() {
        return "{" +
                "fpref:" + fpref +
                ", hPref:" + hPref +
                '}';
    }
}
