package controller;

import database.Data;
import model.users.User;
import java.util.List;

/**
 * Linked list that handles user related tasks
 * For sanity, users are currently beind stored: [     ]
 * 
 * @author rengotap
 */
public class UserManager {

    private User loggedIn; // save data here
    private User currentUser; // curr user in LL
    private List<User> users; // LL

    /**
     * Creates a new Controllor.UserManager
     * @param data
     */
    public UserManager(Data data) {
        // ====PSEUDOCODE====

        // ---STARTUP(list)---
        // add all users to the user list, not sure why but the UML says so, easy enough

        // ---STARTUP(temp)---
        // check to see if user "temp" exists
        // if it does (from a bad exit), wipe and create a new one.
        // temp will be a guest account 

        // ---LOGIN/SEARCH---
        // if nobody is logged in create a new guest account to store prefrences
        // If someone logs in Login() and set loggedIn
        // wipe "temp" and write to the new account

    }

    /**
     * Checks to see if user input matches login and password information
     * @param username saved username
     * @param password saved password
     * @return login accepted (T/F)
     */
    public boolean login(String username, String password) {
        // Checking to see if the user can access the data
        if (saveDataExists(username))
            return false;
        if (!passwordCorrect(username, password))
            return false;
        // Accessing the user data

        // TODO: actually set the user data

        // Sets loggedIn as the logged in user
        loggedIn = new User(person, username, password);
        return true;
    }

    /**
     * Registers the user
     * @param user user to register
     */
    public void registerUser(User user) {
        // TODO: registerUser
    }

    /**
     * Unregisters the user
     * @param user user to remove
     */
    public void unregisterUser(User user) {
        // TODO: unregisterUser
    }

    /**
     * Gets the user that is currently logged in
     * @return currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks to see if the user exists in the database
     * @param username
     * @return a file for user data exists(T/F)
     */
    public boolean saveDataExists(String username) {
        //TODO: check save data
        return false;
    }

    /**
     * Checks login credentials before allowing the user to be created
     * @param username user's name
     * @param password user's secret password
     * @return username password combo is correct
     */
    public boolean passwordCorrect(String username, String password) {
        //TODO: read password
        return false;
    }

}
