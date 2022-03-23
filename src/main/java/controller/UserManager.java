package controller;

import database.Data;
import model.users.User;
import model.users.info.Person;

import java.util.List;
import java.util.ArrayList;
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
        users = new ArrayList<User>();
    
        if (saveDataExists("temp")) {
            //TODO: delete "temp" from disk or override with a default, waiting on IO for that
        }

        // TODO: (James) move to data or io - usermanager is not responsible for io
        // Cycle through user data (this is going to have to become a helper method)
        int numUsers = new File("./database/userdata").list().length;
        for(int i = 0; i < numUsers; i++) {
            // TODO: figure out how to convert whatever comes in from Data data into the all of the users.
            // Can't really do this without the Data class though
            User toAdd = new User(new Person("placeholder", "placeholder"), "placeholder", "placeholder");
            toAdd.setEmail("placeholder");
                // loop to add booking history
                // loop to add travelers
                // loop to add prefrences
            registerUser(toAdd);
        }
    }

    /**
     * Checks to see if user input matches login and password information
     * wipes "temp" and sets the new account as logged in
     * @param username saved username
     * @param password saved password
     * @return login accepted (T/F)
     */
    public void login(String username, String password) {
        currentUser = users.get(findUserIndex(username));
    }

    public boolean userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAnyoneLoggedIn() {
        return currentUser != null;
    }

    public boolean credentialsCorrect(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void logoutCurrent() {
        currentUser = null;
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
