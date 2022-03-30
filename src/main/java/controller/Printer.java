package controller;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import model.Booking;
import model.bookables.flight.Flight;
import model.bookables.flight.Seat;
import model.bookables.hotel.Hotel;
import model.bookables.hotel.Room;
import model.users.info.Passport;
import utils.TimeUtils;

/**
 * Printing means creating a beautifully formatted text file.
 * @author rengotap
 */
public class Printer {
    private static Printer instance;
    private static TimeUtils tUtil;
    private ArrayList<Booking> printQueue;
    private static final String writeDir = "./";
    private static final String writeName = "itinerary";
    private static final DecimalFormat df = new DecimalFormat("0.00");

     // ANSI COLORS
     public static final String ANSI_RESET = "\u001B[0m";
     public static final String ANSI_RED = "\u001B[31m";
     public static final String ANSI_YELLOW = "\u001B[33m";
     public static final String ANSI_CYAN = "\u001B[36m";

     // ASCII
     private static final String H0 = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
     private static final String H1 = "▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰";

    /**
     * Printer Constructor
     * @author rengotap
     */
    private Printer() {
        printQueue = new ArrayList<Booking>();
        tUtil = TimeUtils.getInstance();
    }

    /**
     * Singleton
     * @return instance of Printer
     * @author rengotap
     */
    public static Printer getInstance() {
        if (instance == null) {
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
                w.write(getFileHeader());
                for (int i = 0; i < formatted.length; i++)
                    w.write(formatted[i] + '\n' + '\n' + '\n');
                w.close();
                System.out.println(ANSI_CYAN + "INFO: Successfully wrote to file" + ANSI_RESET);
            } catch (IOException e) {
                System.out.println(ANSI_RED + "ERROR: Unable to write to file" + ANSI_RESET);
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a single formated bookable as a string
     * Used for printing to the console in UI
     * @param b
     * @return formatted bookable
     * @author rengotap
     */
    public String print(Booking b) {
        if(b == null)
            return "";
        return format(b);
    }

    /**
     * Returns the printQueue
     * @return all objects to be printed
     * @author rengotap
     */
    public ArrayList<Booking> getPrintQueue() {
        return printQueue;
    }

    /**
     * Adds a bookable to the print queue
     * @param bookable
     * @author rengotap
     */
    public void enqueue(Booking booking) {
        printQueue.add(booking);
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
     * Verifies that the save location exists
     * If it doesn't, it will attempt to create it
     * @return true if location is found or created
     * @author rengotap
     */
    private boolean verifyLocation() {
        try {
            File writeIn = new File(writeDir + writeName + ".txt");
            if (writeIn.exists()) {
                System.out.println(ANSI_CYAN + "INFO: Save location verified" + ANSI_RESET);
                return true;
            } else {
                writeIn.createNewFile();
                System.out.println(ANSI_CYAN + "INFO: Created file" + ANSI_RESET);
                return true;
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED + "ERROR: Unable to verify printer's save location"
                + ANSI_RESET);
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
        for (int i = 0; i < printQueue.size(); i++)
            formatted[i] = format(printQueue.get(i));
        return formatted;
    }

    /**
     * Formats an object for printing
     * @param bookable
     * @return formatted string
     * @author rengotap
     */
    private String format(Booking b) {
        final String type = b.getBooked().getClass().getSimpleName();
        if(type.equals("Seat")) {
            Seat s = (Seat)b.getBooked();
            Passport p = s.getOwner();
            return p.getPerson().getFirstName() + " " + p.getPerson().getLastName()
            + "'s Flight Information" + '\n' + H1 + " ✈" + '\n'  // Header Line A
            + "Booking ID: " + b.getId() + '\n'
            + format(s);
        }

        if(type.equals("Room")) {
            return "Hotel Reservation Info" + '\n' + H1 + " ⌂" + '\n'  // Header Line A
                + "Booking ID: " + b.getId() + '\n'
                + "Reservation Start: " + tUtil.toString(b.startDate()) + '\n'
                + "Reservation End: " + tUtil.toString(b.endDate()) + '\n' 
                + format((Room)b.getBooked());
        }
        System.out.println(ANSI_YELLOW
            + "WARN: Unknown format passed" + ANSI_RESET);
        return "FORMAT ERROR";
    }

    /**
     * Formats a Seat for printing
     * @param seat
     * @return formatted seat
     * @author rengotap
     */
    private String format(Seat s) {
        Passport p = s.getOwner();
        Flight f = s.getFlight();
        String[] tD = tUtil.splitTime(f.getDepartureTime());
        String[] tA = tUtil.splitTime(f.getArrivalTime());
        return "Passport Number: " + p.getNumber() + '\n'
            + "Price Paid: $" + df.format(s.getPrice()) + '\n'
            + "Service: " + f.getCompany() + '\n'
            + "Duration: " + tUtil.toString(f.getTravelTime()) + '\n'
            + '\n' + "SEAT INFORMATION" +'\n' + H0 + " ✈" + '\n'  // Header Line B
            + "Seat: " + s.getRow() + s.getCol() + '\n'
            + "Class: " + s.getSeatClass() + '\n'
            + '\n' + "DEPARTURE INFORMATION" +'\n' + H0 + " ✈" + '\n'  // Header Line C
            + "Location: " + f.getAirportFrom() + '\n'
            + "Date: " + tD[0] +'\n'
            + "Time: " + tD[1] + "UTC" + '\n'
            + '\n' + "ARRIVAL INFORMATION" +'\n' + H0 + " ✈" + '\n'  // Header Line D
            + "Location: " + f.getAirportTo() + '\n'
            + "Date: " + tA[0] + '\n'
            + "Time: " + tA[1] + "UTC";

    }

    /**
     * Formats a Room for printing
     * @param room
     * @return formatted seat
     * @author rengotap
     */
    private String format(Room r) {
        Hotel h = r.getHotel();
        return "Price Paid: $" + df.format(r.getPrice()) + '\n'
            + "Company Name: " + h.getCompany() + '\n'
            + '\n' + "ROOM INFORMATION" +'\n' + H0 + " ⌂" + '\n'  // Header Line B
            + "Room Number: " + r.getRoomNum() + '\n'
            + "Beds: " + r.getInfo() + '\n'
            + '\n' + "HOTEL INFORMATION" +'\n' + H0 + " ⌂" + '\n'  // Header Line C
            + "Location: " + h.getLocation() + '\n'
            + "Amenities:" 
            + '\n' + toBlock(h.getFeatures(), 3);
    }

    /**
     * Turns a list into a nice block
     * @param input data of the block
     * @param width width of the block
     * @return nice block
     * @author rengotap
     */
    private String toBlock(List<String> input, int width) {
        String ret = "";
        for (int i = 1; i < input.size() + 1; i++) {
            ret = ret + input.get(i - 1) + ", ";
            if (i % width == 0 && i != input.size())
                ret = ret + '\n';
        }
        return ret.replaceAll(", $", "");
    }

    /**
     * Stores the file header/logo
     * @return
     * @author rengotap
     */
    private String getFileHeader() {
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