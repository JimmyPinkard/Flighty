package search;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import database.Data;
import database.FakeData;
import model.bookables.Bookable;
import model.bookables.flight.Flight;
import model.bookables.flight.Seat;
import model.users.SearchPreferences;
import search.filters.FlightFilter;
import utils.TimeUtils;

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

    @Test
    void testQueryPrice() {
        List<Bookable> seats = new ArrayList<>();
        Flight flight = new Flight(LocalDateTime.MIN, LocalDateTime.MAX, "ABC", "CBA", seats, "AA",
                0.0, 0.0, 0.0, 0.0, 0.0);
        seats.add(new Seat(1, "A", false, 101, flight));

        fakeData.flights.add(flight);
        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.PRICE, "100");
        prefs.fPref.put(FlightFilter.AIRPORT_FROM, "ABC");
        prefs.fPref.put(FlightFilter.AIRPORT_TO, "CBA");

        var result = SearchFlightTrips.findRoute(prefs, new ArrayList<>());
        assertTrue(result.isEmpty());
    }

    @Test
    void testQueryDepartTime() {
        List<Bookable> seats = new ArrayList<>();
        Flight flight = new Flight(TimeUtils.getInstance().genDateTime("1/1/1 01:00"),
                LocalDateTime.MAX, "ABC", "CBA", seats, "AA", 0.0, 0.0, 0.0, 0.0, 0.0);

        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.TIME_DEPART_EARLIEST, "23:00");

        assertFalse(SearchFlightTrips.isValidOption(flight, prefs.fPref));
    }

    @Test
    void testQueryDepartDate() {
        List<Bookable> seats = new ArrayList<>();
        Flight flight = new Flight(TimeUtils.getInstance().genDateTime("1/1/1 01:00"),
                LocalDateTime.MAX, "ABC", "CBA", seats, "AA", 0.0, 0.0, 0.0, 0.0, 0.0);

        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.DATE_DEPART_EARLIEST, "2/2/2");

        assertFalse(SearchFlightTrips.isValidOption(flight, prefs.fPref));
    }

    @Test
    void testQueryLayovers() {
        List<Bookable> seats = new ArrayList<>();
        Flight flight = new Flight(TimeUtils.getInstance().genDateTime("1/1/1 01:00"),
                TimeUtils.getInstance().genDateTime("1/1/1 02:00"), "ABC", "EFG", seats, "AA", 0.0,
                0.0, 0.0, 0.0, 0.0);
        Flight flight2 = new Flight(TimeUtils.getInstance().genDateTime("1/1/1 03:00"),
                TimeUtils.getInstance().genDateTime("1/1/1 04:00"), "EFG", "CBA", seats, "AA", 0.0,
                0.0, 0.0, 0.0, 0.0);
        FakeData.getInstance().getFlights().add(flight);
        FakeData.getInstance().getFlights().add(flight2);

        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.LAYOVERS, "0");
        prefs.fPref.put(FlightFilter.AIRPORT_FROM, "ABC");
        prefs.fPref.put(FlightFilter.AIRPORT_TO, "CBA");
        var result = SearchFlightTrips.findRoute(prefs, new ArrayList<>());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindPath() {
        List<Bookable> seats = new ArrayList<>();
        var flights = FakeData.getInstance().getFlights();

        flights.add(new Flight(TimeUtils.getInstance().genDateTime("1/1/1 01:00"),
                TimeUtils.getInstance().genDateTime("1/1/1 02:00"), "A", "B", seats, "AA", 0.0, 0.0,
                0.0, 0.0, 0.0));
        flights.add(new Flight(TimeUtils.getInstance().genDateTime("1/1/1 03:00"),
                TimeUtils.getInstance().genDateTime("1/1/1 04:00"), "B", "C", seats, "AA", 0.0, 0.0,
                0.0, 0.0, 0.0));
        flights.add(new Flight(TimeUtils.getInstance().genDateTime("1/1/1 04:00"),
                TimeUtils.getInstance().genDateTime("1/1/1 04:00"), "C", "D", seats, "AA", 0.0, 0.0,
                0.0, 0.0, 0.0));

        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.AIRPORT_FROM, "A");
        prefs.fPref.put(FlightFilter.AIRPORT_TO, "D");
        var result = SearchFlightTrips.execute(prefs);

        assertFalse(result.isEmpty());
    }

    @Test
    void testFindPathWithDeadEnd() {
        List<Bookable> seats = new ArrayList<>();
        var flights = FakeData.getInstance().getFlights();

        flights.add(new Flight(TimeUtils.getInstance().genDateTime("1/1/1 01:00"),
                TimeUtils.getInstance().genDateTime("1/1/1 02:00"), "S", "A", seats, "AA", 0.0, 0.0,
                0.0, 1.0, 0.0));
        flights.add(new Flight(TimeUtils.getInstance().genDateTime("1/1/1 03:00"),
                TimeUtils.getInstance().genDateTime("1/1/1 04:00"), "A", "B", seats, "AA", 0.0, 1.0,
                0.0, 8.0, 0.0));
        flights.add(new Flight(TimeUtils.getInstance().genDateTime("1/1/1 03:00"),
                TimeUtils.getInstance().genDateTime("1/1/1 04:00"), "A", "C", seats, "AA", 0.0, 0.0,
                0.0, 0.0, 0.0));
        flights.add(new Flight(TimeUtils.getInstance().genDateTime("1/1/1 05:00"),
                TimeUtils.getInstance().genDateTime("1/1/1 06:00"), "C", "D", seats, "AA", 0.0, 0.0,
                0.0, 9.0, 0.0));

        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.AIRPORT_FROM, "A");
        prefs.fPref.put(FlightFilter.AIRPORT_TO, "D");
        var result = SearchFlightTrips.execute(prefs);

        assertFalse(result.isEmpty());
    }

    @Test
    void testQueryNothing() {
        var prefs = new SearchPreferences();
        prefs.fPref.put(FlightFilter.AIRPORT_FROM, "ABC");
        prefs.fPref.put(FlightFilter.AIRPORT_TO, "CBA");
        var result = SearchFlightTrips.execute(prefs);

        assertTrue(result.isEmpty());
    }
}
