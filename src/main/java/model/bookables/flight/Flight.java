package model.bookables.flight;

import com.mongodb.DBObject;
import model.bookables.BookingLayout;
import model.bookables.TravelObject;
import search.filters.FlightFilter;
import utils.GenDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Hyatt
 */
public class Flight extends TravelObject {
    private LocalDate departureTime;
    private LocalDate arrivalTime;
    private List<FlightFilter> filters;

    /**
     * Constructor for Bookables.Flight.Flight
     */
    public Flight(LocalDate departureTime, LocalDate arrivalTime, BookingLayout layout) {
        super(layout);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight(DBObject json) {
        super(json);
        this.filters = new ArrayList<>();
        //this.departureTime = GenDate.generateDate((String)json.get("time_depart"));// LocalDate.parse();
        //this.arrivalTime = GenDate.generateDate((String)json.get("time_arrive"));
    }

    /**
     * 
     * @return the departure time of the flight as a LocalDate data type
     */
    public LocalDate getDepartureTime() {
        return this.departureTime;
    }

    /**
     * 
     * @return the arival time of the flight as a LocalDate data type
     */
    public LocalDate getArrivalTime() {
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
