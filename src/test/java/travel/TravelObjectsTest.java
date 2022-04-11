package travel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import database.Data;
import database.FakeData;
import model.bookables.Bookable;
import model.bookables.flight.*;
import model.bookables.hotel.*;
import model.users.SearchPreferences;
import search.filters.FlightFilter;

/**
 * @author Jack Hyatt
 */
public class TravelObjectsTest {

    static FakeData fakeData;
    
    @BeforeAll
    static void initAll() {
        fakeData = new FakeData();
        Data.setInstance(fakeData);
    }

    @BeforeEach
    void init() {
        fakeData.flights = new ArrayList<Flight>();
        fakeData.hotels = new ArrayList<Hotel>();
    }

    @Test
    void nullHotels(){
        fakeData.hotels.add(null);
        assertEquals(null, fakeData.hotels.get(0));
    }

    @Test
    void nullFlights(){
        fakeData.flights.add(null);
        assertEquals(null, fakeData.flights.get(0));
    }

    @Test
    void normalHotel(){
        Hotel fakeHotel = new Hotel(new ArrayList<Bookable>(), "Hyatt", 4.5, "Rock Hill");
        fakeData.hotels.add(fakeHotel);
        assertEquals(fakeHotel,fakeData.hotels.get(0));
    }

    @Test
    void normalFlight(){
        Flight fakeFlight = new Flight(LocalDateTime.MIN, LocalDateTime.now(), "From", "To", new ArrayList<Bookable>(), "Comp", 4.5, 1,2,3,4);
        fakeData.flights.add(fakeFlight);
        assertEquals(fakeFlight,fakeData.flights.get(0));
    }

    @Test
    void isRoomBookedBigRange(){
        Hotel fakeHotel = new Hotel(new ArrayList<Bookable>(), "Hyatt", 4.5, "Rock Hill");
        Room fakeRoom = new Room(4, "23", 2, fakeHotel);
        fakeRoom.bookRange(LocalDate.MIN, LocalDate.MAX);
        assertTrue(fakeRoom.isBooked(LocalDate.now(), LocalDate.now()));
    }

    @Test
    void isRoomBooked(){
        Hotel fakeHotel = new Hotel(new ArrayList<Bookable>(), "Hyatt", 4.5, "Rock Hill");
        Room fakeRoom = new Room(4, "23", 2, fakeHotel);
        fakeRoom.bookRange(LocalDate.now(), LocalDate.now());
        assertTrue(fakeRoom.isBooked(LocalDate.now(), LocalDate.now()));
    }
}
