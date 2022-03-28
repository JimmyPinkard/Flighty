import database.Data;
import model.bookables.flight.Flight;
import model.bookables.hotel.Room;
import model.users.User;
import model.users.info.Passport;
import model.users.info.Person;
import controller.UserManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import search.SearchFlights;
import search.SearchHotels;
import search.filters.FlightFilter;
import search.filters.HotelFilter;
import model.bookables.hotel.Hotel;

/**
 * UI class
 */
public class Flighty {
    private Scanner input;
    private UserManager userManager;
    private Data data;

    /**
     * Main method for Flighty app
     * @param args
     */
    public static void main(final String[] args) {
        Flighty app = new Flighty();
        app.start();

        // EnumMap<FlightFilter, String> prefs = new EnumMap<FlightFilter,
        // String>(FlightFilter.class);
        // prefs.put(FlightFilter.AIRPORT_FROM, "arg1");
        // prefs.put(FlightFilter.AIRPORT_TO, "arg1");
        // List<Flight> f = SearchFlights.execute(app.data, prefs);
        // app.println("");
    }

    /**
     * Creates a new UI / Flighty instance
     */
    public Flighty() {
        data = Data.getInstance();
        input = new Scanner(System.in);
        userManager = new UserManager(data);

        checkData();  // Comment this out to run program with empty input data
    }

    /**
     * Loads all data from database
     */
    public void start() {
        data.loadAll();
        menuMain();
    }

    /**
     * Saves all data to database
     */
    public void stop() {
        data.saveAll();
    }

    /**
     * Checks to make sure data is all there, terminates if it isn't
     * For debugging
     * @author rengotap
     */
    public void checkData() {
        if(data.getFlights().isEmpty() || data.getHotels().isEmpty()) {
            println("FATAL: Input data is incomplete!");
            if (data.getFlights().isEmpty())
                println("DEBUG: Flight data is empty");
            if (data.getHotels().isEmpty())
                println("DEBUG: Hotel data is empty");
            exit();
        }
    }

    /**
     * Prompts the user for input
     * @param prompt query
     * @return user's string input
     */
    private String promptString(String prompt) {
        print(prompt + "\n> ");
        String response = input.nextLine();

        return response;
    }

    /**
     * Prompts the user for input
     * @param prompt query
     * @return user's date input
     */
    private LocalDate promptDate(String prompt) {
        println(prompt + " (MM/DD/YY)");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");
        String response;

        while (true) {
            print("> ");
            response = input.nextLine();
            try {
                LocalDate date = LocalDate.parse(response, formatter);
                return date;
            } catch (DateTimeParseException e) {
                println("Invalid date");
                continue;
            }
        }
    }

    /**
     * Converts a date into a string
     * @param date input date
     * @return converted string
     */
    private String toString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("M/d/y"));
    }

    /**
     * Prompts the user for a table
     * @param prompt
     * @param table
     * @return
     */
    private String promptTable(String prompt, List<String> table) {
        String header = table.get(0);
        table.remove(0);

        println("   " + header);
        return menuNumbered(prompt, table);
    }

    /**
     * Converts passports into a table of strings
     * @param passports
     * @return table
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
     * @param passport
     * @return
     */
    private List<String> toRow(Passport passport) {
        List<String> row = new ArrayList<String>();

        row.add(passport.getPerson().getFirstName() + " " + passport.getPerson().getLastName());
        row.add(toString(passport.getDOB()));
        row.add(passport.getGender());
        row.add(toString(passport.getExpDate()));
        row.add(passport.getNumber());

        return row;
    }

    /**
     * Turns a passport into a string
     * @param passport passport to convert
     * @return converted
     */
    private String toString(Passport passport) {
        return String.format("""
                NAME: %s %s
                GENDER: %s
                DOB: %s
                EXPIRATION: %s
                NUMBER: %s
                """, passport.getPerson().getFirstName(), passport.getPerson().getLastName(),
                passport.getGender(), toString(passport.getDOB()), toString(passport.getExpDate()),
                passport.getNumber());
    }

    /**
     * Turns the list of options into a string
     * @param options list of options
     * @return converted string
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
     * Returns a simply formated flight with only the most relevant information
     * @param flight
     * @return flight price, departing at, arriving at
     * @author rengotap
     */
    private String displayFlightSimple(Flight flight) {  //TODO: Account for layovers
        String format = "Price: $"+flight.getCost()+" | Departing at: "
            +flight.getDepartureTime()+" | Arriving at: "+flight.getArrivalTime();
        return format;
    }

    /**
     * Returns a nicely formated flight with all relevant information
     * @param flight
     * @return flight multi line form
     * @author rengotap
     */
    private String displayFlightFull(Flight flight) {  // TODO: Make this display properly
        String format = "";
        return format;
    }

    /**
     * Returns a simply formated hotel with only the most relevant information
     * @param hotel
     * @return Hotel single line: price, numbeds, stars
     * @author rengotap
     */
    private String displayHotelSimple(Hotel hotel) { //TODO: Make this display properly
        String format = "Price: $"+hotel.getCost()+" | beds: "+((Room)hotel.getOptions().get(0)).getBeds();
        return format;
    }

    /**
     * Returns a nicely formated hotel with all relevant information
     * @param hotel
     * @return Hotel string location, company, nl, price, stars, nl, beds
     * @author rengotap
     */
    private String displayHotelFull(Hotel hotel) {  // TODO: make this display properly
        String format = "Price: $"+hotel.getCost()+" | beds: "+((Room)hotel.getOptions().get(0)).getBeds();
        return format;
    }

    /**
     * Prompts the user to choose an option
     * 
     * @param options entered string must be in
     * @return string user entered
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
     * @param prompt Query
     * @return user input
     */
    private int promptNumber(String prompt) {
        return promptNumber(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Prompts the user to answer yes (y) or no (n)
     * @param prompt query
     * @return True = yes, false = no
     * @author rengotap
     */
    private boolean promptYN(String prompt) {
        println(prompt+" (y/n)");
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
     * @param string string to print
     */
    private void print(String string) {
        System.out.print(string);
    }

    /**
     * Prints a line
     * @param string line to print
     */
    private void println(String string) {
        System.out.println(string);
    }

    /**
     * Prints a cool logo
     * @author rengotap
     */
    private void printHeader() {   
        println("88888888888  88  88               88" +'\n'
        +"88           88  "+"''"+"               88            ,d"+'\n'
        +"88           88                   88            88"+'\n'
        +"88aaaaa      88  88   ,adPPYb,d8  88,dPPYba,  MM88MMM  8b       d8"+'\n'
        +"88'''''      88  88  a8'    `Y88  88P'    '8a   88     `8b     d8'"+'\n'
        +"88           88  88  8b       88  88       88   88      `8b   d8'"+'\n'
        +"88           88  88  '8a,   ,d88  88       88   88,      `8b,d8'"+'\n'
        +"88           88  88   `'YbbdP'Y8  88       88   'Y888      Y88'"+'\n'
        +"                      aa,    ,88                           d8'"+'\n'
        +"                       'Y8bbdP'                           d8'"+'\n'
        +"                  Flight & Hotel Booking Program");
    }

    /**
     * Creates a numbered menu and promps the user to choose an option
     * 
     * @param options options for the user to choose from
     * @return chosen option from options
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
     * Main menu
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
                currUserName = "";
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
            println("Welcome " + currUserName);
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
                userManager.logoutCurrent();
            } else if (response == OPTION_MANAGE_USER) {
                menuManageCurrentUser();
            }
        }
    }

    /**
     * Menu that deals with the current user
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
            println(String.format("""
                    NAME: %s %s
                    USERNAME: %s
                    EMAIL: %s
                    PASSWORD: %s
                    """, currUser.getRegisteredPerson().getFirstName(),
                    currUser.getRegisteredPerson().getLastName(), currUser.getUsername(),
                    currUser.getEmail(), currUser.getPassword()));

            println(currUser.getUsername() + " Preferences");
            String response = menuNumbered("Enter a Number", options);

            if (response.equals(OPTION_CHANGE_EMAIL)) {
                String email = promptString("Enter a new email");
                userManager.getCurrentUser().setEmail(email);
            } else if (response.equals(OPTION_CHANGE_PASSWORD)) {
                String password = promptString("Enter a new password");
                userManager.getCurrentUser().setPassword(password);
            } else if (response.equals(OPTION_CHANGE_SEARCH_PREFERNECES)) {
                menuChangePref();
            } else if (response.equals(OPTION_MANAGE_PASSPORTS)) {
                menuManagePassports();
            } else if (response.equals(OPTION_DELETE_USER)) {
                userManager.unregisterUser(userManager.getCurrentUser());
                return;
            } else if (response.equals(OPTION_BACK)) {
                return;
            }
        }
    }

    /**
     * Menu that manages the user's passports
     */
    private void menuManagePassports() {
        while (true) {
            var passports = userManager.getCurrentUser().getTravelers();
            if (passports.size() > 0) {
                for (String string : passportsToStringTable(passports)) {
                    println(string);
                }
            } else {
                println("No passports on file");
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
     * @return created person
     */
    private Person promptCreatePerson() {
        String firstName = promptString("Enter a first name");
        String lastName = promptString("Enter a last name");

        return new Person(firstName, lastName);
    }

    /**
     * Menu for creating a new user
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
        String password = promptString("Enter a password");

        User newUser = new User(newPerson, username, password);
        userManager.registerUser(newUser);

        userManager.logoutCurrent();
        userManager.login(username, password);

        println("Created " + username);
    }

    /**
     * User search preferences menu
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
     * @author rengotap
     */
    private void menuChangeFPref() {
        println("Your current flight preferences:");
        final String OPTION_HOMEPORT = "Home Airport:"
                + userManager.getCurrentUser().getFPref().get(FlightFilter.AIRPORT_FROM);
        final String OPTION_COMPANY = "Company: " + userManager.getCurrentUser().getFPref().get(FlightFilter.COMPANY);
        final String OPTION_TIME_DEPART = "Earliest Departure Time: "
                + userManager.getCurrentUser().getFPref().get(FlightFilter.TIME_EARLIEST);
        final String OPTION_TIME_ARRIVE = "Latest Departure Time: "
                + userManager.getCurrentUser().getFPref().get(FlightFilter.TIME_LATEST);
        final String OPTION_PETS = "Traveling with Pets: " + userManager.getCurrentUser().getFPref().get(FlightFilter.PETS_ALLOWED);
        final String OPTION_LAYOVER = "Layovers: " + userManager.getCurrentUser().getFPref().get(FlightFilter.FLIGHTS_LAYOVER);
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
        if(response.equals(OPTION_HOMEPORT)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.AIRPORT_FROM,
                    promptString("Enter a new Home Airport: "));
        } else if(response.equals(OPTION_COMPANY)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.COMPANY, promptString("Enter a new Company: "));
        } else if (response.equals(OPTION_TIME_DEPART)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_EARLIEST,
                    promptString("Enter your earliest time: "));
        } else if (response.equals(OPTION_TIME_ARRIVE)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_LATEST,
                    promptString("Enter your latest time: "));
        } else if (response.equals(OPTION_PETS)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.PETS_ALLOWED, Boolean.toString(promptYN("Will you be traveling with pets?")));
        } else if (response.equals(OPTION_LAYOVER)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.FLIGHTS_LAYOVER, Boolean.toString(promptYN("Are you willing to take a layover?")));
        } else if (response.equals(OPTION_BACK)) {
            return;
        }
    }

    /**
     * Changes user Hotel preferences
     * @author rengotap
     */
    private void menuChangeHPref() {
        println("Your current hotel preferences");
        final String OPTION_COMPANY = "Company: " + userManager.getCurrentUser().getHPref().get(HotelFilter.COMPANY);
        final String OPTION_PETS_ALLOWED = "Pet Preference: " + userManager.getCurrentUser().getHPref().get(HotelFilter.PETS_ALLOWED);
        final String OPTION_BACK = "Back to User Menu";

        List<String> options = new ArrayList<String>();

        options.add(OPTION_COMPANY);
        options.add(OPTION_PETS_ALLOWED);
        options.add(OPTION_BACK);

        String response = menuNumbered("Enter a Number", options);
        if (response.equals(OPTION_COMPANY)) {
            userManager.getCurrentUser().getHPref().put(HotelFilter.COMPANY, promptString("Enter a new company name:"));
        } else if (response.equals(OPTION_PETS_ALLOWED)) {
            userManager.getCurrentUser().getHPref().put(HotelFilter.PETS_ALLOWED, Boolean.toString(promptYN("Will you be traveling with pets?")));
        } else if (response.equals(OPTION_BACK)) {
            return;
        }
    }

    /**
     * UI for booking a flight
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
            if (hasPref(curr.getFPref().get(FlightFilter.AIRPORT_FROM))) {  // Check for user pref
                home = curr.getFPref().get(FlightFilter.AIRPORT_FROM);
            } else {
                home = promptString("What is your home airport?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.AIRPORT_FROM, home);
                }
            }

            if (hasPref(curr.getFPref().get(FlightFilter.TIME_EARLIEST))) {
                timeEarly = curr.getFPref().get(FlightFilter.TIME_EARLIEST);
            } else {
                timeEarly = promptString("What is the earliest time you would be willing to leave?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_EARLIEST, timeEarly);
                }
            }

            if (hasPref(curr.getFPref().get(FlightFilter.TIME_LATEST))) {
                timeLate = curr.getFPref().get(FlightFilter.TIME_LATEST);
            } else {
                timeLate = promptString("What is the latest time you would be willing to leave?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_LATEST, timeLate);
                }
            }

            if (hasPref(curr.getFPref().get(FlightFilter.FLIGHTS_LAYOVER))) {
                layover = Boolean.parseBoolean(curr.getFPref().get(FlightFilter.FLIGHTS_LAYOVER));
            } else {
                layover = promptYN("Would you be willing to take a layover flight?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getFPref().put(FlightFilter.FLIGHTS_LAYOVER, Boolean.toString(layover));
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
                    userManager.getCurrentUser().getFPref().put(FlightFilter.PETS_ALLOWED, Boolean.toString(pets));
                }

            }
        } else {
            company = promptString("What company would you like to book with?");
            pets = promptYN("Will you be traveling with any pets?");
        }

        boolean confirmParam = false;
        while(!confirmParam) {
            println("Please confirm your search parameters");
            final String OPT_DEST = "Destination: "+destination;
            final String OPT_HOME = "Departing From:"+home;
            final String OPT_START = "Departure Date: "+toString(depart);
            final String OPT_END = "Return Date: "+toString(ret);
            final String OPT_TIME_EARLY = "Earliest Time: "+timeEarly;
            final String OPT_TIME_LATE = "Latest Time: " + timeLate;
            final String OPT_LAYOVER = "Layovers: " +Boolean.toString(layover);
            final String OPT_COMPANY = "Airline: "+company;
            final String OPT_PETS = "Pets: "+Boolean.toString(pets);
            final String OPT_CONFIRM = "Confirm & Search";

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

            if(response.equals(OPT_CONFIRM)) {
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
        //TODO: actually book flight
        EnumMap<FlightFilter, String> query = new EnumMap<>(FlightFilter.class);
        query.put(FlightFilter.AIRPORT_TO, destination);
        query.put(FlightFilter.AIRPORT_FROM, home);
        query.put(FlightFilter.DATE_DEPART, toString(depart));
        query.put(FlightFilter.DATE_ARRIVE, toString(ret));
        query.put(FlightFilter.TIME_EARLIEST, timeEarly);
        query.put (FlightFilter.TIME_LATEST, timeLate);
        query.put(FlightFilter.FLIGHTS_LAYOVER, Boolean.toString(layover));
        query.put(FlightFilter.COMPANY, company);
        query.put(FlightFilter.PETS_ALLOWED, Boolean.toString(pets));

        flightResult(query);
        println("                      Thank you for using...");
    }

    /**
     * Displays search results and allows user to purchase tickets
     * @param query search parameters
     */
    private void flightResult(EnumMap<FlightFilter, String> query) {
        List<Flight> searchResults = SearchFlights.execute(data, query);
        println("Here are the best results we could find: " +
            '\n'+"Unsatisfied with your results? Try changing your search parameters!");
        // Assuming that these are the top.. 3?
        if (!searchResults.isEmpty()) {
            final String OPT_ONE = displayFlightSimple(searchResults.get(0));
            final String OPT_TWO = displayFlightSimple(searchResults.get(1));
            final String OPT_THREE = displayFlightSimple(searchResults.get(2));
            final String OPT_EXIT = "Return to main menu";
            List<String> options = new ArrayList<String>();
            options.add(OPT_ONE);
            options.add(OPT_TWO);
            options.add(OPT_THREE);
            options.add(OPT_EXIT);
            boolean chosen = false;
            while (!chosen) {
                String response = menuNumbered("Enter a Number", options);
                if (response.equals(OPT_EXIT)) {
                    chosen = true;
                    return;
                } else if (response.equals(OPT_ONE)) {
                    if (investigateFlight(searchResults.get(0)))
                        chosen = true;
                } else if (response.equals(OPT_TWO)) {
                    if (investigateFlight(searchResults.get(1))) 
                        chosen = true;
                } else if (response.equals(OPT_THREE)) {
                    if (investigateFlight(searchResults.get(2)))
                        chosen = true;
                }
            }
        } else {
            println("Unable to find any matching results.");
        }
    }

    /**
     * Provides more information about a Flight, and allows the user to book it
     * @param flight
     * @return returns true if flight was booked
     */
    private boolean investigateFlight(Flight flight) {
        boolean unbooked = true;
        displayFlightFull(flight);
        while (unbooked) {
            if (promptYN("Book seats on this flight?")) {
                //TODO: interface with booking agent
                int bookSeats = promptNumber("How many seats would you like to book?", 0, 999); // TODO: change this number to max available
                int priceTotal = 0;
                for(int i = 0; i < bookSeats; i++) {
                    // Print available seats
                    // Pick seat number
                }
                if (promptYN("Book " +bookSeats+" seats for $"+priceTotal+"?")) {
                    // Book seats
                    return true;
                }
            }
        }
        return false; // go back to results
    }

    /**
     * UI for booking a hotel
     * @author rengotap
     */
    private void menuBookHotel() {
        //Check User prefrences
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
            if(hasPref(curr.getHPref().get(HotelFilter.COMPANY))) {
                company = curr.getHPref().get(HotelFilter.COMPANY);
            } else {
                company = promptString("What company would you like to book with?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getHPref().put(HotelFilter.COMPANY, company);
                }
            }
            if(hasPref(curr.getHPref().get(HotelFilter.PETS_ALLOWED))) {
                pets = Boolean.parseBoolean(curr.getHPref().get(HotelFilter.PETS_ALLOWED));
            } else {
                pets = promptYN("Will you be traveling with any pets?");
                if (promptYN("Would you like to save this as a default option?")) {
                    println("Setting as user default");
                    userManager.getCurrentUser().getHPref().put(HotelFilter.PETS_ALLOWED, Boolean.toString(pets));
                }
            }
        } else {
            company = promptString("What company would you like to book with?");
            pets = promptYN("Will you be traveling with any pets?");
        }

        boolean confirmParam = false;
        while(!confirmParam) {
            println("Please confirm your search parameters");
            final String OPT_LOCATION = "Location: "+location;
            final String OPT_START = "Start Date: "+toString(start);
            final String OPT_END = "End Date: "+toString(end);
            final String OPT_COMPANY = "Company: "+company;
            final String OPT_PETS = "Pets: "+Boolean.toString(pets);
            final String OPT_CONFIRM = "Confirm & Search";

            List<String> options = new ArrayList<String>();
            options.add(OPT_LOCATION);
            options.add(OPT_START);
            options.add(OPT_END);
            options.add(OPT_COMPANY);
            options.add(OPT_PETS);
            options.add(OPT_CONFIRM);

            String response = menuNumbered("Enter a Number", options);

            if(response.equals(OPT_CONFIRM)) {
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
            query.put(HotelFilter.TIME_DEPART, toString(start));
            query.put(HotelFilter.TIME_END,  toString(end));
            query.put(HotelFilter.PETS_ALLOWED, Boolean.toString(pets));

            //SearchHotels.execute(data, query);
            hotelResult(query);
            println("                      Thank you for using...");
    }

    /**
     * Displays search results and allows user to purchase tickets
     * @param query search parameters
     */
    private void hotelResult(EnumMap<HotelFilter,String> query) {
        //List<Hotel> searchResults = SearchHotels.execute(data, query); //  TODO: correct hotel search
        List<Hotel> searchResults = data.getHotels();  // Temporary stand in
        println("Here are the best results we could find: " +
            '\n'+"Unsatisfied with your results? Try changing your search parameters!");
        // Assuming that these are the top.. 3?
        if(!searchResults.isEmpty()) {
            final String OPT_ONE = displayHotelSimple(searchResults.get(0));
            final String OPT_TWO = displayHotelSimple(searchResults.get(1));
            final String OPT_THREE = displayHotelSimple(searchResults.get(2));
            final String OPT_EXIT = "Return to main menu";
            List<String> options = new ArrayList<String>();
            options.add(OPT_ONE);
            options.add(OPT_TWO);
            options.add(OPT_THREE);
            options.add(OPT_EXIT);
            boolean chosen = false;
            while (!chosen) {
                String response = menuNumbered("Enter a Number", options);
                if (response.equals(OPT_EXIT)) {
                    chosen = true;
                    return;
                } else if (response.equals(OPT_ONE)) {
                    if (investigateHotel(searchResults.get(0)))
                        chosen = true;
                } else if (response.equals(OPT_TWO)) {
                    if (investigateHotel(searchResults.get(1))) 
                        chosen = true;
                } else if (response.equals(OPT_THREE)) {
                    if (investigateHotel(searchResults.get(2)))
                        chosen = true;
                }
            }
        } else {
            println("Unable to find any matching results.");
        }
    }

    /**
     * Provides more information about a hotel, and allows the user to book it
     * @param hotel
     * @return returns true if hotel was booked
     */
    private boolean investigateHotel(Hotel hotel) {
        displayHotelFull(hotel);
        if (promptYN("Book this hotel?")) {
            //TODO: interface with booking agent
            return true;
        }
        return false; // go back to results

    }

    /**
     * Helper method to determine if user has a preference 
     * @param in pref to test
     * @return if pref = none
     */
    private boolean hasPref(String in) {
        if (in.equals("none"))
            return false;
        return true;
    }

    /**
     * UI for editing (deleting really) booking history
     */
    private void menuEditBooking() {
        if(userManager.isAnyoneLoggedIn() && 
            !userManager.getCurrentUser().getBookingHistory().isEmpty()) {
        
            User curr = userManager.getCurrentUser();

            List<String> options = new ArrayList<String>();
            for (int i = 1; i < curr.getBookingHistory().size(); i++) { // should add every booking as an option
                options.add(curr.getBookingHistory().get(i).toString());
            }
            final String OPTIONS_EXIT = "Return to main menu";
            options.add(OPTIONS_EXIT);
        
            String response = menuNumbered("Enter a Number", options);
            if (response.equals(options.get(options.size()-1))) {
                return;
            } else {
                if (promptYN("Are you sure you want to delete this booking?")) {
                    userManager.getCurrentUser().removeBooking(curr.getBookingHistory().get(Integer.parseInt(response)));
                    println("Booking removed, your payment has been refunded.");
                }
            }
        } else {
            println("No bookings to modify.");
        }
    }

    /**
     * Turns a table into an ArrayList
     * @param table
     * @return
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
     */
    public void exit() {
        println("Exiting...");
        System.exit(0);
    }
}
