package utils;

import java.util.ArrayList;

import model.bookables.Bookable;

/**
 * Class to print the itinerary
 * @author Jack Hyatt
 */
public class ItineraryPrint {
    public static String returnItinerary(ArrayList<Bookable> books){
        return books.toString();
    }
}
