package search;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import database.Data;
import model.bookables.hotel.Hotel;
import search.filters.HotelFilter;
import search.filters.SearchFilter;

public abstract class SearchHotels implements Search {
    public static List<Hotel> execute(Data data,EnumMap<? extends SearchFilter, String> preferences) {
        List<Hotel> out = new ArrayList<Hotel>();
        System.out.println("Before loop");
        // Loop through all the hotels
        for(Hotel hotel : data.getHotels()) {
            // Check if the hotel matches the preferences
            if(matchesFilters(hotel, preferences))
                out.add(hotel);
        }
        return out;
    }

    private static boolean matchesFilters(Hotel hotel,EnumMap<? extends SearchFilter, String> preferences) {
        
        boolean correctCompany = hotel.getCompany().equalsIgnoreCase(preferences.get(HotelFilter.COMPANY)) 
        || preferences.get(HotelFilter.COMPANY).equalsIgnoreCase("any");

        boolean correctLocation = hotel.getLocation().equalsIgnoreCase(preferences.get(HotelFilter.LOCATION)) 
        || preferences.get(HotelFilter.LOCATION).equalsIgnoreCase("any");
        
        return correctLocation&&correctCompany;
    }
}