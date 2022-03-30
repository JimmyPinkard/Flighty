package controller;

import database.Data;
import model.users.User;

import java.util.List;

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
        users = data.getUsers();
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
