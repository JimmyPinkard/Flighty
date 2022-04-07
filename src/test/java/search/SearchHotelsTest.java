package search;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import database.Data;
import database.FakeData;
import model.bookables.hotel.Hotel;
import model.users.SearchPreferences;

class SearchHotelsTest {
    static FakeData fakeData;

    @BeforeAll
    static void initAll() {
        fakeData = new FakeData();
        Data.setInstance(fakeData);
    }

    @Test
    void testQueryAll() {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel(""));
        fakeData.hotels = hotels;

        var result = SearchHotels.execute(new SearchPreferences());

        assertIterableEquals(hotels, result);
    }
}
