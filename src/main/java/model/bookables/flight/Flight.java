package model.bookables.flight;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.BookingLayout;
import model.bookables.TravelObject;
import search.filters.FlightFilter;
import utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jack Hyatt
 */
public class Flight extends TravelObject {
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
        this.departureTime = TimeUtils.genDateTime(object.get("date") + " " + ((String)object.get("time_depart")).substring(0, 5));
        this.arrivalTime = TimeUtils.genDateTime(object.get("date") + " " + ((String)object.get("time_arrive")).substring(0, 5));
        this.bookables = (List<Bookable>) object.get("seats");
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
                "departureTime:" + departureTime +
                ", arrivalTime:" + arrivalTime +
                "} " + super.toString();
    }
}
