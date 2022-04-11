package database;

import model.Booking;
import model.bookables.flight.Flight;
import model.bookables.hotel.Hotel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DataTest {

    Data data = DatabaseData.getInstance();

    @Test
    void testValidFlight() {
        for(Flight flight : data.getFlights()) {
            Assertions.assertNotNull(flight);
        }
    }

    @Test
    void testValidHotel() {
        for(Hotel hotel : data.getHotels()) {
            Assertions.assertNotNull(hotel);
        }
    }

    @Test
    void testValidBookings() {
        for(Booking booking : data.getBookings()) {
            Assertions.assertNotNull(booking);
        }
    }
}
