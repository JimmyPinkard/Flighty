package User;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.*;

import controller.BookingAgent;
import controller.UserManager;
import database.Data;
import database.FakeData;
import model.Booking;
import model.bookables.flight.Flight;
import model.bookables.flight.Seat;
import model.users.User;
import model.users.info.Passport;
import model.users.info.Person;
import utils.TimeUtils;
import java.time.LocalDateTime;

/**
 * rengotap
 */
public class UserTest {
    static TimeUtils timeUtils;
    static FakeData fakeData;
    static UserManager fakeUM;
    static User created;
    static Person person;
    static BookingAgent bookingAgent;
    
    @BeforeAll
    static void initAll() {
        fakeData = new FakeData();
        Data.setInstance(fakeData);
        fakeUM = new UserManager(fakeData);
        timeUtils = TimeUtils.getInstance();
        bookingAgent = new BookingAgent();
    }

    @BeforeEach
    void init() {
        fakeData.users = new ArrayList<>();
        person = new Person("Hugh", "Mann");
        created = new User(person, "realHughMann", "p");
    }

    @Test
    void userCreate() {
        fakeUM.registerUser(created);
        assertTrue(created != null && fakeUM.getUList().contains(created));
    }

    @Test
    void userDelete() {
        fakeUM.registerUser(created);
        fakeUM.unregisterUser(created);
        assertTrue(created != null && !fakeUM.getUList().contains(created));
    }

    @Test
    void userModify() {
        created.setPassword("newPassword");
        assertTrue(created.getPassword().equals("newPassword"));

    }

    @Test
    void userNewPassport() {
        created.addTraveler(
                new Passport(new Person("Abdullaahi", "Thorburn"), timeUtils.generateDate("04/20/69"),
                        timeUtils.generateDate("11/1/25"), "694201337", "Male"));
        assertTrue(!created.getTravelers().isEmpty());
    }

    @Test
    void userRmPassport() {
        created.addTraveler(
                new Passport(new Person("Abdullaahi", "Thorburn"), timeUtils.generateDate("04/20/69"),
                        timeUtils.generateDate("11/1/25"), "694201337", "Male"));
        created.removeTraveler(created.getTravelers().get(0));
        assertTrue(created.getTravelers().isEmpty());

    }

    @Test
    void userChkBookingEmpty() {
        assertTrue(created.getBookingHistory().isEmpty());
    }

    @Test
    void userChkBookingAdd() {
        created.addTraveler(
                new Passport(new Person("Abdullaahi", "Thorburn"), timeUtils.generateDate("04/20/69"),
                        timeUtils.generateDate("11/1/25"), "694201337", "Male"));
        Seat booking = new Seat(4,"A",false,200,new Flight(LocalDateTime.MIN, LocalDateTime.MAX, "ABC", "CBA",
        new ArrayList<>(), "AA", 0.0, 0.0, 0.0, 0.0, 0.0));
        bookingAgent.bookListing(booking, created, created.getTravelers().get(0));
        assertTrue(!created.getBookingHistory().isEmpty());
    }
    
}
