package model.users.info;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Holds a Person's first and last name
 * @author rengotap
 */
@Entity
@Table(name = "Person")
public class Person implements Serializable {

    @Column(name = "")
    private String firstName;
    @Column(name = "")
    private String lastName;

    /**
     * Creates a new person
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the person's first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the person's last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }
}
