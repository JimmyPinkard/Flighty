import database.Data;
import model.bookables.Bookable;
import model.bookables.flight.Flight;
import model.bookables.flight.FlightTrip;
import model.bookables.hotel.Room;
import model.users.SearchPreferences;
import model.users.User;
import model.users.info.Passport;
import model.users.info.Person;
import controller.BookingAgent;
import controller.UserManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
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
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BG = "\u001B[47m";


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
        data = Data.getInstance();
        input = new Scanner(System.in);
        userManager = new UserManager(data);
        bookingAgent = new BookingAgent();
        // TODO: uncomment this when data is ready
        //genBogusData();
        //checkData();  // Comment this out to run program with empty input data
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
     * Saves all data to database
     * 
     * @author JimmyPinkard
     */
    public void stop() {
        data.saveAll();
    }

    /**
     * Generates bogus testing data
     * for testing purposes only
     * @author rengotap
     */
    private void genBogusData() {

        println(displayRoom(new Room()));

        int numToGenerate = 5;

        List<User> users = new ArrayList<User>();
        List<Flight> flights = new ArrayList<Flight>();
        List<Hotel> hotels = new ArrayList<Hotel>();
        List<Booking> bookings = new ArrayList<Booking>();

        for (int i = 0; i < numToGenerate; i++) {
            flights.add(new Flight());
            hotels.add(new Hotel());
            bookings.add(new Booking());
        }
        User newUser = new User();
        userManager.registerUser(newUser);
        userManager.logoutCurrent();
        userManager.login("temp","p");
        for (int i = 0; i < numToGenerate; i++ )
            userManager.getCurrentUser().addBooking(bookings.get(i).getBooked());
        users.add(newUser);


        data = new Data(users, flights, hotels, bookings);

    }

    /**
     * Checks to make sure data is all there, terminates if it isn't
     * For debugging
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");
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
     * Prompts the user for a table
     * 
     * @param prompt
     * @param table
     * @return
     * @author jbytes1027
     */
    private String promptTable(String prompt, List<String> table) {
        String header = table.get(0);
        table.remove(0);

        println("   " + header);
        return menuNumbered(prompt, table);
    }

    /**
     * Converts passports into a table of strings
     * 
     * @param passports
     * @return table
     * @author jbytes1027
     */
    private List<String> passportsToStringTable(List<Passport> passports) {
        List<List<String>> table = new ArrayList<List<String>>();

        List<String> headerRow = new ArrayList<String>();
        headerRow.add("NAME");
        headerRow.add("DOB");
        headerRow.add("GENDER");
        headerRow.add("EXPIRATION");
        headerRow.add("NUMBER");

        table.add(headerRow);

        for (Passport passport : passports) {
            table.add(toRow(passport));
        }

        return toStringTable(table);
    }

    /**
     * Helper method for passportsToStringTable()
     * 
     * @param passport
     * @return
     * @author jbytes1027
     */
    private List<String> toRow(Passport passport) {
        List<String> row = new ArrayList<String>();

        row.add(passport.getPerson().getFirstName() + " " + passport.getPerson().getLastName());
        row.add(TimeUtils.getInstance().toString(passport.getDOB()));
        row.add(passport.getGender());
        row.add(TimeUtils.getInstance().toString(passport.getExpDate()));
        row.add(passport.getNumber());

        return row;
    }

    /**
     * Turns the list of options into a string
     * 
     * @param options list of options
     * @return converted string
     * @author jbytes1027
     */
    private String toString(List<String> options) {
        String out = "";
        out += " [";
        for (int i = 0; i < options.size(); i++) {
            out += options.get(i);
            if (i != options.size() - 1) {
                out += "/";
            }
        }
        out += "]\n";

        return out;
    }

    /**
     * Turns features into a nice block
     * @param input data of the block
     * @param width width of the block
     * @return nice block
     * @author rengotap
     */
    private String toBlock(List<String> input, int width) {
        String ret = "";
        for (int i = 1; i < input.size()+1; i++) {
            ret = ret + input.get(i-1) + ", ";
            if (i % width == 0)
                ret = ret + '\n';
        }
        return ret.trim().substring(0, ret.length() - 1);
    }

    /**
     * Displays a bookable as a single line
     * TYPE, COST, DESTINATION/LOCATION, COMPANY, ROOM NUM/SEAT NUM
     * @return
     */
    private String toString(Bookable b) {  // TODO: Make this display properly
        String type = b.getClass().getSimpleName();
        return type + " | $" + String.valueOf(b.getPrice()) + " | " + "LOCATION" + " | " + "COMPANY" + " | " + b.getRow() + b.getCol();
    }

    /**
     * Returns a simply formated flight with only the most relevant information
     * 
     * @param flight
     * @return flight price, departing at, arriving at
     * @author rengotap
     */
    private String displayFlightSimple(Flight flight) { // TODO: Account for layovers
        String format = "Price: $" + flight.getCost() + " | Departing at: "
                + flight.getDepartureTime() + " | Arriving at: " + flight.getArrivalTime();
        return format;
    }

    /**
     * Returns a nicely formated flight with all relevant information
     * 
     * @param flight
     * @return flight multi line form
     * @author rengotap
     */
    private String displayFlightFull(Flight flight) { // TODO: Make this display properly
        String format = "Price: $" + flight.getCost() + " | Departing at: "
                + flight.getDepartureTime() + " | Arriving at: " + flight.getArrivalTime();
        return format;
    }

    /**
     * Returns a simply formated hotel with only the most relevant information
     * 
     * @param hotel
     * @return Hotel single line: PRICE, BEDS, COMPANY, STARS
     * @author rengotap
     */
    private String displayHotelSimple(Hotel hotel) {
        String format = "Price: " + ANSI_CYAN + "$" + hotel.getCost() 
            + ANSI_RESET + " | Rooms Available: " + ANSI_CYAN + hotel.getNumRooms() 
            + ANSI_RESET + " | Company: " + ANSI_CYAN + hotel.getCompany()  
            + ANSI_RESET + " | Rating: " + toStars(hotel.getRating());
        return format;
    }

    /**
     * Returns a nicely formated hotel with all relevant information
     * 
     * @param hotel
     * @return Hotel string location, company, nl, price, stars, nl, beds
     * @author rengotap
     */
    private String displayHotelFull(Hotel hotel) {
        String format = '\n' + ANSI_WHITE_BG + ANSI_BLACK + hotel.getCompany().toUpperCase() 
             + " at " + hotel.getLocation().toUpperCase() + ANSI_RESET + '\n' + "Price: "
             + ANSI_CYAN + "$" + hotel.getCost() + ANSI_RESET + '\n' + "Rating: " 
             + toStars(hotel.getRating()) + ANSI_CYAN + " (" + hotel.getRating() 
             + ")" + ANSI_RESET + '\n' + "Available Rooms: " + ANSI_CYAN + hotel.getNumRooms() 
             + ANSI_RESET + '\n' + "Amenities:" + '\n' + ANSI_CYAN 
             + toBlock(hotel.getFeatures(), 3) + ANSI_RESET + '\n';
        return format;
    }

    /**
     * Displays a room
     * @param room
     * @return
     * @author rengotap
     */
    private String displayRoom(Room room) {
        String format = "Room: " + ANSI_CYAN + room.getRoomNum() 
        + ANSI_RESET + " | Price: " + ANSI_CYAN + "$" + room.getPrice() 
        + ANSI_RESET + " | Beds: " + ANSI_CYAN + room.getInfo() + ANSI_RESET;
        return format;
    }

    /**
     * Turns a double rating into a string of stars
     * @param rating
     * @return stars
     * @author rengotap
     */
    private String toStars(Double rating) {
        String stars = "";
        for(int i = 0; i < Math.round(rating); i++)
            stars = stars+"⭐";
        return stars;
    }

    /**
     * Prompts the user to choose an option
     * 
     * @param options entered string must be in
     * @return string user entered
     * @author jbytes1027
     */
    private String promptOptions(String prompt, List<String> options) {
        print(prompt + toString(options));

        while (true) {
            print("> ");

            String response = input.nextLine();

            for (String option : options) {
                if (option.equalsIgnoreCase(response)) {
                    return option;
                }
            }

            println("Invalid option");
        }
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
     * Prompts the user to input a number
     * 
     * @param prompt Query
     * @return user input
     * @author jbytes1027
     */
    private int promptNumber(String prompt) {
        return promptNumber(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
                + '\n' + "                  Flight & Hotel Booking Program");
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
     * Special version of menuNumbered that returns an array with both 
     * the string and its position in options.
     * 
     * 
     * Meant for menus of unknown length
     * @param prompt
     * @param options
     * @return Array with String at 0 and int at 1
     * @author rengotap
     */
    private String[] menuLong(String prompt, List<String> options) {
        String[] ret = new String[2];
        for (int i = 0; i < options.size(); i++) {
            println(String.format("%d. %s", i + 1, options.get(i)));
        }

        println("");

        int response = promptNumber(prompt, 1, options.size());
        ret[0] = options.get(response - 1);
        ret[1] = Integer.toString(response - 1);  // slight spaghetti
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
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + currUser.getUsername() + "'s User Profile" + ANSI_RESET);
            println(String.format("""
                    Username: %s
                    Name: %s %s
                    Email: %s
                    """, currUser.getUsername(), currUser.getRegisteredPerson().getFirstName(),
                    currUser.getRegisteredPerson().getLastName(), currUser.getEmail()));

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
            if (input.equals(userManager.getCurrentUser().getPassword())) { // current password correct
                return true;
            } else if (input.equals("q")) { // user quits
                return false;
            } else {
                println("Incorrect password. Please try again"); // wrong choice
            }
        }
    }

    /**
     * Menu that manages the user's passports
     * 
     * @author jbytes1027
     */
    private void menuManagePassports() {
        while (true) {
            var passports = userManager.getCurrentUser().getTravelers();
            if (passports.size() > 0) {
                for (String string : passportsToStringTable(passports)) {
                    println(string);
                }
            } else {
                println(ANSI_RED + "No passports on file" + ANSI_RESET);
            }
            println("");

            final String OPTION_ADD = "Add passport";
            final String OPTION_REMOVE = "Remove passport";
            final String OPTION_BACK = "Back";

            List<String> options = new ArrayList<String>();

            options.add(OPTION_ADD);
            if (userManager.getCurrentUser().getTravelers().size() != 0) {
                options.add(OPTION_REMOVE);
            }
            options.add(OPTION_BACK);

            String response = menuNumbered("Enter a Number", options);

            if (response.equals(OPTION_ADD)) {
                menuAddPassport();
            } else if (response.equals(OPTION_REMOVE)) {
                menuRemovePassport();
            } else if (response.equals(OPTION_BACK)) {
                return;
            }
        }

    }

    /**
     * Menu that removes a passport
     * 
     * @author jbytes1027
     */
    private void menuRemovePassport() {
        var passports = userManager.getCurrentUser().getTravelers();

        List<String> options = passportsToStringTable(passports);
        String choice = promptTable("Choose a passport to remove", options);

        for (Passport passport : userManager.getCurrentUser().getTravelers()) {
            String currRow = String.join("", toRow(passport)).replace(" ", "");
            String choiceRow = choice.replace(" ", "");
            if (currRow.equals(choiceRow)) {
                userManager.getCurrentUser().removeTraveler(passport);
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
    private void menuAddPassport() {
        Person newPerson = promptCreatePerson();
        String gender = promptString("Enter a gender");
        LocalDate birth = promptDate("Enter the date of birth");
        String number = promptString("Enter the passport number");
        LocalDate expiration = promptDate("Enter the passport expiration date");

        userManager.getCurrentUser()
                .addTraveler(new Passport(newPerson, birth, expiration, number, gender));

        println("Passport added");
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

        println("Created " + username);
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
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + "Your current flight preferences:" + ANSI_RESET);
            final String OPTION_HOMEPORT = "Home Airport Code: "
                    + userManager.getCurrentUser().getFPref().get(FlightFilter.AIRPORT_FROM);
            final String OPTION_COMPANY =
                    "Company: " + userManager.getCurrentUser().getFPref().get(FlightFilter.COMPANY);
            final String OPTION_TIME_DEPART = "Earliest Departure Time (HH:MM): " + userManager
                    .getCurrentUser().getFPref().get(FlightFilter.TIME_DEPART_EARLIEST);
            final String OPTION_TIME_ARRIVE = "Latest Arrival Time (HH:MM): "
                    + userManager.getCurrentUser().getFPref().get(FlightFilter.TIME_ARRIVE_LATEST);
            final String OPTION_PETS = "Traveling with Pets: "
                    + userManager.getCurrentUser().getFPref().get(FlightFilter.PETS_ALLOWED);
            final String OPTION_LAYOVER = "Layovers: "
                    + userManager.getCurrentUser().getFPref().get(FlightFilter.LAYOVERS);
            final String OPTION_BACK = "Back to User Menu";
            List<String> options = new ArrayList<String>();

            options.add(OPTION_HOMEPORT);
            options.add(OPTION_COMPANY);
            options.add(OPTION_TIME_DEPART);
            options.add(OPTION_TIME_ARRIVE);
            options.add(OPTION_PETS);
            options.add(OPTION_LAYOVER);
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
            } else if (response.equals(OPTION_PETS)) {
                userManager.getCurrentUser().getFPref().put(FlightFilter.PETS_ALLOWED,
                        Boolean.toString(promptYN("Will you be traveling with pets?")));
            } else if (response.equals(OPTION_LAYOVER)) {
                userManager.getCurrentUser().getFPref().put(FlightFilter.LAYOVERS,
                        Boolean.toString(promptYN("Are you willing to take a layover?")));
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
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + "Your current hotel preferences:" + ANSI_RESET);
            final String OPTION_COMPANY =
                    "Company: " + userManager.getCurrentUser().getHPref().get(HotelFilter.COMPANY);
            final String OPTION_PETS_ALLOWED = "Pet Preference: "
                    + userManager.getCurrentUser().getHPref().get(HotelFilter.PETS_ALLOWED);
            final String OPTION_BACK = "Back to User Menu";

            List<String> options = new ArrayList<String>();

            options.add(OPTION_COMPANY);
            options.add(OPTION_PETS_ALLOWED);
            options.add(OPTION_BACK);

            String response = menuNumbered("Enter a Number", options);
            if (response.equals(OPTION_COMPANY)) {
                userManager.getCurrentUser().getHPref().put(HotelFilter.COMPANY,
                        promptString("Enter a new company name:"));
            } else if (response.equals(OPTION_PETS_ALLOWED)) {
                userManager.getCurrentUser().getHPref().put(HotelFilter.PETS_ALLOWED,
                        Boolean.toString(promptYN("Will you be traveling with pets?")));
            } else if (response.equals(OPTION_BACK)) {
                return;
            }
        }
    }

    /**
     * UI for booking a flight
     * // TODO: Enable one way flights
     * @author rengotap
     */
    private void menuBookFlight() {
        User curr = userManager.getCurrentUser();
        String destination;
        String home;
        LocalDate depart;
        LocalDate ret;
        String timeEarly;
        String timeLate;
        boolean layover;
        String company;
        boolean pets;

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
                timeEarly =
                        promptString("What is the earliest time you would be willing to leave?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_DEPART_EARLIEST,
                            timeEarly);
                }
            }

            if (hasPref(curr.getFPref().get(FlightFilter.TIME_ARRIVE_LATEST))) {
                timeLate = curr.getFPref().get(FlightFilter.TIME_ARRIVE_LATEST);
            } else {
                timeLate = promptString("What is the latest time you would be willing to arrive?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_ARRIVE_LATEST,
                            timeLate);
                }
            }

            if (hasPref(curr.getFPref().get(FlightFilter.LAYOVERS))) {
                layover = Boolean.parseBoolean(curr.getFPref().get(FlightFilter.LAYOVERS));
            } else {
                layover = promptYN("Would you be willing to take a layover flight?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.LAYOVERS,
                            Boolean.toString(layover));
                }
            }

        } else {
            home = promptString("What is your home airport?");
            timeEarly = promptString("What is the earliest time you would be willing to leave?");
            timeLate = promptString("What is the latest time you would be willing to leave?");
            layover = promptYN("Would you be willing to take a layover flight?");
        }

        depart = promptDate("Choose a departure date");
        ret = promptDate("Chose a return date");

        if (userManager.isAnyoneLoggedIn()) {
            if (hasPref(curr.getFPref().get(FlightFilter.COMPANY))) {
                company = curr.getFPref().get(FlightFilter.COMPANY);
            } else {
                company = promptString("What company would you like to book with?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.COMPANY, company);
                }
            }

            if (hasPref(curr.getFPref().get(FlightFilter.PETS_ALLOWED))) {
                pets = Boolean.parseBoolean(curr.getFPref().get(FlightFilter.PETS_ALLOWED));
            } else {
                pets = promptYN("Will you be traveling with any pets?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.PETS_ALLOWED,
                            Boolean.toString(pets));
                }

            }
        } else {
            company = promptString("What company would you like to book with?");
            pets = promptYN("Will you be traveling with any pets?");
        }

        boolean confirmParam = false;
        while (!confirmParam) {
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + "Please confirm your search parameters" + ANSI_RESET);
            final String OPT_DEST = "Destination: " + ANSI_CYAN + destination + ANSI_RESET;
            final String OPT_HOME = "Departing From:" + ANSI_CYAN + home + ANSI_RESET;
            final String OPT_START = "Departure Date: " + ANSI_CYAN + toString(depart) + ANSI_RESET;
            final String OPT_END = "Return Date: " + ANSI_CYAN + toString(ret) + ANSI_RESET;
            final String OPT_TIME_EARLY = "Earliest Time: " + ANSI_CYAN + timeEarly + ANSI_RESET;
            final String OPT_TIME_LATE = "Latest Time: " + ANSI_CYAN + timeLate + ANSI_RESET;
            final String OPT_LAYOVER =
                    "Layovers: " + ANSI_CYAN + Boolean.toString(layover) + ANSI_RESET;
            final String OPT_COMPANY = "Airline: " + ANSI_CYAN + company + ANSI_RESET;
            final String OPT_PETS = "Pets: " + ANSI_CYAN + Boolean.toString(pets) + ANSI_RESET;
            final String OPT_CONFIRM = ANSI_GREEN + "Confirm & Search" + ANSI_RESET;

            List<String> options = new ArrayList<String>();
            options.add(OPT_DEST);
            options.add(OPT_HOME);
            options.add(OPT_START);
            options.add(OPT_END);
            options.add(OPT_TIME_EARLY);
            options.add(OPT_TIME_LATE);
            options.add(OPT_LAYOVER);
            options.add(OPT_COMPANY);
            options.add(OPT_PETS);
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
                ret = promptDate("Enter a new return date");
            } else if (response.equals(OPT_TIME_EARLY)) {
                timeEarly = promptString("Enter the earliest time you would be willing to leave");
            } else if (response.equals(OPT_TIME_LATE)) {
                timeLate = promptString("Enter the latest time you would be willing to leave");
            } else if (response.equals(OPT_LAYOVER)) {
                layover = promptYN("Would you be willing to take a layover flight?");
            } else if (response.equals(OPT_COMPANY)) {
                company = promptString("Enter a new airline");
            } else if (response.equals(OPT_PETS)) {
                pets = promptYN("Are you traveling with pets?");
            } else if (response.equals(OPT_CONFIRM)) {
                confirmParam = true;
            }
        }
        System.out.println("Searching for your perfect flight...");
        SearchPreferences queryPrefs = new SearchPreferences();
        var queryFlightPrefs = queryPrefs.getFPref();
        queryFlightPrefs.put(FlightFilter.AIRPORT_TO, destination);
        queryFlightPrefs.put(FlightFilter.AIRPORT_FROM, home);
        queryFlightPrefs.put(FlightFilter.DATE_DEPART_EARLIEST, toString(depart));
        queryFlightPrefs.put(FlightFilter.DATE_ARRIVE_LATEST, toString(ret));
        queryFlightPrefs.put(FlightFilter.TIME_DEPART_EARLIEST, timeEarly);
        queryFlightPrefs.put(FlightFilter.TIME_ARRIVE_LATEST, timeLate);
        queryFlightPrefs.put(FlightFilter.LAYOVERS, Boolean.toString(layover));
        queryFlightPrefs.put(FlightFilter.COMPANY, company);
        queryFlightPrefs.put(FlightFilter.PETS_ALLOWED, Boolean.toString(pets));

        List<FlightTrip> searchResults = SearchFlightTrips.execute(queryPrefs);
        flightResult(queryPrefs);
        println("                      Thank you for using...");
    }

    /**
     * Displays search results and allows user to purchase tickets
     * 
     * @param query search parameters
     * @author rengotap
     */
    private void flightResult(SearchPreferences query) {
        //List<Flight> results = SearchFlights.execute(data, query); // TODO: correct flight search
        List<Flight> results = data.getFlights();
        println("Here are the best results we could find: " + '\n'
                + "Unsatisfied with your results? Try changing your search parameters!" + '\n');
        if (!results.isEmpty()) {
            List<String> options = new ArrayList<String>();
            int numDisplay = 4; // will show up to 4 results
            if(results.size() < 4)
                numDisplay = results.size();

            for (int i = 0; i < numDisplay; i++) {
                options.add(displayFlightSimple(results.get(i)));
            }
            final String OPT_BACK = "Return to main menu";
            options.add(OPT_BACK);
            while (true) {
                println(ANSI_BLACK + ANSI_WHITE_BG + "Select a flight for more information" + ANSI_RESET);
                String[] response = menuLong("Enter a Number", options);
                if(response[0].equals(OPT_BACK)) {
                    return;
                } else {
                    if (investigateFlight(results.get(Integer.parseInt(response[1]))))
                        return;
                }
            }
        } else {
            println(ANSI_RED + "Unable to find any matching results." + ANSI_RESET);
        }
    }

    /**
     * Provides more information about a Flight, and allows the user to book it
     * 
     * @param flight
     * @return returns true if flight was booked
     * @author rengotap
     */
    private boolean investigateFlight(Flight flight) {
        boolean unbooked = true;
        println(displayFlightFull(flight));
        while (unbooked) {
            if (promptYN("Book seats on this flight?")) {
                // TODO: interface with booking agent
                int bookSeats = promptNumber("How many seats would you like to book?", 0, flight.getNumSeats());
                int priceTotal = 0;
                for (int i = 0; i < bookSeats; i++) {
                    // Print available seats (maybe some sort of grid?)
                    // Pick seat number
                }
                if (promptYN("Book " + bookSeats + " seats for $" + priceTotal + "?")) {
                    // Book seats
                    return true;
                }
            }
        }
        return false; // go back to results
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
        boolean pets;

        location = promptString("Where would you like to make a reservation?");
        start = promptDate("When would you like to start your reservation?");
        end = promptDate("When would you like to end your reservation?");

        if (userManager.isAnyoneLoggedIn()) {
            if (hasPref(curr.getHPref().get(HotelFilter.COMPANY))) {
                company = curr.getHPref().get(HotelFilter.COMPANY);
            } else {
                company = promptString("What company would you like to book with?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getHPref().put(HotelFilter.COMPANY, company);
                }
            }
            if (hasPref(curr.getHPref().get(HotelFilter.PETS_ALLOWED))) {
                pets = Boolean.parseBoolean(curr.getHPref().get(HotelFilter.PETS_ALLOWED));
            } else {
                pets = promptYN("Will you be traveling with any pets?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getHPref().put(HotelFilter.PETS_ALLOWED,
                            Boolean.toString(pets));
                }
            }
        } else {
            company = promptString("What company would you like to book with?");
            pets = promptYN("Will you be traveling with any pets?");
        }

        boolean confirmParam = false;
        while (!confirmParam) {
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + "Please confirm your search parameters" + ANSI_RESET);
            final String OPT_LOCATION = "Location: " + ANSI_CYAN + location + ANSI_RESET;
            final String OPT_START = "Start Date: " + ANSI_CYAN + toString(start) + ANSI_RESET;
            final String OPT_END = "End Date: " + ANSI_CYAN + toString(end) + ANSI_RESET;
            final String OPT_COMPANY = "Company: " + ANSI_CYAN + company + ANSI_RESET;
            final String OPT_PETS = "Pets: " + ANSI_CYAN + Boolean.toString(pets) + ANSI_RESET;
            final String OPT_CONFIRM = ANSI_GREEN + "Confirm & Search" + ANSI_RESET;

            List<String> options = new ArrayList<String>();
            options.add(OPT_LOCATION);
            options.add(OPT_START);
            options.add(OPT_END);
            options.add(OPT_COMPANY);
            options.add(OPT_PETS);
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
            } else if (response.equals(OPT_PETS)) {
                pets = promptYN("Are you traveling with pets?");
            }
        }
        System.out.println("Searching for your perfect hotel...");
        EnumMap<HotelFilter, String> query = new EnumMap<>(HotelFilter.class);
        query.put(HotelFilter.LOCATION, location);
        query.put(HotelFilter.COMPANY, company);
        query.put(HotelFilter.DATE_START, toString(start));
        query.put(HotelFilter.DATE_END, toString(end));
        query.put(HotelFilter.PETS_ALLOWED, Boolean.toString(pets));

        // SearchHotels.execute(data, query);
        hotelResult(query);
        // This is intentional
        println("                      Thank you for using...");
    }

    /**
     * Displays search results and allows user to purchase tickets
     * 
     * @param query search parameters
     * @author rengotap
     */
    private void hotelResult(EnumMap<HotelFilter, String> query) {
        // List<Hotel> results = SearchHotels.execute(data, query); // TODO: take from hotel search
        List<Hotel> results = data.getHotels(); // Temporary stand in
        println("Here are the best results we could find: " + '\n'
                + "Unsatisfied with your results? Try changing your search parameters!" + '\n');
        // Assuming that these are the top.. 3?
        if (!results.isEmpty()) {
            List<String> options = new ArrayList<String>();
            int numDisplay = 4; // will show up to 4 results
            if(results.size() < 4)
                numDisplay = results.size();

            for (int i = 0; i < numDisplay; i++) {
                options.add(displayHotelSimple(results.get(i)));
            }
            final String OPT_BACK = "Return to main menu";
            options.add(OPT_BACK);
            while (true) {
                println(ANSI_BLACK + ANSI_WHITE_BG + "Select a hotel for more information" + ANSI_RESET);
                String[] response = menuLong("Enter a Number", options);
                if(response[0].equals(OPT_BACK)) {
                    return;
                } else {
                    if (investigateHotel(results.get(Integer.parseInt(response[1]))))
                        return;
                }
            }
        } else {
            println(ANSI_RED + "Unable to find any matching results." + ANSI_RESET);
        }
    }

    /**
     * Provides more information about a hotel, and allows the user to book it
     * 
     * @param hotel
     * @return returns true if hotel was booked
     * @author rengotap
     */
    private boolean investigateHotel(Hotel hotel) {
        println(displayHotelFull(hotel));
        if (promptYN("Book this hotel?")) {
            if (menuPickRoom(hotel))
                return true;
        }
        return false; // go back to results
    }

    /**
     * Menu for picking a hotel room
     * @param hotel
     * @return if a room was booked
     * @author rengotap
     */
    private boolean menuPickRoom(Hotel hotel) {
        //List<Bookable> rooms = hotel.
        List<Room> rooms = new ArrayList<Room>();  // TODO: extract rooms from hotel
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());


        List<String> options = new ArrayList<String>();
        for (int i = 0; i < rooms.size(); i++) {
            options.add(displayRoom(rooms.get(i)));
        }
        final String OPT_BACK = "Go back";
        options.add(OPT_BACK);

        while (true) {
            println('\n' + ANSI_BLACK + ANSI_WHITE_BG + "Select a room to book" + ANSI_RESET);
            String[] response = menuLong("Enter a Number", options);
            if(response[0].equals(OPT_BACK)) {
                return false;
            } else {
                if (promptYN("Book this room?")) {
                    // bookingAgent.bookListing(room, userManager.getCurrentUser()); // LET ME BOOK DAMN YOU
                    //TODO: use booking manager to book the room HERE
                    return true;
                }
            }
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
     * UI for editing (deleting really) booking history
     * 
     * @author rengotap
     */
    private void menuEditBooking() {
        while (true) {

            final String OPT_PRINT = "Print Booking";
            final String OPT_CANCEL = "Cancel Booking";
            final String OPT_BACK = "Back";

            List<String> options = new ArrayList<String>();

            if(userManager.isAnyoneLoggedIn() && !userManager.getCurrentUser().getBookingHistory().isEmpty()) {
                List<Bookable> bookings = userManager.getCurrentUser().getBookingHistory();
                println('\n' + ANSI_WHITE_BG + ANSI_BLACK + "Your Bookings:" + ANSI_RESET);
                for (int i = 0; i < bookings.size(); i++) {
                    println(toString(bookings.get(i)));
                }
                options.add(OPT_PRINT);
                options.add(OPT_CANCEL);
            } else {
                println('\n' + ANSI_RED + "No bookings on file" + ANSI_RESET);                
            }
            options.add(OPT_BACK);
            println("");
            String response = menuNumbered("Enter a Number", options);

            if (response.equals(OPT_PRINT)) {
                MenuPrintBooking();
            } else if (response.equals(OPT_CANCEL)) {
                MenuCancelBooking();
            } else if (response.equals(OPT_BACK)) {
                return;
            }
        }
    }

    /**
     * Cancels bookings
     * @author rengotap
     */
    private void MenuCancelBooking() {
        List<Bookable> bookings = userManager.getCurrentUser().getBookingHistory();
        List<String> options = new ArrayList<String>();
        for (int i = 0; i < bookings.size(); i++) { // should add every booking as an option
            options.add(toString(bookings.get(i)));
        }
        final String OPTIONS_BACK = "Return to main menu";
        options.add(OPTIONS_BACK); // at position size+1

        String[] response = menuLong("Choose a booking to cancel, or enter '" + options.size() + "' to go back", options);
        if(response[0].equals(OPTIONS_BACK)) {
            return;
        } else if (promptYN("Are you sure you want to cancel this booking?")) {
            userManager.getCurrentUser().removeBooking(Integer.parseInt(response[1]));
            println(ANSI_RED + "Booking canceled." + ANSI_RESET + '\n' +"Your payment has been refunded.");
        }

    }

    /**
     * The user now prints their itinerary for both of their flights, 
     * and the details for their hotel reservation.
     * Printing means creating a beautifully formatted text file.
     * @author rengotap
     */
    private void MenuPrintBooking() {
        List<Bookable> bookings = userManager.getCurrentUser().getBookingHistory();
        List<String> options = new ArrayList<String>();
        for (int i = 0; i < bookings.size(); i++) { // should add every booking as an option
            options.add(toString(bookings.get(i)));
        }
        final String OPTIONS_BACK = "Return to main menu";
        options.add(OPTIONS_BACK); // at position size+1

        String[] response = menuLong("Choose a booking to export, or enter " + options.size() + " to go back", options);
        if(response[0].equals(OPTIONS_BACK)) {
            return;
        } else {
            userManager.getCurrentUser().getBookingHistory().get(Integer.parseInt(response[1]));
            println(ANSI_YELLOW + "ERROR: Exporting to file not yet supported" + ANSI_RESET);  // TODO: Export Bookable as text file
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
        System.exit(0);
    }
}
