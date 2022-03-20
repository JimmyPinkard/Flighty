import database.Data;
import controller.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Flighty {
    private Scanner input;
    private UserManager userManager;
    private Data data;

    public static void main(final String[] args)
    {
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
        final String OPTION_FLIGHT = "Find a Flight";
        final String OPTION_HOTEL = "Find a Hotel";
        final String OPTION_BOOKINGS = "Manage Bookings";
        final String OPTION_MANAGE_USER = "Manage Users";
        final String OPTION_CREATE_USER = "Create Users";
        final String OPTION_LOGOUT = "Logout";
        final String OPTION_EXIT = "Exit";

        List<String> options = new ArrayList<String>();

        String currUserName = "";
        if (userManager.getCurrentUser() == null) {
            options.add(OPTION_CREATE_USER);
        } else {
            options.add(OPTION_MANAGE_USER);
            options.add(OPTION_LOGOUT);

            currUserName = userManager.getCurrentUser().getRegisteredPerson().getFirstName();
        }

        options.add(OPTION_FLIGHT);
        options.add(OPTION_HOTEL);
        options.add(OPTION_BOOKINGS);
        options.add(OPTION_EXIT);

        println("Welcome" + currUserName);
        String response = numberedMenu("Enter a Number", options);

        switch (response) {
            case OPTION_EXIT:
                exit();
                break;

            default:
                break;
        }
    }

    public void exit() {
        println("Exiting...");
        System.exit(0);
    }
}
