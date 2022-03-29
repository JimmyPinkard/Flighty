package search;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.ListIterator;

import database.Data;
import model.bookables.hotel.Hotel;
import model.bookables.hotel.Room;
import model.users.SearchPreferences;
import search.filters.HotelFilter;
import search.filters.SearchFilter;
import utils.TimeUtils;

public abstract class SearchHotels implements Search {
    public static List<Hotel> execute(SearchPreferences preferences) {
        Data data = Data.getInstance();
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
        
        boolean correctDates = false;
        TimeUtils timeUtil = TimeUtils.getInstance();
        for(Room room : hotel.getOptions()) {
            correctDates = true;
            List<LocalDate> days = room.getBookedDays();
            for(LocalDate date : days) {
                if(date.isAfter(timeUtil.generateDate(preferences.get(HotelFilter.DATE_START))) 
                && date.isBefore(timeUtil.generateDate(preferences.get(HotelFilter.DATE_END))))
                    correctDates = false;
            }
        }

        return correctLocation && correctCompany && correctDates;
    }
}