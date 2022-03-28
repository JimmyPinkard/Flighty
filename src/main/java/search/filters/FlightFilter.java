package search.filters;

/**
 * List of Bookables.Flight.Flight Filters
 * @author Rengotap
 */
public enum FlightFilter implements SearchFilter {
    AIRPORT_FROM, 
    AIRPORT_TO,
    COMPANY,
    PRICE,
    DURATION,
    TIME_EARLIEST,
    TIME_LATEST,
    TIME_DEPART, 
    TIME_ARRIVE,
    DATE_DEPART, DATE_ARRIVE,
    PETS_ALLOWED,
    LAYOVERS,
}
