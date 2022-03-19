package model.users.info;

import java.time.LocalDate;

/**
 * Holds passport info
 */
public class Passport {
    private Person person;
    private LocalDate dateOfBirth;
    private LocalDate expirationDate;
    private String number;
    private String gender;

    public Passport(Person person) {
    }
}
