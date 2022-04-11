package database;

import model.bookables.flight.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DataTest {

    @Test
    void testValidFlight() {
        var data = DatabaseData.getInstance();
        var flight = data.getFlights().get(0);
        try {
            for(Field field : Flight.class.getFields()) {
                Assertions.assertNotNull(field.get(flight));
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testNullFlight() {
        var data = DatabaseData.getInstance();
        var flight = data.getFlights().get(0);
    }
}
