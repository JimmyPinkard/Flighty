import database.Data;
import model.users.User;
import model.users.info.Person;
import controller.UserManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Flighty {
    private Scanner input;
    private UserManager userManager;
    private Data data;

    public static void main(final String[] args) {
        Flighty app = new Flighty();
        app.mainMenu();
    }

    public Flighty() {
        data = Data.getInstance();
        input = new Scanner(System.in);
        userManager = new UserManager(data);
    }

    public String promptString(String prompt) {
        print(prompt + "\n> ");
        String response = input.nextLine();

        return response;
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
    public String promptOptions(String prompt, List<String> options) {
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
    public int promptNumber(String prompt, int from, int to) {
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
    private String numberedMenu(String prompt, List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            println(String.format("%d. %s", i + 1, options.get(i)));
        }

        println("");

        int response = promptNumber(prompt, 1, options.size());
        return options.get(response - 1);
    }

    public void mainMenu() {
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
            String response = numberedMenu("Enter a Number", options);

            if (response == OPTION_EXIT) {
                exit();
            } else if (response == OPTION_CREATE_USER) {
                createUserMenu();
            } else if (response == OPTION_LOGIN) {
                loginUserMenu();
            } else if (response == OPTION_LOGOUT) {
                userManager.logoutCurrent();
            } else if (response == OPTION_MANAGE_USER) {
                manageCurrentUserMenu();
            }
        }
    }

    public void manageCurrentUserMenu() {
        while (true) {
            final String OPTION_CHANGE_EMAIL = "Change Email";
            final String OPTION_CHANGE_PASSWORD = "Change password";
            final String OPTION_CHANGE_SEARCH_PREFERNECES = "Change search defaults";
            final String OPTION_DELETE_USER =
                    "Delete " + userManager.getCurrentUser().getUsername();
            final String OPTION_BACK = "Back to Main Menu";

            List<String> options = new ArrayList<String>();

            options.add(OPTION_CHANGE_EMAIL);
            options.add(OPTION_CHANGE_PASSWORD);
            options.add(OPTION_CHANGE_SEARCH_PREFERNECES);
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
            String response = numberedMenu("Enter a Number", options);

            if (response.equals(OPTION_CHANGE_EMAIL)) {
                String email = promptString("Enter a new email");
                userManager.getCurrentUser().setEmail(email);
            } else if (response.equals(OPTION_CHANGE_PASSWORD)) {
                String password = promptString("Enter a new password");
                userManager.getCurrentUser().setPassword(password);
            } else if (response.equals(OPTION_CHANGE_SEARCH_PREFERNECES)) {
                // TODO
            } else if (response.equals(OPTION_DELETE_USER)) {
                userManager.unregisterUser(userManager.getCurrentUser());
                return;
            } else if (response.equals(OPTION_BACK)) {
                return;
            }
        }
    }

    public void loginUserMenu() {
        String username = promptString("Enter a username:");
        if (!userManager.userExists(username)) {
            println("Not a registered user");
            return;
        }

        String password = promptString("Enter a password:");
        if (!userManager.credentialsCorrect(username, password)) {
            println("Wrong credentials");
            return;
        }

        userManager.login(username, password);
        println("Logged in");
    }

    public void createUserMenu() {
        String firstName = promptString("Enter a first name");
        String lastName = promptString("Enter a last name");
        String username = promptString("Enter a username:");
        // TODO: check if user already exists
        String password = promptString("Enter a password:");

        Person newPerson = new Person(firstName, lastName);
        User newUser = new User(newPerson, username, password);
        userManager.registerUser(newUser);

        userManager.logoutCurrent();
        userManager.login(username, password);

        println("Created " + username);
    }

    public void exit() {
        println("Exiting...");
        System.exit(0);
    }
}
