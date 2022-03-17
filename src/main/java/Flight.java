import java.time.LocalDate;

/**
 * @author Jack Hyatt
 */
public class Flight extends TravelObject{
    private LocalDate departureTime;
    private LocalDate arivalTime;

    /**
     * Constructor for Flight
     */
    public Flight(LocalDate departureTime, LocalDate arivalTime,BookingLayout layout) {
        super(layout);
        this.departureTime = departureTime;
        this.arivalTime = arivalTime;
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
    public LocalDate getArivalTime() {
        return this.arivalTime;
    }
}
