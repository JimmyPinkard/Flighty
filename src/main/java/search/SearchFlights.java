package search;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import database.Data;
import model.bookables.flight.Flight;
import search.filters.FlightFilter;
import search.filters.SearchFilter;

public abstract class SearchFlights implements Search {
    public static List<Flight> execute(Data data,
            EnumMap<? extends SearchFilter, String> preferences) {
        List<Flight> out = new ArrayList<Flight>();

        String airportFrom = preferences.get(FlightFilter.AIRPORT_FROM);
        String airportTo = preferences.get(FlightFilter.AIRPORT_TO);

        for (Flight flight : data.getFlights()) {
            if (flight.getAirportFrom().equalsIgnoreCase(airportFrom)
                    && flight.getAirportTo().equals(airportTo)) {
                out.add(flight);
            }
        }

        return out;
    }
}
