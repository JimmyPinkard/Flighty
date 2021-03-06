package model.users;

import com.mongodb.DBObject;

import model.Booking;
import model.users.info.Passport;
import model.users.info.Person;
import search.filters.FlightFilter;
import search.filters.HotelFilter;
import utils.CollectionUtils;

import java.util.*;

/**
 * All user related data
 * Edit this, then write it to the file
 * @author rengotap
 */
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private SearchPreferences preferences;

    private List<String> specialReq;
    private List<Passport> travelers; // passport 0 should always be the user
    private List<Booking> bookingHistory;
    private Person person;

    /**
     * Creates a new User
     * !! Data will always be read from file !!
     * @param person name
     * @param username username
     * @param password password
     */
    public User(Person person, String username, String password) {
        this.person = person;
        this.username = username;
        this.password = password;

        preferences = new SearchPreferences();
        specialReq = new ArrayList<>();
        travelers = new ArrayList<>();
        bookingHistory = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public User(DBObject object) {
        this.id = (String) object.get("id");
        this.username = (String) object.get("username");
        this.password = (String) object.get("password");
        this.person = new Person((DBObject) object.get("person"));
        this.email = (String) object.get("email");
        this.preferences = new SearchPreferences((DBObject) object.get("preferences")); // TODO import preferences from data
        this.specialReq = new ArrayList<>();
        this.specialReq.addAll((List<String>) object.get("specialReq"));
        this.travelers = new ArrayList<>();
        //Duct tape
        //TODO not this
        try {
            for (DBObject passport : (List<DBObject>) object.get("passports")) {
                this.travelers.add(new Passport(passport));
            }
        }
        catch (Exception e) {
            //
        }
        this.bookingHistory = new ArrayList<>();
        //If it's null duct tape
        //TODO not this
        try {
            for(DBObject bookable : (List<DBObject>) object.get("bookedListings")) {
                this.bookingHistory.add(new Booking(bookable));
            }
        }
        catch (Exception e) {
            //
        }
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
        this.id = UUID.randomUUID().toString();
        username = "temp";
        password = "p";
        email = "email@email.com";
        this.person = new Person("temp", "temp");
        preferences = new SearchPreferences();
        travelers = new ArrayList<>();
        specialReq = new ArrayList<>();
        bookingHistory = new ArrayList<>();
    }

    public String getId() {
        return id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the SearchPrefrences OBJECT
     * @return SearchPrefrences
     */
    public SearchPreferences getSearchPreferences() {
        return preferences;
    }

    /**
     * Gets the flight prefrences from SearchPreferences
     * @return EnumMap of flight prefrences
     */
    public EnumMap<FlightFilter, String> getFPref() {
        return preferences.getFPref();
    }

    /**
     * Gets the hotel prefrences from SearchPreferences
     * @return EnumMap of hotel prefrences
     */
    public EnumMap<HotelFilter, String> getHPref() {
        return preferences.getHPref();
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
    public String getFirstName() {
        return person.getFirstName();
    }

    /**
     * Gets the user's last name for display
     * @return last name
     */
    public String getLastName() {
        return person.getLastName();
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
    public void addBooking(Booking toAdd) {
        bookingHistory.add(toAdd);
    }

    /**
     * Removes a previous booking
     * For canceling flights/reservations
     * @param toRemove
     */
    public void removeBooking(Booking toRemove) {
        bookingHistory.remove(toRemove);
    }

    public void removeBooking(int index) {
        bookingHistory.remove(index);
    }


    /**
     * Gets a list of the user's previous bookings
     * @return booking history
     */
    public List<Booking> getBookingHistory() {
        return bookingHistory;
    }

    @Override
    public String toString() {
        return "{" + "\"id\": \"" + id + "\", "
                + "\"username\": \"" + username + "\", "
                + "\"password\": \"" + password + "\", "
                + "\"email\": \"" + email + "\", "
                + "\"preferences\": " + preferences + ", "
                + "\"specialReq\": " + CollectionUtils.stringArray(specialReq.toArray()) + ", "
                + "\"travelers\": " + CollectionUtils.stringArray(travelers.toArray()) + ", "
                + "\"bookingHistory\": " + CollectionUtils.stringArray(bookingHistory.toArray()) + ", "
                + "\"person\": " + person + "}";
    }
}