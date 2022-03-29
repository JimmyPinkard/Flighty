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

    public static List<FlightTrip> execute(SearchPreferences preferences) {
        preferences = preferences.clone();
        for (var pref : preferences.fpref.keySet()) {
            if (preferences.fpref.get(pref).equalsIgnoreCase(SearchPreferences.ANY)) {
                preferences.fpref.put(pref, SearchPreferences.EMPTY);
            }
        }

        List<FlightTrip> out = new ArrayList<FlightTrip>();

        // layovers from Scenario 2
        for (String amountLayovers : new String[] {"0", "1", "1", "2"}) {
            preferences.fpref.put(FlightFilter.LAYOVERS, amountLayovers);

            List<Flight> route = findRoute(preferences, out);
            if (!route.isEmpty())
                out.add(new FlightTrip(route));
        }

        return out;
    }


    // TODO account for "any"
    // TODO other filters
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

        if (!preferences.get(FlightFilter.PEOPLE).equalsIgnoreCase(SearchPreferences.EMPTY)
                && Integer.parseInt(preferences.get(FlightFilter.PEOPLE)) > flight
                        .getNumAvailableSeats())
            return false;

        if (!preferences.get(FlightFilter.DATE_DEPART_EARLIEST)
                .equalsIgnoreCase(SearchPreferences.EMPTY)) {
            LocalDate dateDepartAfter = preferences.get(FlightFilter.DATE_DEPART_EARLIEST)
                    .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalDate.ofEpochDay(0L)
                            : TimeUtils.getInstance().generateDate(
                                    preferences.get(FlightFilter.DATE_DEPART_EARLIEST));

            LocalTime timeDepartAfter = preferences.get(FlightFilter.TIME_DEPART_EARLIEST)
                    .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalTime.ofSecondOfDay(0)
                            : TimeUtils.getInstance()
                                    .generateTime(
                                            preferences.get(FlightFilter.TIME_DEPART_EARLIEST));

            LocalDateTime departAfter = LocalDateTime.of(dateDepartAfter, timeDepartAfter);
            if (departAfter.compareTo(flight.getDepartureTime()) > 0)
                return false;
        }

        if (!preferences.get(FlightFilter.DATE_ARRIVE_LATEST)
                .equalsIgnoreCase(SearchPreferences.EMPTY)) {
            LocalDate dateArriveBefore = preferences.get(FlightFilter.DATE_ARRIVE_LATEST)
                    .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalDate.ofEpochDay(0L)
                            : TimeUtils.getInstance()
                                    .generateDate(preferences.get(FlightFilter.DATE_ARRIVE_LATEST));

            LocalTime timeArriveBefore = preferences.get(FlightFilter.TIME_ARRIVE_LATEST)
                    .equalsIgnoreCase(SearchPreferences.EMPTY) ? LocalTime.ofSecondOfDay(86399)
                            : TimeUtils.getInstance()
                                    .generateTime(preferences.get(FlightFilter.TIME_ARRIVE_LATEST));

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

    public static class FlightPriorityElement implements Comparable<FlightPriorityElement> {
        public Flight flight;
        public int stepsFromStart;
        private double cost;

        FlightPriorityElement(Flight flight, double cost, int stepsFromStart) {
            this.cost = cost;
            this.flight = flight;
            this.stepsFromStart = stepsFromStart;
        }

        @Override
        public int compareTo(FlightPriorityElement element) {
            return Double.compare(this.cost, element.cost);
        }
    }

    private static List<Flight> findRoute(SearchPreferences preferences,
            List<FlightTrip> excludingTrips) {
        Map<Flight, Flight> prevInPath = new HashMap<>();
        Map<Flight, Double> costToReachFromStart = new HashMap<>();
        PriorityQueue<FlightPriorityElement> toExplore = new PriorityQueue<>();

        var startPrefs = preferences.clone().getFPref();
        startPrefs.put(FlightFilter.AIRPORT_TO, SearchPreferences.EMPTY);
        for (Flight start : getValidFlights(startPrefs)) {
            prevInPath.put(start, null);
            costToReachFromStart.put(start, 0.0);
            toExplore.add(new FlightPriorityElement(start, 0.0, 0));
        }

        while (!toExplore.isEmpty()) {
            var currElement = toExplore.poll();
            Flight currentlyExploring = currElement.flight;
            int currentStepsFromStart = currElement.stepsFromStart;

            if (!preferences.getFPref().get(FlightFilter.LAYOVERS)
                    .equalsIgnoreCase(SearchPreferences.EMPTY)
                    && Integer.parseInt(preferences.getFPref()
                            .get(FlightFilter.LAYOVERS)) < currentStepsFromStart)
                continue;

            String airportTo = preferences.getFPref().get(FlightFilter.AIRPORT_TO);
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

                boolean skipPath = false;
                for (var trip : excludingTrips)
                    if (path.equals(trip.getFlights()))
                        skipPath = true;
                if (skipPath)
                    continue;

                return path;
            }

            var prefs = preferences.clone().getFPref();
            prefs.put(FlightFilter.AIRPORT_FROM, currentlyExploring.getAirportTo());
            prefs.put(FlightFilter.AIRPORT_TO, SearchPreferences.EMPTY);
            prefs.put(FlightFilter.DATE_DEPART_EARLIEST, TimeUtils.getInstance()
                    .toString(currentlyExploring.getArrivalTime().toLocalDate()));
            prefs.put(FlightFilter.TIME_DEPART_EARLIEST, TimeUtils.getInstance()
                    .toString(currentlyExploring.getArrivalTime().toLocalTime()));

            for (Flight next : getValidFlights(prefs)) {
                double currentNextCost = costToReachFromStart.get(currentlyExploring)
                        + currentlyExploring.distanceToDestination(next);

                if (!costToReachFromStart.containsKey(next)
                        || costToReachFromStart.get(next) > currentNextCost) {
                    costToReachFromStart.put(next, currentNextCost);

                    toExplore.add(new FlightPriorityElement(next, 0.0, currentStepsFromStart + 1));
                    prevInPath.put(next, currentlyExploring);
                }
            }
        }

        return new ArrayList<Flight>();
    }

}
