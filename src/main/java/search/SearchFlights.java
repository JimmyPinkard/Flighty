package search;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import database.Data;
import model.bookables.flight.Flight;
import model.users.SearchPreferences;
import search.filters.FlightFilter;
import search.filters.SearchFilter;
import utils.TimeUtils;

public abstract class SearchFlights implements Search {
    public static List<Flight> execute(Data data,
            EnumMap<? extends SearchFilter, String> preferences) {
        List<Flight> out = new ArrayList<Flight>();

        String airportFrom = preferences.get(FlightFilter.AIRPORT_FROM);
        String airportTo = preferences.get(FlightFilter.AIRPORT_TO);
        String company = preferences.get(FlightFilter.COMPANY);

        for (Flight flight : data.getFlights()) {
            if (!airportFrom.equalsIgnoreCase(SearchPreferences.EMPTY)
                    && !airportFrom.equalsIgnoreCase(flight.getAirportFrom())) {
                continue;
            } else if (!airportTo.equalsIgnoreCase(SearchPreferences.EMPTY)
                    && !airportTo.equalsIgnoreCase(flight.getAirportTo())) {
                continue;
            } else if (!company.equalsIgnoreCase(SearchPreferences.EMPTY)
                    && !company.equalsIgnoreCase(flight.getCompany())) {
                continue;
            } else if (!preferences.get(FlightFilter.TIME_DEPART)
                    .equalsIgnoreCase(SearchPreferences.EMPTY)) {
                LocalDate dateDepartAfter = preferences.get(FlightFilter.DATE_DEPART)
                        .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalDate.ofEpochDay(0L)
                                : TimeUtils.generateDate(preferences.get(FlightFilter.DATE_DEPART));

                LocalTime timeDepartAfter = preferences.get(FlightFilter.TIME_DEPART)
                        .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalTime.ofSecondOfDay(0)
                                : TimeUtils.generateTime(preferences.get(FlightFilter.TIME_DEPART));

                LocalDateTime departAfter = LocalDateTime.of(dateDepartAfter, timeDepartAfter);
                // TimeUtils.generateTime(preferences.get(FlightFilter.TIME_DEPART));
                // TODO: change flight arival to travel time
                if (departAfter.compareTo(flight.getDepartureTime()) > 0) {
                    continue;
                }
            }
            // } else if (preferences.get(FlightFiltr.TIME_ARRIVE)
            // .equalsIgnoreCase(SearchPreferences.EMPTY)) {
            // LocalTime timeArriveLaterThan =
            // TimeUtils.generateTime(preferences.get(FlightFilter.TIME_ARRIVE));
            // if (timeArriveLaterThan.compareTo(flight.getArrivalTime().toLocalTime()) < 0) {
            // continue;
            // }
            // }


            out.add(flight);
        }

        return out;
    }
}
