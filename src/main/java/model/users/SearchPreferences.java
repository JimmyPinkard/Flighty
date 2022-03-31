package model.users;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;
import search.filters.FlightFilter;
import search.filters.HotelFilter;

/**
 * Stores a user's prefrences
 * @author rengotap
 */
public class SearchPreferences implements Cloneable {

    public EnumMap<FlightFilter, String> fPref;
    public EnumMap<HotelFilter, String> hPref;
    public static final String EMPTY = "none";
    public static final String ANY = "any";

    /**
     * Generates default SearchPrefrences with all set to 'none'
     */
    public SearchPreferences() {
        fPref = new EnumMap<>(FlightFilter.class);
        fPref.put(FlightFilter.AIRPORT_FROM, EMPTY);
        fPref.put(FlightFilter.AIRPORT_TO, EMPTY);
        fPref.put(FlightFilter.COMPANY, EMPTY);
        fPref.put(FlightFilter.PRICE, EMPTY);
        fPref.put(FlightFilter.PEOPLE, EMPTY);
        fPref.put(FlightFilter.TIME_DEPART_EARLIEST, EMPTY);
        fPref.put(FlightFilter.TIME_ARRIVE_LATEST, EMPTY);
        fPref.put(FlightFilter.DATE_DEPART_EARLIEST, EMPTY);
        fPref.put(FlightFilter.DATE_ARRIVE_LATEST, EMPTY);
        fPref.put(FlightFilter.LAYOVERS, EMPTY);
        
        hPref = new EnumMap<>(HotelFilter.class);
        hPref.put(HotelFilter.LOCATION, EMPTY);
        hPref.put(HotelFilter.COMPANY, EMPTY);
        hPref.put(HotelFilter.DATE_START, EMPTY);
        hPref.put(HotelFilter.DATE_END, EMPTY);
    }

    public SearchPreferences(DBObject object) {
        this();
        var list = (List<DBObject>)object.get("fPref");
        for(FlightFilter key : fPref.keySet()) {
            for(DBObject obj : list) {
                String value = (String) obj.get(key.name());
                if(value != null) {
                    if(value.equals(EMPTY)) {
                        fPref.remove(key);
                        continue;
                    }
                    fPref.put(key, value);
                }
            }
        }
        list = ((List<DBObject>)object.get("hPref"));
        for(HotelFilter key : hPref.keySet()) {
            for(DBObject obj : list) {
                String value = (String) obj.get(key.name());
                if(value != null) {
                    if(value.equals(EMPTY)) {
                        hPref.remove(key);
                        continue;
                    }
                    hPref.put(key, value);
                }
            }
        }
    }

    /**
     * Returns the User's Flight prefrences
     * @return fPref
     */
    public EnumMap<FlightFilter, String> getFPref(){
        return fPref;
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

        newSearchPreferences.fPref = this.fPref.clone();
        newSearchPreferences.hPref = this.hPref.clone();

        return newSearchPreferences;
    }

    @Override
    public String toString() {
        return ("{" +
                "\"fPref\":" + mapString(fPref, null) +
                ", \"hPref\":" + mapString(hPref) +
                '}').replace('=', ':');
    }

    public String mapString(Map<FlightFilter, String> map, String b) {
        if(map.size() == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[");
        for(Map.Entry entry : map.entrySet()) {
            builder.append("{\"").append(entry.getKey()).append("\": \"").append(entry.getValue().toString()).append("\"}, ");
        }
        return builder.delete(builder.lastIndexOf(","), builder.length()).append("]").toString();
    }

    public String mapString(Map<HotelFilter, String> map) {
        if(map.size() == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[");
        for(Map.Entry entry : map.entrySet()) {
            builder.append("{\"").append(entry.getKey()).append("\": \"").append(entry.getValue().toString()).append("\"}, ");
        }
        return builder.delete(builder.lastIndexOf(","), builder.length()).append("]").toString();
    }
}
