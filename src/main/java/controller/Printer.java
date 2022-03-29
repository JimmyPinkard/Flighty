package controller;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    final private String writeDir = "./database/userdata/";
    final private String writeName = "itinerary";

     // ANSI COLORS
     public static final String ANSI_RESET = "\u001B[0m";
     public static final String ANSI_RED = "\u001B[31m";
     public static final String ANSI_YELLOW = "\u001B[33m";
     public static final String ANSI_CYAN = "\u001B[36m";

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
     * @author rengotap
     */
    public void print() {
        if (verifyLocation()) {
            String[] formatted = formatAll();
            try {
                FileWriter w = new FileWriter(writeDir+writeName+".txt");
                for(int i = 0; i < formatted.length; i++)
                    w.write(formatted[i]+'\n');
                w.close();
            } catch (IOException e) {
                System.out.println(ANSI_RED + "ERROR: Unable to write to file" + ANSI_RESET);
                e.printStackTrace();
            }
        }
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
     * Clears the printQueue
     * @author rengotap
     */
    public void wipe() {
        printQueue.clear();   
    }

    /**
     * Verifies that the save location exists.
     * If it doesn't, it will attempt to create it.
     * @author rengotap
     */
    private boolean verifyLocation() {
        try {
            File writeIn = new File(writeDir+writeName+".txt");
            if(writeIn.exists()) {
                return true;
            } else {
                writeIn.createNewFile();
                System.out.println(ANSI_CYAN+"INFO: Created file"+ANSI_RESET);
                return true;
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED
            + "ERROR: Unable to verify printer's write location" + ANSI_RESET);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Formats all items in printQueue into a String array
     * @return printable string array
     * @author rengotap
     */
    private String[] formatAll() {
        String[] formatted = new String[printQueue.size()];
        for(int i = 0; i < printQueue.size(); i++)
            formatted[i] = format(printQueue.get(i));
        return formatted;
    }

    /**
     * Formats an object for printing
     * @param bookable
     * @return formatted string
     * @author rengotap
     */
    private String format(Bookable b) {
        final String type = b.getClass().getSimpleName();
        if(type.equals("Seat"))
            return format((Seat)b);
        if(type.equals("Room"))
            return format((Room)b);
        System.out.println(ANSI_YELLOW
            + "WARN: Unknown format passed" + ANSI_RESET);
        return "ERROR";
    }

    /**
     * Formats a Seat for printing
     * @param seat
     * @return formatted seat
     * @author rengotap
     */
    private String format(Seat s) {  // TODO: Seat Formating
        return "Destination: " + s.getFlight().getAirportTo();
    }

    /**
     * Formats a Room for printing
     * @param room
     * @return formatted seat
     * @author rengotap
     */
    private String format(Room r) {  // TODO: Room Formating
        return "Location: " + r.getHotel().getLocation();
    }
}
