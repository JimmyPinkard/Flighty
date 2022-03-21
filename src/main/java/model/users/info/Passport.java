package model.users.info;

import java.time.LocalDate;

/**
 * Holds passport info (a traveler)
 * @author rengotap
 */
public class Passport {
    private Person person;
    private LocalDate dateOfBirth;
    private LocalDate expirationDate;
    private String number;
    private String gender;

    /**
     * Creates a new passport
     * @param person
     */
    public Passport(Person person, LocalDate dateOfBirth, 
        LocalDate expirationDate, String number, String gender) {
        this.person = person;
        this.dateOfBirth = dateOfBirth;
        this.expirationDate = expirationDate;
        this.number = number;
        this.gender = gender;
    }

    /**
     * Passport Holder
     * @return person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Holder's Date of Birth
     * @return dateOfBirth
     */
    public LocalDate getDOB() {
        return dateOfBirth;
    }

    /**
     * Passport expiration date
     * @return expirationDate
     */
    public LocalDate getExpDate() {
        return expirationDate;
    }

    /**
     * Holder's phone number
     * @return number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Holder's gender
     * @return gender
     */
    public String getGender() {
        return gender;
    }
}