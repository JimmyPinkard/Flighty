package model.bookables.flight;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import model.bookables.TravelObject;

public class FlightTrip extends TravelObject {
    private List<Flight> flights;

    public FlightTrip(List<Flight> flights) {
        super();
        this.flights = flights;
    }

    public double getCost() {
        double total = 0;
        for (Flight flight : flights) {
            total += flight.getCost();
        }
        return total;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public int getAmountTransfers() {
        return flights.size() - 1;
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
