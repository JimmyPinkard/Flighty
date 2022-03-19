package model.users;


import model.users.info.Passport;
import model.users.info.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Saves all user related data
 */
public class User {
    private String username;
    private String password;
    private String email;
    private SearchPreferences searchPrefs;
    private List<Passport> travelers;
    private Person person;

    public User(Person person, String username, String password) {}

    public void addTraveler(Passport passport) {}

    public void removeTraveler(Passport passport) {}

    public List<Passport> getTravelers() {
        return new ArrayList<Passport>();
    }

    /**
     * Gets the user associated with the account
     */
    public Person getRegisteredPerson() {
        return person;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
