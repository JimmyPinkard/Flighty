package controller;

import database.Data;
import model.users.User;
import java.util.List;
import java.io.File;

/**
 * List that handles user related tasks
 * For sanity, users are currently beind stored: [./database/userdata]
 * 
 * @author rengotap
 */
public class UserManager {

    private User currentUser; // User we're reading and writing from
    private List<User> users; // list of users

    /**
     * Creates a new Controllor.UserManager
     * @param data
     */
    public UserManager(Data data) {
        // ---STARTUP(temp)---
        // check to see if user "temp" exists
        // if it does (from a bad exit or something), wipe and create a new one.
        if (saveDataExists("temp")) {
            //TODO: delete "temp"
        }
        registerUser(new User());  // creates a new guest account called temp at the start of the list
        currentUser = users.get(0); // sets currentUser as the guest account

        // ---STARTUP(list)---
        // Cycle through user data (which doesn't really exist yet so what am i gonna do)
        // add all users to the user list, easy enough


    }

    /**
     * Checks to see if user input matches login and password information
     * wipes "temp" and sets the new account as logged in
     * @param username saved username
     * @param password saved password
     * @return login accepted (T/F)
     */
    public boolean login(String username, String password) {
        // Checking to see if the user can access the data
        if (saveDataExists(username)) {
            System.out.println("Username is incorrect, please try again.");
            return false;
        }
        if (!passwordCorrect(username, password)) {
            System.out.println("Password is incorrect, please try again.");
            return false;
        }
        currentUser = users.get(findUserIndex(username));
        // Clean up temp, return everything to defaults
        return true;
    }

    /**
     * Registers the user
     * @param user user to register
     */
    public void registerUser(User user) {
        users.add(user);
    }

    /**
     * Unregisters the user
     * @param user user to remove
     */
    public void unregisterUser(User user) {
        users.remove(user);
    }

    /**
     * Gets the user that is currently logged in
     * @return currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Searches for a user by their username
     * returns -1, if user not found
     * @param username user's username/name of file
     * @return position in users
     */
    private int findUserIndex(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username))
                return i;
        }
        return -1;
    }

    /**
     * Checks to see if the user exists in the database
     * @param username the name of the file
     * @return a file for user data exists(T/F)
     */
    public boolean saveDataExists(String username) {
        File saveData = new File("./database/userdata" + username + ".json");
        if (saveData.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  compare desired user to credentials, if correct return true
     * @param username user's name
     * @param password user's secret password
     * @return username password combo is correct
     */
    public boolean passwordCorrect(String username, String password) {
        // search by username since we know it exists.
        if(users.get(findUserIndex(username)).getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

}
