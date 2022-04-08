package search;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import database.Data;
import database.FakeData;
import model.bookables.flight.Flight;
import model.users.SearchPreferences;
import search.filters.FlightFilter;

class SearchFlightTripsTest {
    static FakeData fakeData;

    @BeforeAll
    static void initAll() {
        fakeData = new FakeData();
        Data.setInstance(fakeData);
    }

    @BeforeEach
    void init() {
        fakeData.flights = new ArrayList<>();
    }

    @Test
    void testQueryAll() {
        fakeData.flights.add(new Flight(LocalDateTime.MIN, LocalDateTime.MAX, "ABC", "CBA",
                new ArrayList<>(), "AA", 0.0, 0.0, 0.0, 0.0, 0.0));

        var result = SearchFlightTrips.getValidFlights(new SearchPreferences().fPref);

        assertIterableEquals(fakeData.flights, result);
    }

    @Test
    void testQueryAirportTo() {
        fakeData.flights.add(new Flight(LocalDateTime.MIN, LocalDateTime.MAX, "ABC", "CBA",
                new ArrayList<>(), "AA", 0.0, 0.0, 0.0, 0.0, 0.0));
        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.AIRPORT_TO, "LAZ");

        var result = SearchFlightTrips.getValidFlights(prefs.fPref);
        assertTrue(result.isEmpty());
    }

    @Test
    void testQueryAirportFrom() {
        fakeData.flights.add(new Flight(LocalDateTime.MIN, LocalDateTime.MAX, "ABC", "CBA",
                new ArrayList<>(), "AA", 0.0, 0.0, 0.0, 0.0, 0.0));
        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.AIRPORT_FROM, "LAZ");

        var result = SearchFlightTrips.getValidFlights(prefs.fPref);
        assertTrue(result.isEmpty());
    }

    @Test
    void testQueryCompany() {
        fakeData.flights.add(new Flight(LocalDateTime.MIN, LocalDateTime.MAX, "ABC", "CBA",
                new ArrayList<>(), "AA", 0.0, 0.0, 0.0, 0.0, 0.0));
        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.COMPANY, "BB");

        var result = SearchFlightTrips.getValidFlights(prefs.fPref);
        assertTrue(result.isEmpty());
    }

    // @Test
    // void testQueryPrice() {
    // List<Bookable> seats = new ArrayList<>();
    // fakeData.flights.add(new Flight(LocalDateTime.MIN, LocalDateTime.MAX, "ABC", "CBA",
    // new ArrayList<>(), "AA", 0.0, 0.0, 0.0, 0.0, 0.0));
    // // seats.add(new Seat(1, "A", false, 101, null));
    // var prefs = new SearchPreferences();
    // // prefs.fPref.put(FlightFilter.PRICE, "100");
    // prefs.fPref.put(FlightFilter.AIRPORT_FROM, "ABC");
    // prefs.fPref.put(FlightFilter.AIRPORT_FROM, "CBA");

    // var result = SearchFlightTrips.findRoute(prefs, null);
    // assertTrue(result.isEmpty());
    // }
}
