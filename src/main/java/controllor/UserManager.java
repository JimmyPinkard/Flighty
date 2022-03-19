package controllor;

import database.Data;
import model.users.info.User;

import java.util.List;
/**
 * Handles all user related tasks
 */
public class UserManager {
    private User currentUser;
    private List<User> users;

    /**
     * Creates a new Controllor.UserManager
     * @param data
     */
    public UserManager(Data data) {

    }

    /**
     * Checks to see if user input matches login and password information
     * @param username saved username
     * @param password saved password
     * @return login accepted (T/F)
     */
    public boolean login(String username, String password) {
        return false;
    }

    /**
     * Registers the user
     * @param user user to register
     */
    public void registerUser(User user) {

    }

    /**
     * Unregisters the user
     * @param user user to remove
     */
    public void unregisterUser(User user) {

    }

    /**
     * Gets the user that is currently logged in
     * @return currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
