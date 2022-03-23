package model.users.info;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Holds passport info (a traveler)
 * @author rengotap
 */
@Entity("Passport")
public class Passport {
    @Id
    private String id;
    @Property("person")
    private Person person;
    @Property("dateOfBirth")
    private LocalDate dateOfBirth;
    @Property("expirationDate")
    private LocalDate expirationDate;
    @Property("number")
    private String number;
    @Property("gender")
    private String gender;

    /**
     * Creates a new passport
     * @param person
     */
    public Passport(Person person, LocalDate dateOfBirth, 
        LocalDate expirationDate, String number, String gender) {
        this.id = UUID.randomUUID().toString();
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