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
    PEOPLE,
    TIME_DEPART_EARLIEST,
    DATE_DEPART_EARLIEST, 
    TIME_ARRIVE_LATEST, 
    DATE_ARRIVE_LATEST,
    LAYOVERS,
}
