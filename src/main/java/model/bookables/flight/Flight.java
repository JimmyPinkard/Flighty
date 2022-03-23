package model.bookables.flight;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Property;
import model.bookables.BookingLayout;
import model.bookables.TravelObject;

import java.time.LocalDate;

/**
 * @author Jack Hyatt
 */
@Entity("Flights")
public class Flight extends TravelObject {
    @Property("departure")
    private LocalDate departureTime;
    @Property("arrival")
    private LocalDate arrivalTime;

    /**
     * Constructor for Bookables.Flight.Flight
     */
    public Flight(LocalDate departureTime, LocalDate arrivalTime, BookingLayout layout) {
        super(layout);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
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
}
