package search;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import database.Data;
import model.bookables.hotel.Hotel;
import search.filters.HotelFilter;
import search.filters.SearchFilter;
import java.lang.reflect.*;

public abstract class SearchHotels implements Search {
    public static List<Hotel> execute(Data data,EnumMap<? extends SearchFilter, String> preferences) {
        List<Hotel> out = new ArrayList<Hotel>();
        System.out.println("Before loop");
        // Loop through all the hotels
        for(Hotel hotel : data.getHotels()) {
            System.out.println("In loop");
            boolean match = true;
            // Loop through the filters in the hotel and see if they match the preferences
            for(String filter: preferences.values()) {
                // Grab all the attributes for the hotel class
                Field[] fields = Hotel.class.getDeclaredFields();
                match = false;
                // Loop throuh all the attributes and compare them to the preferences
                for(Field field : fields) {
                    // Get the attribute value
                    try{
                    String attribute = field.get(hotel).toString();
                    // Compare attribute to preference
                    System.out.println("Attribute: "+attribute + "Filter: "+filter); //TODO: remove this line
                    if(filter.equalsIgnoreCase(attribute) || filter.equalsIgnoreCase("any"))
                        match = true;
                    }catch(IllegalAccessException e){}
                }
                // If a single filter didn't match any of the attributes, break and dont add to the list
                if(!match)
                    break;
            }
            // If every filter matched to an attribute, add the hotel to the list
            if(match)
                out.add(hotel);
        }

        return out;
    }
}