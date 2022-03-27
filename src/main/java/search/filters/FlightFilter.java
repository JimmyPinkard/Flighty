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
    DATE_DEPART, 
    DATE_ARRIVE,
    TIME_EARLIEST,
    TIME_LATEST,
    PETS_ALLOWED,
    FLIGHTS_LAYOVER,
}
