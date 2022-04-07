package search;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import database.Data;
import database.DatabaseData;
import model.bookables.hotel.Hotel;
import model.users.SearchPreferences;
import search.filters.HotelFilter;
import search.filters.SearchFilter;
import utils.TimeUtils;

public class SearchHotels implements Search {
    public static List<Hotel> execute(SearchPreferences preferences) {
        Data data = DatabaseData.getInstance();
        List<Hotel> out = new ArrayList<Hotel>();
        // Loop through all the hotels
        for(Hotel hotel : data.getHotels()) {
            // Check if the hotel matches the preferences
            if(matchesFilters(hotel, preferences.hPref))
                out.add(hotel);
        }
        return out;
    }

    private static boolean matchesFilters(Hotel hotel,EnumMap<? extends SearchFilter, String> preferences) {
        
        boolean correctCompany = hotel.getCompany().equalsIgnoreCase(preferences.get(HotelFilter.COMPANY)) 
        || preferences.get(HotelFilter.COMPANY).equalsIgnoreCase("any") || preferences.get(HotelFilter.COMPANY).equalsIgnoreCase("none");

        boolean correctLocation = hotel.getLocation().equalsIgnoreCase(preferences.get(HotelFilter.LOCATION)) 
        || preferences.get(HotelFilter.LOCATION).equalsIgnoreCase("any") || preferences.get(HotelFilter.LOCATION).equalsIgnoreCase("none");

        TimeUtils timeUtil = TimeUtils.getInstance();
        boolean correctDates = hotel
                .getAvailableOptions(timeUtil.generateDate(preferences.get(HotelFilter.DATE_START)),
                        timeUtil.generateDate(preferences.get(HotelFilter.DATE_END)))
                .size() != 0;

        return correctLocation && correctCompany && correctDates;
    }
}