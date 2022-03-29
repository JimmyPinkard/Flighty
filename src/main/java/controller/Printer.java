package controller;

import java.util.ArrayList;

import model.bookables.Bookable;
import model.bookables.flight.Seat;
import model.bookables.hotel.Room;

/**
 * Printing means creating a beautifully formatted text file.
 * @author rengotap
 */
public class Printer {
    private static Printer instance;
    private ArrayList<Bookable> printQueue;
    final protected String saveTo = ""; 

    /**
     * Constructor
     * @author rengotap
     */
    private Printer() {
        printQueue = new ArrayList<Bookable>();
    }

    /**
     * Singleton
     * @return instance of Printer
     * @author rengotap
     */
    public static Printer getInstance() {
        if(instance == null) {
            instance = new Printer();
        }
        return instance;
    }

    /**
     * Prints the printQueue to a file
     * @return save location
     * @author rengotap
     */
    public String print() {
        String[] formatted = formatAll();
        //IO STUFF HERE
        return "Saved itinerary to " + saveTo;
    }

    /**
     * Returns the printQueue
     * @return all objects to be printed
     */
    public ArrayList<Bookable> getPrintQueue() {
        return printQueue;
    }

    /**
     * Adds a bookable to the print queue
     * @param bookable
     * @author rengotap
     */
    public void enqueue(Bookable bookable) {
        printQueue.add(bookable);
    }

    /**
     * Removes a bookable from the print queue at index
     * @param index
     * @author rengotap
     */
    public void dequeue(int index) {
        printQueue.remove(index);
    }

    /**
     * Formats all items in printQueue into a String array
     * @return printable string array
     * @author rengotap
     */
    private String[] formatAll() {
        return new String[printQueue.size()];
    }

    /**
     * Formats an object for printing
     * @param bookable
     * @return formatted string
     * @author rengotap
     */
    private String format(Bookable bookable) {
        return "";
    }

    /**
     * Formats a Seat for printing
     * @param seat
     * @return formatted seat
     * @author rengotap
     */
    private String format(Seat seat) {
        return "";
    }

    /**
     * Formats a Room for printing
     * @param room
     * @return formatted seat
     * @author rengotap
     */
    private String format(Room room) {
        return "";
    }
    
}
