package database;

import model.Booking;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Jimmy
 */
class DataTest {

    static Data data;
    @BeforeAll
    static void init() {
        data = DatabaseData.getInstance();
    }

    @Test
    void testValidFlight() {
        for(Flight flight : data.getFlights()) {
            Assertions.assertNotNull(flight);
            Assertions.assertFalse(flight.toString().contains("null"));
        }
    }

    @Test
    void testValidHotel() {
        for(Hotel hotel : data.getHotels()) {
            Assertions.assertNotNull(hotel);
            Assertions.assertFalse(hotel.toString().contains("null"));
        }
    }

    @Test
    void testValidBookings() {
        for(Booking booking : data.getBookings()) {
            Assertions.assertNotNull(booking);
            Assertions.assertFalse(booking.toString().contains("null"));
        }
    }
}
