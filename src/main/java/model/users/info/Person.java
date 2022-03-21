package model.users.info;

/**
 * Holds a Person's first and last name
 * @author rengotap
 */
public class Person {

    private String firstName;
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
