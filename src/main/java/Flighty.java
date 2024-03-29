import database.Data;
import database.DatabaseData;
import model.bookables.Bookable;
import model.bookables.flight.Flight;
import model.bookables.flight.FlightTrip;
import model.bookables.flight.Seat;
import model.bookables.hotel.Room;
import model.users.SearchPreferences;
import model.users.User;
import model.users.info.Passport;
import model.users.info.Person;
import controller.BookingAgent;
import controller.Printer;
import controller.UserManager;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import search.SearchFlightTrips;
import search.SearchHotels;
import search.filters.FlightFilter;
import search.filters.HotelFilter;
import utils.TimeUtils;
import model.bookables.hotel.Hotel;
import model.Booking;

/**
 * UI class
 * 
 * @author rengotap, jbytes1027
 */
public class Flighty {
    private Scanner input;
    private UserManager userManager;
    private BookingAgent bookingAgent;
    private Data data;
    private Printer printer;
    private TimeUtils timeUtils;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    // ANSI COLORS
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BG = "\u001B[47m";

    // ANSI FUNCTIONAL
    public static final String ANSI_B = "\u001b[1m"; // Bold
    public static final String ANSI_U = "\u001b[4m"; // Italic


    /**
     * Main method for Flighty app
     * 
     * @param args
     * @author jbytes1027
     */
    public static void main(final String[] args) {
        Flighty app = new Flighty();
        app.start();
    }

    /**
     * Creates a new UI / Flighty instance
     * 
     * @author jbytes1027
     */
    public Flighty() {
        data = DatabaseData.getInstance();
        input = new Scanner(System.in);
        userManager = new UserManager(data);
        bookingAgent = new BookingAgent();
        printer = Printer.getInstance();
        timeUtils = TimeUtils.getInstance();
        checkData();
        //headStart();
    }

    /**
     * Loads all data from database
     * 
     * @author JimmyPinkard
     */
    public void start() {
        menuMain();
    }

    /**
     * SUPER TIME SAVER! Logs you into a test account with preferences filled to make life a little
     * easier
     * 
     * @author rengotap
     */
    private void headStart() {
        User newUser = new User();
        // SUPER TIME SAVER
        newUser.getFPref().replaceAll((key, old) -> "any");
        newUser.getFPref().put(FlightFilter.AIRPORT_FROM, "AUS");
        newUser.getHPref().replaceAll((key, old) -> "any");
        newUser.addTraveler(
                new Passport(new Person("Hugh", "Mann"), timeUtils.generateDate("1/1/1"),
                        timeUtils.generateDate("1/1/1"), "8346903", "Male"));
        newUser.addTraveler(
                new Passport(new Person("Abdullaahi", "Thorburn"), timeUtils.generateDate("04/20/69"),
                        timeUtils.generateDate("11/1/25"), "694201337", "Male"));
        newUser.addTraveler(
                new Passport(new Person("Jarmo", "Dijkstra"), timeUtils.generateDate("07/15/92"),
                        timeUtils.generateDate("7/1/28"), "849264", "Female"));
        newUser.addTraveler(
                new Passport(new Person("Yusif", "Ó Maolagáin"), timeUtils.generateDate("04/12/40"),
                        timeUtils.generateDate("6/1/29"), "7777777", "Other"));
        userManager.registerUser(newUser);
        userManager.logoutCurrent();
        userManager.login("temp", "p");
    }

    /**
     * Checks to make sure data is all there, terminates program if it isnt
     * 
     * @author rengotap
     */
    public void checkData() {
        if (data.getFlights().isEmpty() || data.getHotels().isEmpty()) {
            println(ANSI_RED + "FATAL: Input data is incomplete!" + ANSI_RESET);
            if (data.getFlights().isEmpty())
                println(ANSI_YELLOW + "DEBUG: Flight data is empty" + ANSI_RESET);
            if (data.getHotels().isEmpty())
                println(ANSI_YELLOW + "DEBUG: Hotel data is empty" + ANSI_RESET);
            exit();
        } else {
            println(ANSI_GREEN + "SUCCESS: Input data loaded successfully" + ANSI_RESET);
        }
    }

    /**
     * Clears the screen using ANSI
     * 
     * @author rengotap
     */
    private void clr() {
        print("\033[H\033[2J");
    }


    /**
     * Keeps the screen until the user hits enter
     * 
     * @author rengotap
     */
    private void awaitEnter() {
        println('\n' + ANSI_WHITE_BG + ANSI_BLACK + "   PRESS ENTER TO CONTINUE   " + ANSI_RESET);
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ITS A LITTLE PLANE AND IT FLIES best thing i've ever made
     * 
     * @rengotap
     */
    private void pBar() {
        try {
            for (int i = 0; i < 100; i++) {
                TimeUnit.MILLISECONDS.sleep(70);
                int width = (i + 1);
                String bar = "🏠" + new String(new char[width]).replace("\0", "═") + "✈"
                        + new String(new char[36 - (width + 1)]).replace("\0", " ") + "🏝️  ";
                System.out.print("\33[2K\r" + bar);
                System.out.flush();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Adds spaces around a string
     * @param s string
     * @param w width
     * @return
     */
    private String pad(String str, int w) {
        int wStart = str.length() + (w - str.length()) / 2;
        str = String.format("%" + wStart + "s", str); // pad left
        return String.format("%" + (-w) + "s", str); // pad right
    }

    /**
     * Prompts the user for input
     * 
     * @param prompt query
     * @return user's string input
     * @author jbytes1027
     */
    private String promptString(String prompt) {
        print(prompt + "\n> ");
        String response = input.nextLine();

        return response;
    }

    /**
     * Prompts the user for input
     * 
     * @param prompt query
     * @return user's date input
     * @author jbytes1027
     */
    private LocalDate promptDate(String prompt) {
        println(prompt + " (MM/DD/YY)");

        String response;

        while (true) {
            print("> ");
            response = input.nextLine();
            try {
                LocalDate date = TimeUtils.getInstance().generateDate(response);
                return date;
            } catch (DateTimeParseException e) {
                println("Invalid date");
                continue;
            }
        }
    }

    /**
     * Converts a date into a string
     * 
     * @param date input date
     * @return converted string
     * @author jbytes1027
     */
    private String toString(LocalDate date) {
        return TimeUtils.getInstance().toString(date);
    }

    /**
     * Turns features into a nice block
     * 
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
     * Displays a bookable as a single line TYPE, COST, DESTINATION/LOCATION, COMPANY, ROOM NUM/SEAT
     * NUM
     * 
     * @return
     * @author rengotap
     */
    private String toString(Bookable b) {
        final String type = b.getClass().getSimpleName();
        String line = "Type: " + ANSI_CYAN + type + ANSI_RESET + " | Price: " + ANSI_CYAN + "$"
                + pad(String.valueOf(df.format(b.getPrice())),6) + ANSI_RESET;
        if (type.equals("Seat")) {
            Seat s = (Seat) b;
            line = line + " | Destination: " + ANSI_CYAN + pad(s.getFlight().getAirportTo(),7) + ANSI_RESET
                    + " | Airline: " + ANSI_CYAN + pad(s.getTravelObject().getCompany(),17) + ANSI_RESET
                    + " | Seat Number: " + ANSI_CYAN + s.getRow() + s.getCol() + ANSI_RESET;
        } else if (type.equals("Room")) {
            Room r = (Room) b;
            line = line + " | Location: " + ANSI_CYAN + pad(r.getHotel().getLocation(),10) + ANSI_RESET
                    + " | Company: " + ANSI_CYAN + pad(r.getHotel().getCompany(),17) + ANSI_RESET
                    + " | Room Number: " + ANSI_CYAN + r.getRoomNum() + ANSI_RESET;
        }
        return line;
    }

    /**
     * Returns a simply formated flight with only the most relevant information PRICE, TRIP, SEATS
     * AVAILABLE, COMPANY, RATING
     * 
     * @param flight
     * @return simple flight string
     * @author rengotap
     */
    private String displayFlightSimple(Flight flight) {
        return "Lowest Price: " + ANSI_CYAN + "$" + pad(df.format(flight.getMinCost()),6) + ANSI_RESET
                + " | " + ANSI_CYAN + flight.getAirportFrom() + ANSI_RESET + " ➡  " + ANSI_CYAN
                + flight.getAirportTo() + ANSI_RESET + " | Travel Time: " + ANSI_CYAN
                + pad(timeUtils.toString(flight.getTravelTime()),5) + ANSI_RESET + " | Seats Available: "
                + ANSI_CYAN + pad(Integer.toString(flight.getNumAvailableSeats()),2) + ANSI_RESET + " | Company: "
                + ANSI_CYAN + pad(flight.getCompany(),17) + ANSI_RESET + " | Rating: "
                + toStars(flight.getRating());
    }


    private String displayFlightTripSimple(FlightTrip trip) {
        return "Lowest Price: " + ANSI_CYAN + "$" + pad(df.format(trip.getMinCost()),6) + ANSI_RESET
                + " | Departure: " + ANSI_CYAN
                + TimeUtils.getInstance().toString(trip.getDepartureTime()) + ANSI_RESET
                + " | Arrival: " + ANSI_CYAN
                + TimeUtils.getInstance().toString(trip.getArrivalTime()) + ANSI_RESET
                + " | Transfers: " + ANSI_CYAN + trip.getAmountTransfers() + ANSI_RESET;
    }

    private String displayFlightTripFull(FlightTrip trip) {
        String out = "";
        for (Flight flight : trip.getFlights()) {
            out += displayFlightFull(flight) + '\n';
        }
        return out;
    }

    /**
     * Returns a nicely formated flight with all relevant information
     * 
     * @param flight
     * @return flight multi line form
     * @author rengotap
     */
    private String displayFlightFull(Flight flight) {
        String[] tD = timeUtils.splitTime(flight.getDepartureTime());
        String[] tA = timeUtils.splitTime(flight.getArrivalTime());
        String format = '\n' + ANSI_WHITE_BG + ANSI_BLACK + " " + flight.getCompany().toUpperCase()
                + " " + flight.getAirportFrom() + " ➡  " + flight.getAirportTo() + " " + ANSI_RESET
                + '\n' + "Price: " + ANSI_CYAN + "$" + df.format(flight.getMinCost()) + ANSI_RESET
                + '\n' + "Rating: " + toStars(flight.getRating()) + ANSI_CYAN + " ("
                + flight.getRating() + ")" + ANSI_RESET + '\n' + "Travel Time: " + ANSI_CYAN
                + timeUtils.toString(flight.getTravelTime()) + ANSI_RESET + '\n'
                + "Departure Date: " + ANSI_CYAN + tD[0] + ANSI_RESET + '\n' + "Departure Time: "
                + ANSI_CYAN + tD[1] + " UTC" + ANSI_RESET + '\n' + "Arrival Date: " + ANSI_CYAN
                + tA[0] + ANSI_RESET + '\n' + "Arrival Time: " + ANSI_CYAN + tA[1] + " UTC"
                + ANSI_RESET + '\n' + "Seats Available: " + ANSI_CYAN
                + flight.getNumAvailableSeats() + ANSI_RESET + '\n';

        ArrayList<String> openSeats = new ArrayList<String>();
        for (int i = 0; i < flight.getNumAvailableSeats(); i++)
            openSeats.add(flight.getAvailableOptions().get(i).getRow()
                    + flight.getAvailableOptions().get(i).getCol());

        format = format + ANSI_GREEN + toBlock(openSeats, 5) + ANSI_RESET;
        return format;
    }

    /**
     * Makes a 2d map of seats with available seats highglhighted
     * 
     * @param flight
     * @return
     */
    private String flightMap(Flight flight) {
        final String pEconomy = df.format(flight.getMinCost());
        final String pBusiness = df.format(flight.getBusinessPrice());
        final String pFirst = df.format(flight.getMaxCost());

        String ret = '\n' + ANSI_BLACK + ANSI_WHITE_BG + " CLASS: ECONOMY      PRICE: $" + pEconomy
                + " " + ANSI_RESET + '\n';
        String[][] map = flightMapMaker(flight);
        String[] rows = flightMapFlattener(map, flight);
        for (int i = 0; i < rows.length; i++) {
            ret = ret + rows[i] + '\n';
            if (i == 8)
                ret = ret + ANSI_BLACK + ANSI_WHITE_BG + " CLASS: BUSINESS     PRICE: $" + pBusiness
                        + " " + ANSI_RESET + '\n';
            else if (i == 17)
                ret = ret + ANSI_BLACK + ANSI_WHITE_BG + " CLASS: FIRST CLASS  PRICE: $" + pFirst
                        + " " + ANSI_RESET + '\n';
        }
        return ret;
    }

    /**
     * Makes a 2d flightmap array and highlights seats
     * 
     * @param flight
     * @param seats
     * @return
     * @author rengotap
     */
    private String[][] flightMapMaker(Flight flight) {
        String[][] ret = new String[6][27]; // ABC DEF
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 27; j++) {
                ret[i][j] = j + numToLet(i);
            }
        }
        return ret;
    }

    /**
     * Converts the 2D array into a formatted 1D array for printing
     * 
     * @param map
     * @return
     * @author rengotap
     */
    private String[] flightMapFlattener(String[][] map, Flight flight) {
        String[] ret = new String[map[0].length];

        ArrayList<String> openSeats = new ArrayList<String>();
        for (int i = 0; i < flight.getNumAvailableSeats(); i++)
            openSeats.add(flight.getAvailableOptions().get(i).getRow()
                    + flight.getAvailableOptions().get(i).getCol());
        for (int j = 0; j < map[0].length; j++) {
            String temp = "";
            for (int i = 0; i < map.length; i++) {
                String seat = map[i][j];
                if (openSeats.contains(map[i][j]))
                    seat = ANSI_GREEN + map[i][j] + ANSI_RESET;

                if (map[i][j].length() == 2)
                    temp = temp + seat + "  ";
                else
                    temp = temp + seat + " ";
                if (i == map.length / 2 - 1)
                    temp = temp + "      "; // Aisle
            }
            ret[j] = temp;
        }
        return ret;
    }

    /**
     * Turns a number into a letter (0-5) -> (A-F)
     * 
     * @param number
     * @author rengotap
     */
    private String numToLet(int i) {
        if (i == 0)
            return "A";
        else if (i == 1)
            return "B";
        else if (i == 2)
            return "C";
        else if (i == 3)
            return "D";
        else if (i == 4)
            return "E";
        else if (i == 5)
            return "F";
        else
            return "";
    }

    /**
     * Returns a simply formated hotel with only the most relevant information
     * 
     * @param hotel
     * @return Hotel single line: PRICE, BEDS, COMPANY, STARS
     * @author rengotap
     */
    private String displayHotelSimple(Hotel hotel, LocalDate from, LocalDate to) {
        return "Price: " + ANSI_CYAN + "$" + pad(df.format(hotel.getMinCost()),6) + ANSI_RESET
                + " | Rooms Available: " + ANSI_CYAN + pad(Integer.toString(hotel.getNumAvailableRooms(from, to)),2)
                + ANSI_RESET + " | Company: " + ANSI_CYAN + pad(hotel.getCompany(),16) + ANSI_RESET
                + " | Rating: " + toStars(hotel.getRating());
    }

    /**
     * Returns a nicely formated hotel with all relevant information
     * 
     * @param hotel
     * @return Hotel string location, company, nl, price, stars, nl, beds
     * @author rengotap
     */
    private String displayHotelFull(Hotel hotel, LocalDate from, LocalDate to) {
        return '\n' + ANSI_WHITE_BG + ANSI_BLACK + " " + hotel.getCompany().toUpperCase() + " AT "
                + hotel.getLocation().toUpperCase() + " " + ANSI_RESET + '\n' + "Price: "
                + ANSI_CYAN + "$" + df.format(hotel.getMinCost()) + ANSI_RESET + '\n' + "Rating: "
                + toStars(hotel.getRating()) + ANSI_CYAN + " (" + hotel.getRating() + ")"
                + ANSI_RESET + '\n' + "Available Rooms: " + ANSI_CYAN
                + hotel.getNumAvailableRooms(from, to) + ANSI_RESET + '\n' + "Amenities:" + '\n'
                + ANSI_CYAN + toBlock(hotel.getFeatures(), 3) + ANSI_RESET + '\n';
    }

    /**
     * Displays a seat
     * 
     * @param seat
     * @return
     * @author rengotap
     */
    private String displaySeat(Seat seat) {
        return "Seat: " + ANSI_CYAN + seat.getRow() + seat.getCol() + ANSI_RESET + " | Price: "
                + ANSI_CYAN + "$" + df.format(seat.getPrice()) + ANSI_RESET + " | Class: "
                + ANSI_CYAN + seat.getSeatClass() + ANSI_RESET;
    }

    /**
     * Displays a room
     * 
     * @param room
     * @return
     * @author rengotap
     */
    private String displayRoom(Room room) {
        return "Room Number: " + ANSI_CYAN + room.getRoomNum() + ANSI_RESET + " | Price: "
                + ANSI_CYAN + "$" + df.format(room.getPrice()) + ANSI_RESET + " | Beds: "
                + ANSI_CYAN + room.getInfo() + ANSI_RESET;
    }

    /**
     * Turns a double rating into a string of stars
     * 
     * @param rating
     * @return stars
     * @author rengotap
     */
    private String toStars(Double rating) {
        String stars = "";
        for (int i = 0; i < Math.round(rating); i++)
            stars = stars + "⭐";
        return stars;
    }

    /**
     * Prompts the user for a number
     * 
     * @param from entered number must be greater than or equal to
     * @param to entered number must be less than or equal to
     * @return number user entered
     * @author jbytes1027
     */
    private int promptNumber(String prompt, int from, int to) {
        println(prompt);

        while (true) {
            print("> ");

            int response;

            try {
                response = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                println("Not a number");
                continue;
            }

            if (response >= from && response <= to) {
                return response;
            }

            println("Invalid option");
        }
    }

    /**
     * Prompts the user to answer yes (y) or no (n)
     * 
     * @param prompt query
     * @return True = yes, false = no
     * @author rengotap
     */
    private boolean promptYN(String prompt) {
        println(prompt + " (y/n)");
        String response;
        while (true) {
            print("> ");
            response = input.nextLine();
            if (response.equals("y"))
                return true;
            else if (response.equals("n"))
                return false;
            else
                println("Invalid option");
        }
    }

    /**
     * Prints a string
     * 
     * @param string string to print
     * @author jbytes1027
     */
    private void print(String string) {
        System.out.print(string);
    }

    /**
     * Prints a line
     * 
     * @param string line to print
     * @author jbytes1027
     */
    private void println(String string) {
        System.out.println(string);
    }

    /**
     * Prints a cool logo
     * 
     * @author rengotap
     */
    private void printHeader() {
        println(ANSI_CYAN + "88888888888  88  88               88" + '\n' + "88           88  "
                + "''" + "               88            ,d" + '\n'
                + "88           88                   88            88" + '\n'
                + "88aaaaa      88  88   ,adPPYb,d8  88,dPPYba,  MM88MMM  8b       d8" + '\n'
                + "88'''''      88  88  a8'    `Y88  88P'    '8a   88     `8b     d8'" + '\n'
                + "88           88  88  8b       88  88       88   88      `8b   d8'" + '\n'
                + "88           88  88  '8a,   ,d88  88       88   88,      `8b,d8'" + '\n'
                + "88           88  88   `'YbbdP'Y8  88       88   'Y888      Y88'" + '\n'
                + "                      aa,    ,88                           d8'" + '\n'
                + "                       'Y8bbdP'                           d8'" + ANSI_RESET
                + '\n' + "                 "
                +ANSI_B+" Flight & Hotel Booking Program " +ANSI_RESET);
    }

    /**
     * Creates a numbered menu and promps the user to choose an option
     * 
     * @param options options for the user to choose from
     * @return chosen option from options
     * @author jbytes1027
     */
    private String menuNumbered(String prompt, List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            println(String.format("%d. %s", i + 1, options.get(i)));
        }

        println("");

        int response = promptNumber(prompt, 1, options.size());
        return options.get(response - 1);
    }

    /**
     * Special version of menuNumbered that returns an array with both the string and its position
     * in options.
     * 
     * 
     * Meant for menus of unknown length
     * 
     * @param prompt
     * @param options
     * @return Array with String at 0 and int at 1
     * @author rengotap
     */
    private String[] menuLong(String prompt, List<String> options) {
        String[] ret = new String[2];
        for (int i = 0; i < options.size(); i++) {
            String format = "%d. %s";
            if (i < 9)
                format = "%d.  %s";
            println(String.format(format, i + 1, options.get(i)));
        }

        println("");

        int response = promptNumber(prompt, 1, options.size());
        ret[0] = options.get(response - 1);
        ret[1] = Integer.toString(response - 1); // slight spaghetti
        return ret;
    }

    /**
     * Main menu
     * 
     * @author jbytes1027
     */
    private void menuMain() {
        while (true) {
            final String OPTION_FLIGHT = "Find a Flight";
            final String OPTION_HOTEL = "Find a Hotel";
            final String OPTION_BOOKINGS = "Manage Bookings";
            final String OPTION_MANAGE_USER = "Manage User Profile";
            final String OPTION_LOGIN = "Login";
            final String OPTION_CREATE_USER = "Create User Profile";
            final String OPTION_LOGOUT = "Logout";
            final String OPTION_EXIT = "Exit";

            List<String> options = new ArrayList<String>();

            // temp is a default guest account that saves data before making an account
            String currUserName;
            if (!userManager.isAnyoneLoggedIn()) {
                options.add(OPTION_CREATE_USER);
                options.add(OPTION_LOGIN);
                currUserName = "Guest";
            } else {
                currUserName = userManager.getCurrentUser().getFirstName();
                options.add(OPTION_MANAGE_USER);
                options.add(OPTION_LOGOUT);
            }

            options.add(OPTION_FLIGHT);
            options.add(OPTION_HOTEL);
            options.add(OPTION_BOOKINGS);
            options.add(OPTION_EXIT);
            clr();
            printHeader();
            println("Welcome " + ANSI_GREEN + currUserName + ANSI_RESET);
            String response = menuNumbered("Enter a Number", options);

            if (response == OPTION_EXIT) {
                exit();
            } else if (response == OPTION_FLIGHT) {
                menuBookFlight();
            } else if (response == OPTION_HOTEL) {
                menuBookHotel();
            } else if (response == OPTION_BOOKINGS) {
                menuEditBooking();
            } else if (response == OPTION_CREATE_USER) {
                menuCreateUser();
            } else if (response == OPTION_LOGIN) {
                menuLoginUser();
            } else if (response == OPTION_LOGOUT) {
                if (promptYN("Logout?"))
                    userManager.logoutCurrent();
            } else if (response == OPTION_MANAGE_USER) {
                menuManageCurrentUser();
            }
        }
    }

    /**
     * Menu that deals with the current user
     * 
     * @author rengotap, jbytes1027
     */
    private void menuManageCurrentUser() {
        while (true) {
            clr();
            final String OPTION_CHANGE_EMAIL = "Change Email";
            final String OPTION_CHANGE_PASSWORD = "Change password";
            final String OPTION_CHANGE_SEARCH_PREFERNECES = "Change search defaults";
            final String OPTION_MANAGE_PASSPORTS = "Manage passports";
            final String OPTION_DELETE_USER =
                    "Delete " + userManager.getCurrentUser().getUsername();
            final String OPTION_BACK = "Back to Main Menu";

            List<String> options = new ArrayList<String>();

            options.add(OPTION_CHANGE_EMAIL);
            options.add(OPTION_CHANGE_PASSWORD);
            options.add(OPTION_CHANGE_SEARCH_PREFERNECES);
            options.add(OPTION_MANAGE_PASSPORTS);
            options.add(OPTION_DELETE_USER);
            options.add(OPTION_BACK);

            User currUser = userManager.getCurrentUser();
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " " + currUser.getUsername()
                    + "'s User Profile " + ANSI_RESET + '\n');
            println(String.format("""
                    Username: %s
                    Name: %s %s
                    Email: %s
                    """, ANSI_CYAN+currUser.getUsername()+ANSI_RESET, ANSI_CYAN
                    +currUser.getRegisteredPerson().getFirstName(),
                    currUser.getRegisteredPerson().getLastName()
                    +ANSI_RESET, ANSI_CYAN+currUser.getEmail()+ANSI_RESET));

            String response = menuNumbered("Enter a Number", options);

            if (response.equals(OPTION_CHANGE_EMAIL)) {
                if (confirmPassword()) {
                    String email = promptString("Enter a new email");
                    userManager.getCurrentUser().setEmail(email);
                }
            } else if (response.equals(OPTION_CHANGE_PASSWORD)) {
                if (confirmPassword()) {
                    boolean confirm = false;
                    while (!confirm) {
                        String password = promptString("Enter a new password");
                        if (promptString("Confirm password").equals(password)) {
                            userManager.getCurrentUser().setPassword(password);
                            println("Password updated.");
                            confirm = true;
                        } else {
                            println("Passwords do not match, try again.");
                        }
                    }
                }
            } else if (response.equals(OPTION_CHANGE_SEARCH_PREFERNECES)) {
                menuChangePref();
            } else if (response.equals(OPTION_MANAGE_PASSPORTS)) {
                menuManagePassports();
            } else if (response.equals(OPTION_DELETE_USER)) {
                if (confirmPassword()) {
                    if (promptYN("Are you sure you want to delete this user? " + '\n' + ANSI_RED
                            + "This cannot be undone." + ANSI_RESET)) {
                        userManager.unregisterUser(userManager.getCurrentUser());
                        userManager.logoutCurrent();
                        println(ANSI_RED + "Deleted user account." + ANSI_RESET);
                        return;
                    }
                }
            } else if (response.equals(OPTION_BACK)) {
                return;
            }
        }
    }

    /**
     * Gets a user to confirm their password Used to protect more sensitive settings
     * 
     * @return true if password correct
     * @author rengotap
     */
    private boolean confirmPassword() {
        while (true) {
            String input = promptString("Enter current password or 'q' to quit");
            if (input.equals(userManager.getCurrentUser().getPassword())) { // current password
                                                                            // correct
                return true;
            } else if (input.equals("q")) { // user quits
                return false;
            } else {
                println("Incorrect password. Please try again"); // wrong choice
            }
        }
    }

    /**
     * Home menu for managing Passports
     * @author rengotap
     */
    private void menuManagePassports() {
        while(true) {
            clr();
            println(printPassports());
            final String OPT_ADD = "Add passport";
            final String OPT_RM = "Remove passport";
            final String OPT_BACK = "Back";
            List<String> options = new ArrayList<String>();
            if (userManager.getCurrentUser().getTravelers().size() != 0)
                options.add(OPT_RM);
            options.add(OPT_ADD);
            options.add(OPT_BACK);
            String response = menuNumbered("Enter a Number", options);
            if(response.equals(OPT_ADD)) {
                menuAddPassport();
            } else if (response.equals(OPT_RM)) {
                menuRemovePassport();
            } else if (response.equals(OPT_BACK)) {
                return;
            }
        }
    }

    /**
     * Displays a passport
     * @param p
     * @return
     * @author rengotap
     */
    private String displayPassport(Passport p) {
        return ANSI_CYAN+pad(p.getPerson().getFirstName(), 18)+ANSI_RESET + " | " 
        + ANSI_CYAN+pad(p.getPerson().getLastName(), 18)+ANSI_RESET + " | " 
        + ANSI_CYAN+pad(p.getGender(),6) + ANSI_RESET + " | " + ANSI_CYAN
        + pad(timeUtils.toString(p.getDOB()),8)+ANSI_RESET + " | " + ANSI_CYAN
        + pad(p.getNumber(),10) + ANSI_RESET + " | " + ANSI_CYAN + pad(timeUtils
        .toString(p.getExpDate()),8)+ANSI_RESET;
    }

    /**
     * Prints Current User's Passports
     * @return passportString
     */
    private String printPassports() {
        List<Passport> passports = userManager.getCurrentUser().getTravelers();
        String ret = '\n' + ANSI_BLACK + ANSI_WHITE_BG + " SAVED PASSPORTS " 
                + ANSI_RESET + '\n';
        if (passports.isEmpty())
            ret = ret + ANSI_RED + "No passports on file" + ANSI_RESET + '\n';
        else {
            // Header
            ret = ret + '\n' + pad("First Name",18) + "   " + pad("Last Name",18) + "   "
            + "Gender" + "   " + pad("D.O.B",8) + "   " + pad("Passport #",10) + "   "
            + "EXP Date" + '\n';
            for(int i = 0; i < passports.size(); i++) {
                ret = ret + displayPassport(passports.get(i)) + '\n';
            }
        }
        return ret;
    }

    /**
     * Menu that removes a passport
     * @author rengotap
     */
    private void menuRemovePassport() {
        while (!userManager.getCurrentUser().getTravelers().isEmpty()) {
            clr();
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " SAVED PASSPORTS " 
                + ANSI_RESET + '\n');
            List<Passport> passports = userManager.getCurrentUser().getTravelers();
            List<String> options = new ArrayList<String>();
            for (int i = 0; i < passports.size(); i++)
                options.add(displayPassport(passports.get(i)));
            final String OPT_BACK = "Back";
            options.add("Back");
            // Header
            println("    " + pad("First Name",18) + "   " + pad("Last Name",18) + "   "
            + "Gender" + "   " + pad("D.O.B",8) + "   " + pad("Passport #",10) + "   "
            + "EXP Date");
            String[] response = menuLong("Choose a number to remove, or '"+options.size()+"' to go back", options);
            if (response[0].equals(OPT_BACK)) {
                return;
            } else if (promptYN("Are you sure you want to remove this passport?")) {
                userManager.getCurrentUser().removeTraveler(passports.get(Integer.parseInt(response[1])));
                println("Passport removed");
                return;
            }
        }
    }

    /**
     * Menu that helps a user add a passport
     * 
     * @author jbytes1027
     */
    private Passport menuAddPassport() {
        Person newPerson = promptCreatePerson();
        String gender = promptString("Enter a gender");
        LocalDate birth = promptDate("Enter the date of birth");
        String number = promptString("Enter the passport number");
        LocalDate expiration = promptDate("Enter the passport expiration date");

        Passport created = new Passport(newPerson, birth, expiration, number, gender);
        userManager.getCurrentUser()
                .addTraveler(created);
        return created;
    }

    /**
     * menu for logging in
     * 
     * @author jbytes1027
     */
    private void menuLoginUser() {
        String username = promptString("Enter a username");
        if (!userManager.userExists(username)) {
            println("Not a registered user");
            return;
        }

        String password = promptString("Enter a password");
        if (!userManager.credentialsCorrect(username, password)) {
            println("Wrong credentials");
            return;
        }

        userManager.login(username, password);
        println("Logged in");
    }

    /**
     * Menu for creating a new person
     * 
     * @return created person
     * @author jbytes1027
     */
    private Person promptCreatePerson() {
        String firstName = promptString("Enter a first name");
        String lastName = promptString("Enter a last name");

        return new Person(firstName, lastName);
    }

    /**
     * Menu for creating a new user
     * 
     * @author jbytes1027, rengotap
     */
    private void menuCreateUser() {
        Person newPerson = promptCreatePerson();
        String username;
        while (true) {
            username = promptString("Enter a username");
            if (userManager.userExists(username)) {
                println("User already exists");
            } else {
                break;
            }
        }
        String password = "";
        boolean confirm = false;
        while (!confirm) {
            password = promptString("Enter a password");
            if (promptString("Confirm password").equals(password)) {
                confirm = true;
            } else {
                println("Passwords do not match, try again.");
            }
        }
        User newUser = new User(newPerson, username, password);
        userManager.registerUser(newUser);
        userManager.logoutCurrent();
        userManager.login(username, password);
        userManager.getCurrentUser().setEmail(promptString("Enter an email address"));

        println('\n' + ANSI_GREEN + "Successfully created user " + username + ANSI_RESET + '\n');
    }

    /**
     * User search preferences menu
     * 
     * @author rengotap
     */
    private void menuChangePref() {
        final String OPTION_FPREF = "Change Flight Preferences";
        final String OPTION_HPREF = "Change Hotel Preferences";
        final String OPTION_BACK = "Back to User Menu";

        List<String> options = new ArrayList<String>();

        options.add(OPTION_FPREF);
        options.add(OPTION_HPREF);
        options.add(OPTION_BACK);

        String response = menuNumbered("Enter a Number", options);
        if (response.equals(OPTION_FPREF)) {
            menuChangeFPref();
        } else if (response.equals(OPTION_HPREF)) {
            menuChangeHPref();
        } else if (response.equals(OPTION_BACK)) {
            return;
        }

    }

    /**
     * Changes user Flight prefrences
     * 
     * @author rengotap
     */
    private void menuChangeFPref() {
        while (true) {
            clr();
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " CURRENT FLIGHT PREFERENCES "
                    + ANSI_RESET + '\n');
            final String OPTION_HOMEPORT = "Home Airport Code: " + ANSI_CYAN
                    + userManager.getCurrentUser().getFPref().get(FlightFilter.AIRPORT_FROM)
                    + ANSI_RESET;
            final String OPTION_COMPANY = "Company: " + ANSI_CYAN + userManager.getCurrentUser()
                    .getFPref().get(FlightFilter.COMPANY) + ANSI_RESET;
            final String OPTION_TIME_DEPART = "Earliest Departure Time (HH:MM): " + ANSI_CYAN 
                    + userManager.getCurrentUser().getFPref().get(FlightFilter
                    .TIME_DEPART_EARLIEST) + ANSI_RESET;
            final String OPTION_TIME_ARRIVE = "Latest Arrival Time (HH:MM): " + ANSI_CYAN
                    + userManager.getCurrentUser().getFPref()
                    .get(FlightFilter.TIME_ARRIVE_LATEST) + ANSI_RESET;
            final String OPTION_BACK = "Back to User Menu";
            List<String> options = new ArrayList<String>();

            options.add(OPTION_HOMEPORT);
            options.add(OPTION_COMPANY);
            options.add(OPTION_TIME_DEPART);
            options.add(OPTION_TIME_ARRIVE);
            options.add(OPTION_BACK);

            String response = menuNumbered("Enter a Number", options);
            if (response.equals(OPTION_HOMEPORT)) {
                userManager.getCurrentUser().getFPref().put(FlightFilter.AIRPORT_FROM,
                        promptString("Enter a new Home Airport Code: "));
            } else if (response.equals(OPTION_COMPANY)) {
                userManager.getCurrentUser().getFPref().put(FlightFilter.COMPANY,
                        promptString("Enter a new Company: "));
            } else if (response.equals(OPTION_TIME_DEPART)) {
                userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_DEPART_EARLIEST,
                        promptString("Enter your earliest time (HH:MM)"));
            } else if (response.equals(OPTION_TIME_ARRIVE)) {
                userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_ARRIVE_LATEST,
                        promptString("Enter your latest time (HH:MM): "));
            } else if (response.equals(OPTION_BACK)) {
                return;
            }
        }
    }

    /**
     * Changes user Hotel preferences
     * 
     * @author rengotap
     */
    private void menuChangeHPref() {
        while (true) {
            clr();
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " CURRENT HOTEL PREFERENCES "
                    + ANSI_RESET + '\n');
            final String OPTION_COMPANY = "Company: " + ANSI_CYAN + userManager
                    .getCurrentUser().getHPref().get(HotelFilter.COMPANY) + ANSI_RESET;
            final String OPTION_BACK = "Back to User Menu";

            List<String> options = new ArrayList<String>();

            options.add(OPTION_COMPANY);
            options.add(OPTION_BACK);

            String response = menuNumbered("Enter a Number", options);
            if (response.equals(OPTION_COMPANY)) {
                userManager.getCurrentUser().getHPref().put(HotelFilter.COMPANY,
                        promptString("Enter a new company name:"));
            } else if (response.equals(OPTION_BACK)) {
                return;
            }
        }
    }

    /**
     * UI for booking a flight
     * 
     * @author rengotap
     */
    private void menuBookFlight() {
        User curr = userManager.getCurrentUser();
        String destination;
        String home;
        LocalDate depart;
        LocalDate arrive;
        String timeEarly;
        String timeLate;
        String company;


        destination = promptString("Please enter a destination");

        if (userManager.isAnyoneLoggedIn()) {
            if (hasPref(curr.getFPref().get(FlightFilter.AIRPORT_FROM))) { // Check for user pref
                home = curr.getFPref().get(FlightFilter.AIRPORT_FROM);
            } else {
                home = promptString("What is your home airport?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.AIRPORT_FROM, home);
                }
            }

            if (hasPref(curr.getFPref().get(FlightFilter.TIME_DEPART_EARLIEST))) {
                timeEarly = curr.getFPref().get(FlightFilter.TIME_DEPART_EARLIEST);
            } else {
                timeEarly = promptString(
                        "What is the earliest time you would be willing to leave? (Enter 'any' for no preference)");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_DEPART_EARLIEST,
                            timeEarly);
                }
            }

            if (hasPref(curr.getFPref().get(FlightFilter.TIME_ARRIVE_LATEST))) {
                timeLate = curr.getFPref().get(FlightFilter.TIME_ARRIVE_LATEST);
            } else {
                timeLate = promptString(
                        "What is the latest time you would be willing to arrive? (Enter 'any' for no preference)");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_ARRIVE_LATEST,
                            timeLate);
                }
            }
        } else {
            home = promptString("What is your home airport?");
            timeEarly = promptString("What is the earliest time you would be willing to leave?");
            timeLate = promptString("What is the latest time you would be willing to leave?");
        }

        depart = promptDate("Choose a departure date");
        arrive = promptDate("Chose a date you would like to arrive by");

        if (userManager.isAnyoneLoggedIn()) {
            if (hasPref(curr.getFPref().get(FlightFilter.COMPANY))) {
                company = curr.getFPref().get(FlightFilter.COMPANY);
            } else {
                company = promptString(
                        "What company would you like to book with? (Enter 'any' for no preference)");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.COMPANY, company);
                }
            }
        } else {
            company = promptString(
                    "What company would you like to book with? (Enter 'any' for no preference)");
        }

        boolean confirmParam = false;
        while (!confirmParam) {
            clr();
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " CONFIRM SEARCH PARAMETERS "
                    + ANSI_RESET + '\n');
            final String OPT_DEST = "Destination: " + ANSI_CYAN + destination + ANSI_RESET;
            final String OPT_HOME = "Departing From:" + ANSI_CYAN + home + ANSI_RESET;
            final String OPT_START = "Departure Date: " + ANSI_CYAN + toString(depart) + ANSI_RESET;
            final String OPT_END = "Arrive By: " + ANSI_CYAN + toString(arrive) + ANSI_RESET;
            final String OPT_TIME_EARLY = "Earliest Time: " + ANSI_CYAN + timeEarly + ANSI_RESET;
            final String OPT_TIME_LATE = "Latest Time: " + ANSI_CYAN + timeLate + ANSI_RESET;
            final String OPT_COMPANY = "Airline: " + ANSI_CYAN + company + ANSI_RESET;
            final String OPT_CONFIRM = ANSI_GREEN + "Confirm & Search" + ANSI_RESET;

            List<String> options = new ArrayList<String>();
            options.add(OPT_DEST);
            options.add(OPT_HOME);
            options.add(OPT_START);
            options.add(OPT_END);
            options.add(OPT_TIME_EARLY);
            options.add(OPT_TIME_LATE);
            options.add(OPT_COMPANY);
            options.add(OPT_CONFIRM);

            String response = menuNumbered("Enter a Number", options);

            if (response.equals(OPT_CONFIRM)) {
                confirmParam = true;
            } else if (response.equals(OPT_DEST)) {
                destination = promptString("Enter a new destination");
            } else if (response.equals(OPT_HOME)) {
                home = promptString("Enter a new departure location");
            } else if (response.equals(OPT_START)) {
                depart = promptDate("Enter a new departure date");
            } else if (response.equals(OPT_END)) {
                arrive = promptDate("Enter a new arrival date");
            } else if (response.equals(OPT_TIME_EARLY)) {
                timeEarly = promptString("Enter the earliest time you would be willing to leave (HH:MM)");
            } else if (response.equals(OPT_TIME_LATE)) {
                timeLate = promptString("Enter the latest time you would be willing to arrive (HH:MM)");
            } else if (response.equals(OPT_COMPANY)) {
                company = promptString("Enter a new airline");
            } else if (response.equals(OPT_CONFIRM)) {
                confirmParam = true;
            }
        }
        SearchPreferences queryPrefs = new SearchPreferences();
        var queryFlightPrefs = queryPrefs.getFPref();
        queryFlightPrefs.put(FlightFilter.AIRPORT_TO, destination);
        queryFlightPrefs.put(FlightFilter.AIRPORT_FROM, home);
        queryFlightPrefs.put(FlightFilter.DATE_DEPART_EARLIEST, toString(depart));
        queryFlightPrefs.put(FlightFilter.DATE_ARRIVE_LATEST, toString(arrive));
        queryFlightPrefs.put(FlightFilter.TIME_DEPART_EARLIEST, timeEarly);
        queryFlightPrefs.put(FlightFilter.TIME_ARRIVE_LATEST, timeLate);
        queryFlightPrefs.put(FlightFilter.COMPANY, company);

        clr();
        System.out.println('\n' + "Searching for your perfect flight..." + '\n');
        pBar();
        showTrips(queryPrefs);
    }

    private void showTrips(SearchPreferences query) {
        List<FlightTrip> results = SearchFlightTrips.execute(query);

        if (results.isEmpty()) {
            clr();
            println('\n'+ANSI_RED + "Unable to find any matching results." + ANSI_RESET + '\n' 
                + "Try changing your search parameters");
            awaitEnter();
            return;
        }

        List<String> options = new ArrayList<String>();
        int numDisplay = 4; // will show up to 4 results
        if (results.size() < 4)
            numDisplay = results.size();

        for (int i = 0; i < numDisplay; i++) {
            options.add(displayFlightTripSimple(results.get(i)));
        }
        final String OPT_BACK = "Return to main menu";
        options.add(OPT_BACK);
        while (true) {
            clr();
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " SELECT TRIP FOR DETAILS "
                    + ANSI_RESET + '\n');
            String[] response = menuLong("Enter a Number", options);
            if (response[0].equals(OPT_BACK)) {
                return;
            } else {
                if (investigateTrip(results.get(Integer.parseInt(response[1]))))
                    return;
            }
        }
    }

    private boolean investigateTrip(FlightTrip trip) {
        while (true) {
            clr();
            println('\n' + displayFlightTripFull(trip));
            if (promptYN("Book this trip?")) {
                if (!userManager.isAnyoneLoggedIn())
                    forceAccount();

                int bookSeats = promptNumber("How many seats would you like to book?", 0, 10);
                List<Flight> fl = trip.getFlights(); // flight list
                clr();
                for (int i = 0; i < bookSeats; i++) {
                    Passport ticketHolder = forcePassport();
                    for (int curr = 0; curr < fl.size(); curr++) {
                        clr();
                        println('\n' + ANSI_WHITE_BG + ANSI_BLACK + "   NOW BOOKING FLIGHT "
                                + (curr + 1) + " OF " + fl.size() + "   " + ANSI_RESET + '\n');
                        println(displayFlightSimple(fl.get(i)));
                        menuBookSeat(fl.get(curr), ticketHolder);
                    }
                    if (i + 1 < bookSeats) {
                        clr();
                        println('\n' + ANSI_WHITE_BG + ANSI_BLACK + "   NOW BOOKING NEXT USER "
                                + ANSI_RESET + '\n');
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    private void menuBookSeat(Flight flight, Passport ticketHolder) {
        while (true) {
            List<Seat> seats = flight.getAvailableOptions();

            List<String> options = new ArrayList<String>();
            for (int i = 0; i < seats.size(); i++) {
                options.add(displaySeat(seats.get(i)));
            }
            
            final String OPT_DISPMAP = "Display Seat Map";
            options.add(OPT_DISPMAP);
            String[] response = new String[2];
            Boolean chosen = false;
            Boolean flag = true;
            while(!chosen) {
                if(flag){flag=false;}else{clr();} // Does not clear on first call
                println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " SELECT SEAT OR '"
                + options.size() +"' TO VIEW SEAT MAP "+ ANSI_RESET+ '\n');
                response = menuLong("Enter a Number", options);
                if(response[0].equals(OPT_DISPMAP)) {
                    clr();
                    println(flightMap(flight));
                    println("Available seats are highlighted in " + ANSI_GREEN + "green"
                            + ANSI_RESET + '\n');
                    awaitEnter();
                } else { // User chose an acual seat
                    chosen = true;
                }
            }
            int index = Integer.parseInt(response[1]);
            if (promptYN(displaySeat(seats.get(index)) + '\n' + "Book this seat?")) {
                try {
                    Booking b = bookingAgent.bookListing(flight.getAvailableOptions().get(index),
                            userManager.getCurrentUser(), ticketHolder);
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    clr();
                    println('\n' + ANSI_WHITE_BG + ANSI_BLACK + " " + timestamp
                            + "   SEAT BOOKED SUCCESSFULLY   " + ANSI_RESET + '\n');

                    println(printer.print(b));

                    println('\n' + "The details of your booking have been emailed to: " + ANSI_CYAN
                            + userManager.getCurrentUser().getEmail() + ANSI_RESET + '\n'
                            + "You can also view your new booking in the 'Manage Bookings' menu"
                            + '\n');
                    awaitEnter();
                } catch (Exception e) {
                    println(ANSI_YELLOW + "WARN: Failed to book booking" + ANSI_RESET);
                }
                return;
            }
        }

    }

    /**
     * UI for booking a hotel
     * 
     * @author rengotap
     */
    private void menuBookHotel() {
        // Check User prefrences
        User curr = userManager.getCurrentUser();
        String location;
        LocalDate start;
        LocalDate end;
        String company;

        location = promptString("Where would you like to make a reservation?");
        start = promptDate("When would you like to start your reservation?");
        end = promptDate("When would you like to end your reservation?");

        if (userManager.isAnyoneLoggedIn()) {
            if (hasPref(curr.getHPref().get(HotelFilter.COMPANY))) {
                company = curr.getHPref().get(HotelFilter.COMPANY);
            } else {
                company = promptString(
                        "What company would you like to book with? (Enter 'any' for no preference)");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getHPref().put(HotelFilter.COMPANY, company);
                }
            }
        } else {
            company = promptString(
                    "What company would you like to book with? (Enter 'any' for no preference)");
        }

        boolean confirmParam = false;
        while (!confirmParam) {
            clr();
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " CONFIRM SEARCH PARAMETERS "
                    + ANSI_RESET + '\n');
            final String OPT_LOCATION = "Location: " + ANSI_CYAN + location + ANSI_RESET;
            final String OPT_START = "Start Date: " + ANSI_CYAN + toString(start) + ANSI_RESET;
            final String OPT_END = "End Date: " + ANSI_CYAN + toString(end) + ANSI_RESET;
            final String OPT_COMPANY = "Company: " + ANSI_CYAN + company + ANSI_RESET;
            final String OPT_CONFIRM = ANSI_GREEN + "Confirm & Search" + ANSI_RESET;

            List<String> options = new ArrayList<String>();
            options.add(OPT_LOCATION);
            options.add(OPT_START);
            options.add(OPT_END);
            options.add(OPT_COMPANY);
            options.add(OPT_CONFIRM);

            String response = menuNumbered("Enter a Number", options);

            if (response.equals(OPT_CONFIRM)) {
                confirmParam = true;
            } else if (response.equals(OPT_LOCATION)) {
                location = promptString("Enter a new location");
            } else if (response.equals(OPT_START)) {
                start = promptDate("Enter a new start date");
            } else if (response.equals(OPT_END)) {
                end = promptDate("Enter a new end date");
            } else if (response.equals(OPT_COMPANY)) {
                company = promptString("Enter a new company");
            }
        }
        clr();
        System.out.println('\n' + "Searching for your perfect hotel..." + '\n');
        pBar();
        clr();

        SearchPreferences prefs = new SearchPreferences();
        EnumMap<HotelFilter, String> query = prefs.hPref;
        query.put(HotelFilter.LOCATION, location);
        query.put(HotelFilter.COMPANY, company);
        query.put(HotelFilter.DATE_START, toString(start));
        query.put(HotelFilter.DATE_END, toString(end));

        hotelResult(prefs);
    }

    /**
     * Displays search results and allows user to purchase tickets
     * 
     * @param query search parameters
     * @author rengotap
     */
    private void hotelResult(SearchPreferences prefs) {
        List<Hotel> results = SearchHotels.execute(prefs);
        LocalDate from =
                TimeUtils.getInstance().generateDate(prefs.hPref.get(HotelFilter.DATE_START));
        LocalDate to = TimeUtils.getInstance().generateDate(prefs.hPref.get(HotelFilter.DATE_END));

        if (!results.isEmpty()) {
            List<String> options = new ArrayList<String>();
            int numDisplay = 4; // will show up to 4 results
            if (results.size() < 4)
                numDisplay = results.size();

            for (int i = 0; i < numDisplay; i++) {
                options.add(displayHotelSimple(results.get(i), from, to));
            }
            final String OPT_BACK = "Return to main menu";
            options.add(OPT_BACK);
            while (true) {
                clr();
                println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " SELECT HOTEL FOR DETAILS "
                        + ANSI_RESET + '\n');
                String[] response = menuLong("Enter a Number", options);
                if (response[0].equals(OPT_BACK)) {
                    return;
                } else {
                    if (investigateHotel(results.get(Integer.parseInt(response[1])), from, to))
                        return;
                }
            }
        } else {
            if (results.isEmpty()) {
                clr();
                println('\n'+ANSI_RED + "Unable to find any matching results." + ANSI_RESET + '\n' 
                    + "Try changing your search parameters");
                awaitEnter();
                return;
            }
        }
    }

    /**
     * Provides more information about a hotel, and allows the user to book it
     * 
     * @param hotel
     * @return returns true if hotel was booked
     * @author rengotap
     */
    private boolean investigateHotel(Hotel hotel, LocalDate from, LocalDate to) {
        clr();
        println(displayHotelFull(hotel, from, to));
        if (promptYN("Book this hotel?")) {
            if (menuPickRoom(hotel, from, to))
                return true;
        }
        return false; // go back to results
    }

    /**
     * Menu for picking a hotel room
     * 
     * @param hotel
     * @return if a room was booked
     * @author rengotap
     */
    private boolean menuPickRoom(Hotel hotel, LocalDate from, LocalDate to) {
        List<Room> rooms = hotel.getAvailableOptions(from, to);

        List<String> options = new ArrayList<String>();
        for (int i = 0; i < rooms.size(); i++) {
            options.add(displayRoom(rooms.get(i)));
        }
        final String OPT_BACK = "Go back";
        options.add(OPT_BACK);

        while (true) {
            clr();
            displayHotelFull(hotel, from, to);
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " SELECT ROOM " + ANSI_RESET
                    + '\n');
            String[] response = menuLong("Enter a Number", options);
            if (response[0].equals(OPT_BACK)) {
                return false;
            } else {
                Room room = rooms.get(Integer.parseInt(response[1]));
                if (promptYN(displayRoom(room) + '\n' + "Book this room?")) {
                    menuBookRoom(hotel, room, from, to);
                    return true;
                }
            }
        }
    }

    /**
     * Menu for booking a room
     * 
     * @param hotel
     * @param room
     * @author rengotap
     */
    private void menuBookRoom(Hotel hotel, Room room, LocalDate from, LocalDate to) {
        if (!userManager.isAnyoneLoggedIn())
            forceAccount();

        /*
         * // We already asked them this try { LocalDate bookFrom; LocalDate bookTo; while (true) {
         * bookFrom = promptDate("When do you want to book from?"); bookTo =
         * promptDate("When do you want to book to?");
         * 
         * if (room.isBooked(bookFrom, bookTo)) { println("Room is booked then, try again"); } else
         * { break; } }
         */
        try {
            Booking b = bookingAgent.bookListing(room, userManager.getCurrentUser(), from, to);
            clr();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            println('\n' + ANSI_WHITE_BG + ANSI_BLACK + " " + timestamp
                    + "   ROOM BOOKED SUCCESSFULLY   " + ANSI_RESET + '\n');

            println(printer.print(b));

            println('\n' + "The details of your booking have been emailed to: " + ANSI_CYAN
                    + userManager.getCurrentUser().getEmail() + ANSI_RESET + '\n'
                    + "You can also view your new booking in the 'Manage Bookings' menu" + '\n');
            awaitEnter();

        } catch (Exception e) {
            println(ANSI_YELLOW + "WARN: Failed to book bookable" + ANSI_RESET);
        }
    }

    /**
     * Helper method to determine if user has a preference
     * 
     * @param in pref to test
     * @return if pref = none
     * @author rengotap
     */
    private boolean hasPref(String in) {
        if (in.equals("none"))
            return false;
        return true;
    }

    /**
     * Forces a guest user to either login or create account Ensures bookings have a place to go
     * @author rengotap
     */
    private void forceAccount() {
        clr();
        println("You don't appear to have a user account."
                + " You'll need to create one to save your booking" + '\n');
        final String OPT_CREATE = "Create an account";
        final String OPT_LOGIN = "I already have an account";
        List<String> options = new ArrayList<String>();
        options.add(OPT_CREATE);
        options.add(OPT_LOGIN);
        String response = menuNumbered("Enter a Number", options);
        if (response.equals(OPT_CREATE)) {
            menuCreateUser();
        } else if (response.equals(OPT_LOGIN)) {
            menuLoginUser();
        }
    }

    /**
     * Forces the user to either pick or make a passport for their ticket
     * @return
     */
    private Passport forcePassport() {
        List<Passport> passports = userManager.getCurrentUser().getTravelers();
        if (passports.isEmpty()) { // No passport?
            println(ANSI_RED + "No passports on file" + ANSI_RESET + '\n'
                    + "You need to create a passport before continuing" + '\n');
            return menuAddPassport();
        }
        List<String> options = new ArrayList<String>();
        for (int i = 0; i < passports.size(); i++)
            options.add(displayPassport(passports.get(i)));
        final String OPT_NEW = "Create New";
        options.add(OPT_NEW);
        println('\n' + ANSI_BLACK + ANSI_WHITE_BG + " SAVED PASSPORTS " 
                + ANSI_RESET + '\n');
        println("    " + pad("First Name",18) + "   " + pad("Last Name",18) + "   "
            + "Gender" + "   " + pad("D.O.B",8) + "   " + pad("Passport #",10) + "   "
            + "EXP Date");
        String[] response = menuLong("Choose a number to book with, or '"+options.size()+"' to create new", options);
        if (response[0].equals(OPT_NEW)) {
            return menuAddPassport();
        } else {
            return passports.get(Integer.parseInt(response[1]));
        }
    }

    /**
     * Menu for viewing, exporting, and deleting bookings
     * 
     * @author rengotap
     */
    private void menuEditBooking() {
        while (true) {
            clr();
            final String OPT_INSPECT = "View Booking Details";
            final String OPT_PRINT = "Export Bookings";
            final String OPT_CANCEL = "Cancel Booking";
            final String OPT_BACK = "Back";

            List<String> options = new ArrayList<String>();
            println('\n' + ANSI_WHITE_BG + ANSI_BLACK + " YOUR BOOKINGS: " + ANSI_RESET + '\n');
            if (userManager.isAnyoneLoggedIn()
                    && !userManager.getCurrentUser().getBookingHistory().isEmpty()) {
                List<Booking> bookings = userManager.getCurrentUser().getBookingHistory();
                for (int i = 0; i < bookings.size(); i++) {
                    println(toString(bookings.get(i).getBooked()));
                }
                options.add(OPT_INSPECT);
                options.add(OPT_PRINT);
                options.add(OPT_CANCEL);
            } else {
                println('\n' + ANSI_RED + "No bookings on file" + ANSI_RESET);
            }
            options.add(OPT_BACK);
            println("");
            String response = menuNumbered("Enter a Number", options);
            if (response.equals(OPT_INSPECT)) {
                menuInspectBooking();
            } else if (response.equals(OPT_PRINT)) {
                menuPrintBooking();
            } else if (response.equals(OPT_CANCEL)) {
                menuCancelBooking();
            } else if (response.equals(OPT_BACK)) {
                return;
            }
        }
    }

    public void menuInspectBooking() {
        clr();
        List<Booking> bookings = userManager.getCurrentUser().getBookingHistory();
        List<String> options = new ArrayList<String>();
        for (int i = 0; i < bookings.size(); i++)
            options.add(toString(bookings.get(i).getBooked()));
        final String OPTIONS_BACK = "Return to main menu";
        options.add(OPTIONS_BACK); // at position size+1

        println('\n' + ANSI_WHITE_BG + ANSI_BLACK + " YOUR BOOKINGS: " + ANSI_RESET + '\n');
        String[] response = menuLong(
                "Choose a booking to view, or enter '" + options.size() + "' to go back", options);
        if (response[0].equals(OPTIONS_BACK)) {
            return;
        } else {
            clr();
            println(printer.print(bookings.get(Integer.parseInt(response[1]))));
            awaitEnter();
            clr();
        }
    }

    /**
     * Cancels bookings
     * 
     * @author rengotap
     */
    private void menuCancelBooking() {
        clr();
        List<Booking> bookings = userManager.getCurrentUser().getBookingHistory();
        List<String> options = new ArrayList<String>();
        for (int i = 0; i < bookings.size(); i++) { // should add every booking as an option
            options.add(toString(bookings.get(i).getBooked()));
        }
        final String OPTIONS_BACK = "Return to main menu";
        options.add(OPTIONS_BACK); // at position size+1
        println('\n' + ANSI_WHITE_BG + ANSI_BLACK + " YOUR BOOKINGS: " + ANSI_RESET + '\n');
        String[] response =
                menuLong("Choose a booking to cancel, or enter '" + options.size() + "' to go back",
                        options);
        if (response[0].equals(OPTIONS_BACK)) {
            return;
        } else if (promptYN("Are you sure you want to cancel this booking?")) {
            bookingAgent.unbookListing(userManager.getCurrentUser().getBookingHistory()
                    .get(Integer.parseInt(response[1])));
            userManager.getCurrentUser().removeBooking(Integer.parseInt(response[1]));
            println(ANSI_RED + "Booking canceled." + ANSI_RESET + '\n'
                    + "Your payment has been refunded.");
        }
    }

    /**
     * The user now prints their itinerary for both of their flights, and the details for their
     * hotel reservation. Printing means creating a beautifully formatted text file.
     * 
     * @author rengotap
     */
    private void menuPrintBooking() {
        final String OPT_EXP = "Export Print Queue";
        final String OPT_ENQ = "Add Bookings";
        final String OPT_DEQ = "Remove Bookings";
        final String OPT_WIPE = "Empty Print Queue";
        final String OPT_BACK = "Back";
        while (true) {
            clr();
            List<String> options = new ArrayList<String>();
            ArrayList<Booking> pq = printer.getPrintQueue();
            printPQ();
            println("");
            if (!pq.isEmpty()) {
                options.add(OPT_EXP);
                options.add(OPT_ENQ);
                options.add(OPT_DEQ);
                options.add(OPT_WIPE);
            } else {
                options.add(OPT_ENQ);
            }
            options.add(OPT_BACK);
            String response = menuNumbered("Enter a Number", options);
            if (response.equals(OPT_BACK)) {
                return;
            } else if (response.equals(OPT_EXP)) {
                println(ANSI_B + pad("Exporting to file.", 41) + ANSI_RESET + '\n');
                pBar();
                clr();
                printer.print();
                awaitEnter();
            } else if (response.equals(OPT_ENQ)) {
                menuEnqueuePrint();
            } else if (response.equals(OPT_DEQ)) {
                menuDequeuePrint();
            } else if (response.equals(OPT_WIPE)) {
                if (promptYN("Are you sure you want to empty the print queue?"))
                    printer.wipe();
            }
        }
    }

    /**
     * Select bookables to add to the print queue
     * 
     * @author rengotap
     */
    private void menuEnqueuePrint() {
        List<Booking> bookings = userManager.getCurrentUser().getBookingHistory();
        while (true) {
            clr();
            printPQ();
            println(" ");
            List<String> options = new ArrayList<String>();
            println('\n' + ANSI_WHITE_BG + ANSI_BLACK + " YOUR BOOKINGS: " + ANSI_RESET + '\n');
            for (int i = 0; i < bookings.size(); i++) // should add every booking as an option
                options.add(toString(bookings.get(i).getBooked()));

            final String OPT_BACK = "Back";
            options.add(OPT_BACK);

            String[] response = menuLong("Choose a booking to " + ANSI_GREEN + "enqueue"
                    + ANSI_RESET + ", or enter " + options.size() + " to go back", options);
            if (response[0].equals(OPT_BACK)) {
                return;
            } else {
                Booking b = userManager.getCurrentUser().getBookingHistory()
                        .get(Integer.parseInt(response[1]));
                printer.enqueue(b);
            }
        }
    }

    /**
     * Select bookables to remove from the print queue
     * 
     * @author rengotap
     */
    private void menuDequeuePrint() {
        final String OPT_BACK = "Back";
        while (true) {
            clr();
            ArrayList<Booking> pq = printer.getPrintQueue();
            if (pq.isEmpty()) // if all items deleted
                return;
            List<String> options = new ArrayList<String>();
            println('\n' + ANSI_WHITE_BG + ANSI_BLACK + " CURRENT PRINT QUEUE: " + ANSI_RESET
                    + '\n');
            for (int i = 0; i < pq.size(); i++)
                options.add(toString(pq.get(i).getBooked()));
            options.add(OPT_BACK);
            String[] response = menuLong("Choose a booking to " + ANSI_RED + "dequeue" + ANSI_RESET
                    + ", or enter " + options.size() + " to go back", options);
            if (response[0].equals(OPT_BACK)) {
                return;
            } else {
                println(ANSI_RED + "Removed the following booking from the print queue:"
                        + ANSI_RESET + '\n'
                        + toString(pq.get(Integer.parseInt(response[1])).getBooked()));
                printer.dequeue(Integer.parseInt(response[1]));
            }
        }
    }

    /**
     * Prints the Print Queue
     * 
     * @author rengotap
     */
    private void printPQ() {
        println('\n' + ANSI_WHITE_BG + ANSI_BLACK + " CURRENT PRINT QUEUE: " + ANSI_RESET + '\n');
        ArrayList<Booking> pq = printer.getPrintQueue();
        if (!pq.isEmpty()) {
            for (int i = 0; i < pq.size(); i++)
                println(toString(pq.get(i).getBooked()));
        } else {
            println(ANSI_RED + "Queue is empty!" + ANSI_RESET);
        }
    }

    /**
     * <<<<<<< HEAD Turns a table into an ArrayList ======= Turns the rows of a 2d array of strings
     * into one strings with each element padded with whitespace
     * 
     * >>>>>>> flight-search
     * 
     * @param table
     * @return
     * @author jbytes1027
     */
    List<String> toStringTable(List<List<String>> table) {
        if (table.size() == 0)
            return new ArrayList<String>();

        int numCol = table.get(0).size();
        int[] colPadding = new int[numCol];
        for (int row = 0; row < table.size(); row++) {
            for (int col = 0; col < numCol; col++) {
                int currStringLen = table.get(row).get(col).length();
                if (colPadding[col] < currStringLen) {
                    colPadding[col] = currStringLen;
                }
            }
        }

        List<String> out = new ArrayList<String>();
        for (int row = 0; row < table.size(); row++) {
            String rowString = "";
            for (int col = 0; col < numCol; col++) {
                rowString += String.format("%-" + colPadding[col] + "s", table.get(row).get(col));
                rowString += " ";
            }
            out.add(rowString);
        }

        return out;
    }

    /**
     * Safely exits the program
     * 
     * @author jbytes1027
     */
    public void exit() {
        println("Exiting...");
        data.saveAll();
        System.exit(0);
    }
}
