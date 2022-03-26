package model.users.info;

import org.bson.Document;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Holds passport info (a traveler)
 * @author rengotap
 */
public class Passport {
    private String id;
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
        this.id = UUID.randomUUID().toString();
        this.person = person;
        this.dateOfBirth = dateOfBirth;
        this.expirationDate = expirationDate;
        this.number = number;
        this.gender = gender;
    }

    public Passport(final Document document) {
        this.id = (String) document.get("id");
        this.person = new Person(document);
        this.dateOfBirth = (LocalDate) document.get("dateOfBirth");
        this.expirationDate = (LocalDate) document.get("expirationDate");
        this.number = (String) document.get("number");
        this.gender = (String) document.get("gender");
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

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", person:" + person +
                ", dateOfBirth:" + dateOfBirth +
                ", expirationDate:" + expirationDate +
                ", number:'" + number + '\'' +
                ", gender:'" + gender + '\'' +
                '}';
    }
}