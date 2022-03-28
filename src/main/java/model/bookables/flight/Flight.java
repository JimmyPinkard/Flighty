package model.bookables.flight;

import com.mongodb.DBObject;
import model.bookables.BookingLayout;
import model.bookables.TravelObject;
import search.filters.FlightFilter;
import utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Hyatt
 */
public class Flight extends TravelObject {
    private String airportFrom;
    private String airportTo;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<FlightFilter> filters;

    /**
     * Constructor for Bookables.Flight.Flight
     */
    public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, BookingLayout layout) {
        super(layout);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight(DBObject object) {
        super(object);
        final TimeUtils timeUtils = TimeUtils.getInstance();
        this.departureTime = timeUtils.genDateTime(
                object.get("date") + " " + ((String) object.get("time_depart")).substring(0, 5));
        this.arrivalTime = timeUtils.genDateTime(
                object.get("date") + " " + ((String) object.get("time_arrive")).substring(0, 5));
        this.airportFrom = (String) object.get("airport_code_from");
        this.airportTo = (String) object.get("airport_code_to");
        this.bookables = new ArrayList<>();
        var seats = (List<DBObject>) object.get("seats");
        for(DBObject seat : seats) {
            this.bookables.add(new Seat(seat));
        }
    }

    /**
     * Bogus constructor for flight.
     * for testing purposes only
     * @author rengotap
     */
    public Flight() {
        super(new FlightLayout("test"));
        airportFrom = "ATL";
        airportTo = "LAX";
    }


    public String getAirportFrom() {
        return airportFrom;
    }

    public String getAirportTo() {
        return airportTo;
    }

    /**
     * 
     * @return the departure time of the flight as a LocalDate data type
     */
    public LocalDateTime getDepartureTime() {
        return this.departureTime;
    }

    /**
     * 
     * @return the arival time of the flight as a LocalDate data type
     */
    public LocalDateTime getArrivalTime() {
        return this.arrivalTime;
    }

    @Override
    public String toString() {
        return "{" +
                "travelObject: " + super.toString() +
                ", airportFrom:'" + airportFrom + '\'' +
                ", airportTo:'" + airportTo + '\'' +
                ", departureTime:" + departureTime + "UTC" +
                ", arrivalTime:" + arrivalTime + "UTC" +
                ", filters:" + filters +
                "}";
    }
}
