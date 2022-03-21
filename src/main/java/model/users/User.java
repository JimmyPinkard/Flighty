package model.users;

import model.bookables.TravelObject;
import model.users.info.Passport;
import model.users.info.Person;
import org.json.JSONObject;

import java.util.List;
import java.io.File;

/**
 * All user related data
 * Edit this, then write it to the file
 * @author rengotap
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String name;

    //Temporary preferences while preferences are unfinished
    private String prefAirline;
    private String prefClass;
    private String homeAirport;
    private String prefHotel;
    private int depEarly;
    private int depLate;

    private SearchPreferences searchPrefs;

    private List<String>specialReq;
    private List<Passport> travelers;
    private List<TravelObject> bookingHistory;
    private Person person;

    /**
     * Creates a new User
     * !! Data will always be read from file !!
     * @param person name
     * @param username username
     * @param password password
     */
    public User(Person person, String username, String password) {
        this.name = person.getFirstName();
        this.person = person;
        this.username = username;
        this.password = password;
    }

    public User(JSONObject json) {
        fromJSON(json);
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
        name = "Guest";
        username = "temp";
        this.person = new Person("temp", "temp");
    }

    /**
     * Takes temporary guest data and saves it as a registered user for later
     * @param username new username
     * @param password new password
     * @return True if operation was successful
     */
    public boolean registerUser(String username, String password) {
        File saveData = new File("./database/userdata" + username + ".json");
        if (saveData.exists()) {
            System.out.println("This username is already taken.");
            return false;
        } else {  // Changes the guest credentials effectively transfering it to the new user
            this.username = username;
            this.password = password;
            // TODO: set LoggedIn in UserManager to be the current user, (may be taken care of???)
            return true;
        }
    }

    /**
     * Gets the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password, feels wrong setting it to
     * public since its a password but it makes sense.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Adds a special accommodation to the user profile
     * @param toAdd
     */
    public void addSpecial(String toAdd) {
        specialReq.remove(toAdd);
    }

    /**
     * Removes a special accommodation from the user profile
     * @param toRemove
     */
    public void removeSpecial(String toRemove) {
        specialReq.remove(toRemove);
    }

    /**
     * Adds a traveler (family)
     * @param passport
     */
    public void addTraveler(Passport passport) {
        travelers.add(passport);
    }

    /**
     * Removes a traveler (family)
     * @param passport
     */
    public void removeTraveler(Passport passport) {
        travelers.remove(passport);
    }

    /**
     * Gives a list of all travelers associated with this account
     * @return list of travelers
     */
    public List<Passport> getTravelers() {
        return travelers;
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
     * Gets the user's first name for display arounf the UI
     * @return first name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the user's Email
     * @param email new email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Saves a previous booking
     * For booking flights/reservations
     * @param toAdd
     */
    public void addBooking(TravelObject toAdd) {
        bookingHistory.add(toAdd);
    }

    /**
     * Removes a previous booking
     * For canceling flights/reservations
     * @param toRemove
     */
    public void removeBooking(TravelObject toRemove) {
        bookingHistory.remove(toRemove);
    }

    /**
     * Gets a list of the user's previous bookings
     * @return booking history
     */
    public List<TravelObject> getBookingHistory() {
        return bookingHistory;
    }

    /**
     * Turns object into JSON
     * @return
     */
    protected JSONObject toJSON() {
        return null;
    }

    /**
     * Constructs object from JSON
     * @param json
     */
    protected void fromJSON(final JSONObject json) {
    }
}