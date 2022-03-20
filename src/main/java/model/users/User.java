package model.users;

import model.bookables.TravelObject;
import model.users.info.Passport;
import model.users.info.Person;
import java.util.ArrayList;
import java.util.List;

/**
 * All user related data
 * Edit this, then write it to the file
 * @author rengotap
 */
public class User {
    private String username;
    private String password;
    private String email;
    private SearchPreferences searchPrefs;
    private List<Passport> travelers;
    private Person person;

    /**
     * Creates a new User
     * !! Data will always be read from file !!
     * @param person name
     * @param username username
     * @param password password
     */
    public User(Person person, String username, String password) {

    }

    /**
     * Creates a new temporary User account
     * Used for saving temporary guest data
     * !! Guest data should not be saved* !!
     * 
     * *Can be converted into a regular account and saved 
     *  if the user desires using registerUser() method
     */
    public User() {
        username = "temp";
        password = "temp";
    }

    /**
     * Takes temporary guest data and saves it as a registered user for later
     * @param username new username
     * @param password new password
     * @return True if operation was successful
     */
    public boolean registerUser(String username, String password) {
        // === PSEUDO CODE === 
        // check to see if specified requested username is available
        // assign new user selected username and password
        // set LoggedIn in UserManager to be the current user, (should be taken care of?)
        this.username = username;
        this.password = password;
        //TODO: Check if username is available
        return false;
    }
    /**
     * Adds a traveler (family)
     * @param passport
     */
    public void addTraveler(Passport passport) {
        //TODO: add traveler to travelers
    }

    /**
     * Removes a traveler (family)
     * @param passport
     */
    public void removeTraveler(Passport passport) {
        //TODO: remove traveler from travelers
    }

    /**
     * Gives a list of all travelers associated with this account
     * @return Array list of travelers
     */
    public List<Passport> getTravelers() {
        return new ArrayList<Passport>();
    }

    /**
     * Gets the user associated with the account
     * @return returns the account owner
     */
    public Person getRegisteredPerson() {
        return person;
    }

    /**
     * Gets the user's Email
     * @return email string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Changes the user's Email
     * @param email new email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    // User Booking History Methods

    /**
     * Saves a previous booking
     * For booking flights/reservations
     * @param toAdd
     */
    public void addBooking(TravelObject toAdd) {
        // TODO: add booking to json history
    }

    /**
     * Removes a previous booking
     * For canceling flights/reservations
     */
    public void removeBooking() {
        // TODO: remove booking form json history
    }
}
