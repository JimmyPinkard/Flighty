import database.Data;
import model.users.User;
import model.users.info.Passport;
import model.users.info.Person;
import controller.UserManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import search.filters.FlightFilter;
import search.filters.HotelFilter;

public class Flighty {
    private Scanner input;
    private UserManager userManager;
    private Data data;

    public static void main(final String[] args) {
        Flighty app = new Flighty();
        app.menuMain();
    }

    public Flighty() {
        data = Data.getInstance();
        input = new Scanner(System.in);
        userManager = new UserManager(data);
    }

    public void start() {
        data.loadAll();
    }

    public void stop() {
        data.saveAll();
    }

    private String promptString(String prompt) {
        print(prompt + "\n> ");
        String response = input.nextLine();

        return response;
    }

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

    private String toString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MM/DD/YY"));
    }

    private String promptTable(String prompt, List<String> table) {
        String header = table.get(0);
        table.remove(0);

        println("   " + header);
        return menuNumbered(prompt, table);
    }

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

    private List<String> toRow(Passport passport) {
        List<String> row = new ArrayList<String>();

        row.add(passport.getPerson().getFirstName() + " " + passport.getPerson().getLastName());
        row.add(toString(passport.getDOB()));
        row.add(passport.getGender());
        row.add(toString(passport.getExpDate()));
        row.add(passport.getNumber());

        return row;
    }

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

    private int promptNumber(String prompt) {
        return promptNumber(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private void print(String string) {
        System.out.print(string);
    }

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

    private Person promptCreatePerson() {
        String firstName = promptString("Enter a first name");
        String lastName = promptString("Enter a last name");

        return new Person(firstName, lastName);
    }

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

        User newUser = new User(newPerson, username, password);  // TODO: Bookmark
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
            menuManageCurrentUser();
        }

    }

    /**
     * Changes user Flight prefrences
     * @author rengotap
     */
    private void menuChangeFPref() {
        println("Your current flight preferences:");
        final String OPTION_HOMEPORT = "Home Airport:" + userManager.getCurrentUser().getFPref().get(FlightFilter.AIRPORT);
        final String OPTION_COMPANY = "Company: " + userManager.getCurrentUser().getFPref().get(FlightFilter.COMPANY);
        final String OPTION_TIME_START = "Earliest Departure Time: " + userManager.getCurrentUser().getFPref().get(FlightFilter.TIME_START);
        final String OPTION_TIME_END = "Latest Departure Time: " + userManager.getCurrentUser().getFPref().get(FlightFilter.TIME_END);
        final String OPTION_PETS = "Pets allowed: " + userManager.getCurrentUser().getFPref().get(FlightFilter.PETS_ALLOWED);
        final String OPTION_LAYOVER = "Layover preference: " + userManager.getCurrentUser().getFPref().get(FlightFilter.FLIGHTS_LAYOVER);
        final String OPTION_BACK = "Back to User Menu";
        List<String> options = new ArrayList<String>();

        options.add(OPTION_HOMEPORT);
        options.add(OPTION_COMPANY);
        options.add(OPTION_TIME_START);
        options.add(OPTION_TIME_END);
        options.add(OPTION_PETS);
        options.add(OPTION_LAYOVER);
        options.add(OPTION_BACK);

        String response = menuNumbered("Enter a Number", options);
        if(response.equals(OPTION_HOMEPORT)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.AIRPORT, promptString("Enter a new Home Airport: "));
        } else if(response.equals(OPTION_COMPANY)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.COMPANY, promptString("Enter a new Company: "));
        } else if (response.equals(OPTION_TIME_START)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_START, promptString("Enter your earliest time: "));
        } else if (response.equals(OPTION_TIME_END)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.TIME_END, promptString("Enter your latest time: "));
        } else if (response.equals(OPTION_PETS)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.PETS_ALLOWED, promptString("Enter a pet preference: "));
        } else if (response.equals(OPTION_LAYOVER)) {
            userManager.getCurrentUser().getFPref().put(FlightFilter.FLIGHTS_LAYOVER, promptString("Enter a layover preference: "));
        } else if (response.equals(OPTION_BACK)) {
            menuManageCurrentUser();
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
            userManager.getCurrentUser().getHPref().put(HotelFilter.COMPANY, promptString("Enter a pet preference:"));
        } else if (response.equals(OPTION_BACK)) {
            menuManageCurrentUser();
        }
    }

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

    public void exit() {
        println("Exiting...");
        System.exit(0);
    }
}
