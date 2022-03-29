package controller;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.bookables.Bookable;
import model.bookables.flight.Flight;
import model.bookables.flight.Seat;
import model.bookables.hotel.Hotel;
import model.bookables.hotel.Room;
import model.users.info.Passport;

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

     // ASCII
     private static final String H0 = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
     private static final String H1 = "━━━━━━━━━━━━━━━━━━▲━━━━━━━━━━━━━━━━━━";
     private static final String H2 = "━━━━━━━━━━━━━━━━━━▼━━━━━━━━━━━━━━━━━━";
     private static final String H3 = "▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰";

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
                w.write(getFHeader());
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
    private String format(Seat s) {  // TODO: Finish filling out Seat Formating
        Passport p = s.getOwner();
        Flight f = s.getFlight();
        return p.getPerson().getFirstName() + " " + p.getPerson().getLastName()
            + "'s Flight Information" + '\n' + H3 + " ✈" + '\n'  // Header Line A
            + "Passport Number: " + p.getNumber() + '\n'
            + "Flight ID: " + f.getId() + '\n'
            + "Service: " + f.getCompany() + " Airlines" + '\n'
            + "Seat: " + s.getRow() + s.getCol() + '\n'
            + "Class: " + s.getSeatClass() + '\n'
            + '\n' + "DEPARTURE INFORMATION" +'\n' + H0 + " ✈" + '\n'  // Header Line B
            + "Date:" + '\n'
            + "Time: " + f.getDepartureTime() + '\n'
            + "Location: " + f.getAirportFrom() + '\n'
            + '\n' + "ARRIVAL INFORMATION" +'\n' + H0 + " ✈" + '\n'  // Header Line C
            + "Date:" + '\n'
            + "Time: " + f.getArrivalTime() + '\n'
            + "Location: " + f.getAirportTo() + '\n';

    }

    /**
     * Formats a Room for printing
     * @param room
     * @return formatted seat
     * @author rengotap
     */
    private String format(Room r) {  // TODO: Finish filling out Room Formating
        Hotel h = r.getHotel();
        return "Hotel Reservation Info" + '\n' + H3 + " ⌂" + '\n'  // Header Line A
            + "Booking ID: " + h.getId() + '\n'
            + "Company Name: " + h.getCompany() + '\n'
            + "Room Number: " + r.getRoomNum() + '\n'
            + "Beds: " + r.getInfo() + '\n'
            + "Location: " + h.getLocation() + '\n'
            + "Reservation Start: " + '\n'
            + "Reservation End: " + '\n'
            + "Features: " + '\n';
    }

    /**
     * Stores the file header/logo
     * @return
     * @author rengotap
     */
    private String getFHeader() {
        return '\n' + "88888888888  88  88               88" + '\n' + "88           88  "
                + "''" + "               88            ,d" + '\n'
                + "88           88                   88            88" + '\n'
                + "88aaaaa      88  88   ,adPPYb,d8  88,dPPYba,  MM88MMM  8b       d8" + '\n'
                + "88'''''      88  88  a8'    `Y88  88P'    '8a   88     `8b     d8'" + '\n'
                + "88           88  88  8b       88  88       88   88      `8b   d8'" + '\n'
                + "88           88  88  '8a,   ,d88  88       88   88,      `8b,d8'" + '\n'
                + "88           88  88   `'YbbdP'Y8  88       88   'Y888      Y88'" + '\n'
                + "                      aa,    ,88                           d8'" + '\n'
                + "                       'Y8bbdP'                           d8'"
                + '\n' + "                  Flight & Hotel Booking Program" + '\n' + '\n';
    }
}