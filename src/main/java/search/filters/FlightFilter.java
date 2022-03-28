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
    TIME_DEPART_EARLIEST,
    DATE_DEPART_EARLIEST, 
    TIME_ARRIVE_LATEST, 
    DATE_ARRIVE_LATEST,
    PETS_ALLOWED,
    LAYOVERS,
}
