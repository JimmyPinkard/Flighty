package search;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import database.Data;
import model.bookables.hotel.Hotel;
import model.bookables.hotel.Room;
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
        
        boolean correctDates = false;
        for(Room room : hotel.getOptions()) {
            correctDates = true;
            for(LocalDate date : room.getBookedDays()) {
                if(date.isAfter(LocalDate.parse(preferences.get(HotelFilter.DATE_START))) 
                && date.isBefore(LocalDate.parse(preferences.get(HotelFilter.DATE_END))))
                    correctDates = false;
            }
        }

        return correctLocation && correctCompany && correctDates;
    }
}