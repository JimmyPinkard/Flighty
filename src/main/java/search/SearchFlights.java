package search;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import database.Data;
import model.bookables.TravelObject;
import model.bookables.flight.Flight;
import search.filters.FlightFilter;
import search.filters.SearchFilter;

public class SearchFlights implements Search {
    @Override
    public List<TravelObject> execute(Data data,
            EnumMap<? extends SearchFilter, String> preferences) {
        List<TravelObject> out = new ArrayList<TravelObject>();

        // for (Flight flight : data.getFlights()) {
        // if (flight.getAirportFrom().equalsIgnoreCase(airportFrom)
        // && flight.getAirportTo().equals(airportTo)) {
        // out.add(flight);
        // }
        // }

        return out;
    }
}
