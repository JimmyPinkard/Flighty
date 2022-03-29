package utils;

import java.util.ArrayList;
import java.util.Collections;

import controller.Printer;
import model.bookables.Bookable;

/**
 * Class to print the itinerary
 * @author Jack Hyatt
 */
public class ItineraryPrint {
    /**
     * Method to print the itinerary of the users bookings
     * @param bookings: an arraylist of bookables so that each one can be displayed
     */
    public static void printItinerary(ArrayList<Bookable> bookings){
        Printer printer = Printer.getInstance();
        printer.wipe();
        Collections.sort(bookings);
        for(Bookable book : bookings){
            printer.enqueue(book);
        }
        printer.print();
    }
}
