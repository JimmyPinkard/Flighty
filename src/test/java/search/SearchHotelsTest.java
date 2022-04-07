package search;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.*;
import database.Data;
import database.FakeData;
import model.bookables.Bookable;
import model.bookables.hotel.Hotel;
import model.bookables.hotel.Room;
import model.users.SearchPreferences;
import search.filters.HotelFilter;

class SearchHotelsTest {
    static FakeData fakeData;

    @BeforeAll
    static void initAll() {
        fakeData = new FakeData();
        Data.setInstance(fakeData);
    }

    @BeforeEach
    void init() {
        fakeData.hotels = new ArrayList<>();
    }

    void setupWorkingBaseRoom() {
        List<Bookable> bookables = new ArrayList<>();
        Hotel hotel = new Hotel(bookables, "smith", 0.0, "earth");

        bookables.add(new Room(1, "A", 1, new HashSet<LocalDate>(), hotel));

        fakeData.hotels.add(hotel);
    }

    @Test
    void testQueryAll() {
        fakeData.hotels.add(new Hotel());

        var result = SearchHotels.execute(new SearchPreferences());

        assertIterableEquals(fakeData.hotels, result);
    }

    @Test
    void testQueryLocation() {
        setupWorkingBaseRoom(); // location = "earth"

        var prefs = new SearchPreferences();
        prefs.hPref.put(HotelFilter.LOCATION, "space");
        // dates fix parse error
        prefs.hPref.put(HotelFilter.DATE_START, "1/1/1");
        prefs.hPref.put(HotelFilter.DATE_END, "1/1/1");
        var result = SearchHotels.execute(prefs);

        assertTrue(result.isEmpty());
    }

    @Test
    void testQueryNothing() {
        fakeData.hotels = new ArrayList<>();

        var result = SearchHotels.execute(new SearchPreferences());

        assertTrue(result.isEmpty());
    }

    @Test
    void testQueryCompany() {
        setupWorkingBaseRoom(); // company = smith

        var prefs = new SearchPreferences();
        prefs.hPref.put(HotelFilter.COMPANY, "not smith");
        // dates fix parse error
        prefs.hPref.put(HotelFilter.DATE_START, "1/1/1");
        prefs.hPref.put(HotelFilter.DATE_END, "1/1/1");
        var result = SearchHotels.execute(prefs);

        assertTrue(result.isEmpty());
    }

    @Test
    void testQueryDateEndBeforeStart() {
        setupWorkingBaseRoom();
        var prefs = new SearchPreferences();

        prefs.hPref.put(HotelFilter.DATE_START, "1/3/1");
        prefs.hPref.put(HotelFilter.DATE_END, "1/1/1");
        var result = SearchHotels.execute(prefs);

        assertTrue(result.isEmpty());
    }

    @Test
    void testQueryRangeNotAvailable() {
        List<Bookable> bookables = new ArrayList<>();
        Hotel hotel = new Hotel(bookables, "smith", 0.0, "earth");

        var booked = new HashSet<LocalDate>();
        booked.add(LocalDate.of(1, 1, 2));
        bookables.add(new Room(1, "A", 1, booked, hotel));

        fakeData.hotels.add(hotel);
        var prefs = new SearchPreferences();

        prefs.hPref.put(HotelFilter.DATE_START, "1/1/1");
        prefs.hPref.put(HotelFilter.DATE_END, "1/3/1");
        var result = SearchHotels.execute(prefs);

        assertTrue(result.isEmpty());
    }

    @Test
    void testQueryRating() {
        setupWorkingBaseRoom(); // rating = 0.0

        var prefs = new SearchPreferences();
        prefs.hPref.put(HotelFilter.RATING, "5.0");
        // dates fix parse error
        prefs.hPref.put(HotelFilter.DATE_START, "1/1/1");
        prefs.hPref.put(HotelFilter.DATE_END, "1/1/1");
        var result = SearchHotels.execute(prefs);

        assertTrue(result.isEmpty());
    }
}
