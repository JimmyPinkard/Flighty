import java.time.LocalDate;

public class Flight {
    private LocalDate departureTime;
    private LocalDate arivalTime;

    /**
     * Constructor for Flight
     */
    public Flight(LocalDate departureTime, LocalDate arivalTime) {
        this.departureTime = departureTime;
        this.arivalTime = arivalTime;
    }
    


}
