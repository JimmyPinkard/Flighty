package model.users.info;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Property;
import org.bson.Document;

/**
 * Holds a Person's first and last name
 * @author rengotap
 */
@Entity("People")
public class Person {
    @Property("firstName")
    private String firstName;
    @Property("lastName")
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

    public Person(Document document) {
        this.firstName = (String)document.get("firstName");
        this.lastName = (String)document.get("lastName");
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

    @Override
    public String toString() {
        return "{" +
                "firstName:'" + firstName + '\'' +
                ", lastName:'" + lastName + '\'' +
                '}';
    }
}
