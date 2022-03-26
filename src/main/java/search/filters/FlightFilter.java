package search.filters;

/**
 * List of Bookables.Flight.Flight Filters
 * @author Rengotap
 */
public enum FlightFilter implements SearchFilter {
    FLIGHT,
    AIRPORT_FROM, 
    AIRPORT_TO,
    COMPANY,
    PRICE,
    DURATION,
    TIME_DEPART, 
    TIME_ARRIVE,
    PETS_ALLOWED,
    FLIGHTS_LAYOVER,
}
