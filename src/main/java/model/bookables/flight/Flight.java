package model.bookables.flight;

import com.mongodb.DBObject;
import org.bson.BsonInt32;
import model.bookables.Bookable;
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
    private double startX;
    private double startY;
    private double stopX;
    private double stopY;

    /**
     * Constructor for Bookables.Flight.Flight
     */
    public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, String airportFrom,
            String airportTo, BookingLayout layout) {
        super(layout);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airportFrom = airportFrom;
        this.airportTo = airportTo;
    }

    public Flight(DBObject object) {
        super(object);
        TimeUtils timeUtils = new TimeUtils();
        this.departureTime = timeUtils.genDateTime(object.get("date_depart") + " "
                + ((String) object.get("time_depart")).substring(0, 5));
        this.arrivalTime = timeUtils.genDateTime(object.get("date_arrive") + " "
                + ((String) object.get("time_arrive")).substring(0, 5));
        this.startX = (double) object.get("from_x");
        this.startY = (double) object.get("from_y");
        this.stopX = (double) object.get("to_x");
        this.stopY = (double) object.get("to_y");
        this.bookables = (List<Bookable>) object.get("seats");
        this.airportFrom = (String) object.get("airport_code_from");
        this.airportTo = (String) object.get("airport_code_to");
    }

    public double distanceToDestination(Flight flight) {
        double a2 = Math.pow(this.startX - flight.startX, 2);
        double b2 = Math.pow(this.stopX - flight.stopX, 2);

        return Math.sqrt(a2 + b2);
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
        return "{" + "travelObject: " + super.toString() + ", airportFrom:'" + airportFrom + '\''
                + ", airportTo:'" + airportTo + '\'' + ", departureTime:" + departureTime + "UTC"
                + ", arrivalTime:" + arrivalTime + "UTC" + ", filters:" + filters + "}";
    }
}
