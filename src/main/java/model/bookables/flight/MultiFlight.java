package model.bookables.flight;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class MultiFlight {
    private List<Flight> flights;

    public MultiFlight(List<Flight> flights) {
        this.flights = flights;
    }

    public double getCost() {
        double total = 0;
        for (Flight flight : flights) {
            total += flight.getCost();
        }
        return total;
    }

    public Duration getTravelTime() {
        return Duration.between(getDepartureTime(), getArrivalTime());
    }

    public LocalDateTime getDepartureTime() {
        return flights.get(0).getDepartureTime();
    }

    public LocalDateTime getArrivalTime() {
        return flights.get(flights.size() - 1).getArrivalTime();
    }
}
