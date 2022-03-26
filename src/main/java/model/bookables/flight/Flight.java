package model.bookables.flight;

import com.mongodb.DBObject;
import model.bookables.BookingLayout;
import model.bookables.TravelObject;
import search.filters.FlightFilter;
import utils.TimeUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Hyatt
 */
public class Flight extends TravelObject {
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private List<FlightFilter> filters;

    /**
     * Constructor for Bookables.Flight.Flight
     */
    public Flight(LocalTime departureTime, LocalTime arrivalTime, BookingLayout layout) {
        super(layout);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight(DBObject object) {
        super(object);
        this.filters = new ArrayList<>();
        this.departureTime = TimeUtils.generateTime(((String)object.get("time_depart")).substring(0, 5));
        this.arrivalTime = TimeUtils.generateTime(((String)object.get("time_arrive")).substring(0, 5));
    }

    /**
     * 
     * @return the departure time of the flight as a LocalDate data type
     */
    public LocalTime getDepartureTime() {
        return this.departureTime;
    }

    /**
     * 
     * @return the arival time of the flight as a LocalDate data type
     */
    public LocalTime getArrivalTime() {
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
