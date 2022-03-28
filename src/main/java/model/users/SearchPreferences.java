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
    public static final String ANY = "any";

    /**
     * Generates default SearchPrefrences with all set to 'none'
     */
    public SearchPreferences() {
        fpref = new EnumMap<>(FlightFilter.class);
        fpref.put(FlightFilter.AIRPORT_FROM, EMPTY);
        fpref.put(FlightFilter.AIRPORT_TO, EMPTY);
        fpref.put(FlightFilter.COMPANY, EMPTY);
        fpref.put(FlightFilter.PRICE, EMPTY);
        fpref.put(FlightFilter.PEOPLE, EMPTY);
        fpref.put(FlightFilter.TIME_DEPART_EARLIEST, EMPTY);
        fpref.put(FlightFilter.TIME_ARRIVE_LATEST, EMPTY);
        fpref.put(FlightFilter.DATE_DEPART_EARLIEST, EMPTY);
        fpref.put(FlightFilter.DATE_ARRIVE_LATEST, EMPTY);
        fpref.put(FlightFilter.PETS_ALLOWED, EMPTY);
        fpref.put(FlightFilter.LAYOVERS, EMPTY);
        
        hPref = new EnumMap<>(HotelFilter.class);
        hPref.put(HotelFilter.COMPANY, EMPTY);
        hPref.put(HotelFilter.DATE_START, EMPTY);
        hPref.put(HotelFilter.DATE_END, EMPTY);
        hPref.put(HotelFilter.PETS_ALLOWED, EMPTY);
    }

    public SearchPreferences(DBObject object) {
        this();
        for(FlightFilter key : fpref.keySet()) {
            String value = (String) object.get(String.valueOf(key));
            if(value != null) {
                if(value.equals(EMPTY)) {
                    fpref.remove(key);
                    continue;
                }
                fpref.put(key, value);
            }
        }
        for(HotelFilter key : hPref.keySet()) {
            String value = (String) object.get(String.valueOf(key));
            if(value != null) {
                if(value.equals(EMPTY)) {
                    hPref.remove(key);
                    continue;
                }
                hPref.put(key, value);
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
        return ("{" +
                "fpref:" + fpref +
                ", hPref:" + hPref +
                '}').replace('=', ':');
    }
}
