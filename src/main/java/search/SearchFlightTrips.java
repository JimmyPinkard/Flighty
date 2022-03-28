package search;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import database.Data;
import model.bookables.flight.Flight;
import model.bookables.flight.FlightTrip;
import model.users.SearchPreferences;
import search.filters.FlightFilter;
import search.filters.SearchFilter;
import utils.TimeUtils;

public class SearchFlightTrips implements Search {

    public static List<FlightTrip> execute(EnumMap<? extends SearchFilter, String> preferences) {
        List<FlightTrip> out = new ArrayList<FlightTrip>();

        String airportFrom = preferences.get(FlightFilter.AIRPORT_FROM);
        String airportTo = preferences.get(FlightFilter.AIRPORT_TO);
        var flights = findRoute(airportFrom, airportTo);
        out.add(new FlightTrip(flights));

        return out;
    }


    private static boolean isValidOption(Flight flight,
            EnumMap<? extends SearchFilter, String> preferences) {
        String airportFrom = preferences.get(FlightFilter.AIRPORT_FROM);
        String airportTo = preferences.get(FlightFilter.AIRPORT_TO);
        String company = preferences.get(FlightFilter.COMPANY);

        if (!airportFrom.equalsIgnoreCase(SearchPreferences.EMPTY)
                && !airportFrom.equalsIgnoreCase(flight.getAirportFrom()))
            return false;

        if (!airportTo.equalsIgnoreCase(SearchPreferences.EMPTY)
                && !airportTo.equalsIgnoreCase(flight.getAirportTo()))
            return false;

        if (!company.equalsIgnoreCase(SearchPreferences.EMPTY)
                && !company.equalsIgnoreCase(flight.getCompany()))
            return false;

        if (!preferences.get(FlightFilter.DATE_DEPART).equalsIgnoreCase(SearchPreferences.EMPTY)) {
            LocalDate dateDepartAfter = preferences.get(FlightFilter.DATE_DEPART)
                    .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalDate.ofEpochDay(0L)
                            : TimeUtils.generateDate(preferences.get(FlightFilter.DATE_DEPART));

            LocalTime timeDepartAfter = preferences.get(FlightFilter.TIME_DEPART)
                    .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalTime.ofSecondOfDay(0)
                            : TimeUtils.generateTime(preferences.get(FlightFilter.TIME_DEPART));

            LocalDateTime departAfter = LocalDateTime.of(dateDepartAfter, timeDepartAfter);
            if (departAfter.compareTo(flight.getDepartureTime()) > 0)
                return false;
        }

        if (!preferences.get(FlightFilter.DATE_ARRIVE).equalsIgnoreCase(SearchPreferences.EMPTY)) {
            LocalDate dateArriveBefore = preferences.get(FlightFilter.DATE_ARRIVE)
                    .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalDate.ofEpochDay(0L)
                            : TimeUtils.generateDate(preferences.get(FlightFilter.DATE_ARRIVE));

            LocalTime timeArriveBefore = preferences.get(FlightFilter.TIME_ARRIVE)
                    .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalTime.ofSecondOfDay(86399)
                            : TimeUtils.generateTime(preferences.get(FlightFilter.TIME_ARRIVE));

            LocalDateTime arriveBefore = LocalDateTime.of(dateArriveBefore, timeArriveBefore);
            if (arriveBefore.compareTo(flight.getArrivalTime()) < 0)
                return false;
        }

        return true;
    }

    public static List<Flight> getValidFlights(
            EnumMap<? extends SearchFilter, String> preferences) {
        List<Flight> out = new ArrayList<Flight>();

        for (Flight flight : Data.getInstance().getFlights()) {
            if (isValidOption(flight, preferences)) {
                out.add(flight);
            }
        }

        return out;
    }

    private static List<Flight> getLeavingFrom(String airport) {
        EnumMap<FlightFilter, String> prefs = new SearchPreferences().fpref;
        prefs.put(FlightFilter.AIRPORT_FROM, airport);
        prefs.put(FlightFilter.AIRPORT_TO, SearchPreferences.EMPTY);

        return getValidFlights(prefs);
    }

    private static List<Flight> getFromDestAirport(Flight flight) {
        EnumMap<FlightFilter, String> prefs = new EnumMap<FlightFilter, String>(FlightFilter.class);
        prefs.put(FlightFilter.AIRPORT_FROM, SearchPreferences.EMPTY);
        prefs.put(FlightFilter.AIRPORT_TO, SearchPreferences.EMPTY);

        return getValidFlights(prefs);
    }

    public static class FlightPriorityElement implements Comparable<FlightPriorityElement> {
        public Flight flight;
        private double cost;

        FlightPriorityElement(Flight flight, double cost) {
            this.cost = cost;
            this.flight = flight;
        }

        @Override
        public int compareTo(FlightPriorityElement element) {
            return Double.compare(this.cost, element.cost);
        }
    }

    private static List<Flight> findRoute(String airportFrom, String airportTo) {


        Map<Flight, Flight> prevInPath = new HashMap<>();
        Map<Flight, Double> costToReachFromStart = new HashMap<>();
        PriorityQueue<FlightPriorityElement> toExplore = new PriorityQueue<>();

        for (Flight start : getLeavingFrom(airportFrom)) {
            prevInPath.put(start, null);
            costToReachFromStart.put(start, 0.0);
            toExplore.add(new FlightPriorityElement(start, 0.0));
        }

        while (!toExplore.isEmpty()) {
            Flight currentlyExploring = toExplore.poll().flight;

            if (currentlyExploring.getAirportTo().equalsIgnoreCase(airportTo)) {
                // flight found so reconstruct path by walking backwards through prevInPath
                // starting
                // from the end
                List<Flight> path = new ArrayList<Flight>();

                Flight currentFlight = currentlyExploring;
                while (currentFlight != null) {
                    path.add(0, currentFlight);
                    currentFlight = prevInPath.get(currentFlight);
                }

                return path;
            }

            for (Flight next : getLeavingFrom(currentlyExploring.getAirportTo())) {
                double currentNextCost = costToReachFromStart.get(currentlyExploring)
                        + currentlyExploring.distanceToDestination(next);

                if (!costToReachFromStart.containsKey(next)
                        || costToReachFromStart.get(next) > currentNextCost) {
                    costToReachFromStart.put(next, currentNextCost);

                    toExplore.add(new FlightPriorityElement(next, 0.0));
                    prevInPath.put(next, currentlyExploring);
                }
            }
        }

        return new ArrayList<Flight>();
    }

    public List<Flight> findRoute(Flight from, Flight to) {
        Map<Flight, Flight> prevInPath = new HashMap<>();
        Map<Flight, Double> costToReachFromStart = new HashMap<>();
        PriorityQueue<Flight> toExplore = new PriorityQueue<>();

        prevInPath.put(from, null);
        costToReachFromStart.put(from, 0.0);
        toExplore.add(from);

        while (!toExplore.isEmpty()) {
            Flight currentlyExploring = toExplore.poll();

            if (currentlyExploring.equals(to)) {
                // flight found so reconstruct path by walking backwards through prevInPath
                // starting
                // from the end
                List<Flight> path = new ArrayList<Flight>();

                Flight currentFlight = to;
                while (currentFlight != from) {
                    path.add(currentFlight);
                    currentFlight = prevInPath.get(currentFlight);
                }

                return path;
            }

            for (Flight next : getFromDestAirport(currentlyExploring)) {
                double currentNextCost = costToReachFromStart.get(currentlyExploring)
                        + currentlyExploring.distanceToDestination(next);

                if (!costToReachFromStart.containsKey(next)
                        || costToReachFromStart.get(next) > currentNextCost) {
                    costToReachFromStart.put(next, currentNextCost);

                    toExplore.add(next);
                    prevInPath.put(next, currentlyExploring);
                }
            }
        }

        return new ArrayList<Flight>();
    }
}
