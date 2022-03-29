package utils;

import java.util.ArrayList;

import model.bookables.Bookable;
import model.users.User;

/**
 * Class to print the itinerary
 * @author Jack Hyatt
 */
public class ItineraryPrint {
    /**
     * Method to print the itinerary of the users bookings
     * @param bookings: an arraylist of bookables so that each one can be displayed
     * @return the string that holds the entire format of the booking
     */
    public static String returnItinerary(ArrayList<Bookable> bookings){
        String out = "";
        for(Bookable book : bookings) 
            out += book.toString() + "\n\n";
        return out;
    }

    /**
     * Method to print the itinerary of the users bookings
     * @param bookings: an arraylist of bookables so that each one can be displayed
     * @param user: the user that holds all the bookings
     * @return the string that holds the entire format of the booking
     */
    public static String returnItinerary(ArrayList<Bookable> bookings, User user){
        String out = user.getFirstName() + " " +user.getLastName() + "\n";
        for(Bookable book : bookings) 
            out += book.toString() + "\n\n";
        return out;
    }
}
