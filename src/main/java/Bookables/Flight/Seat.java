package Bookables.Flight;

import Bookables.Bookable;

/**
 * 
 * @author Jack Hyatt
 */
public class Seat extends Bookable {
    private String whichClass;
    private boolean isBooked;

    /**
     * Constructor for seat
     */
    public Seat() {
        super();
        whichClass = "First CLass";
        isBooked= false;
    }

    /**
     * Method to set the booking of the seat
     * @return true if it booked, false if already booked
     */
    public boolean book() {
        if(isBooked)
            return false;
        isBooked = true;
        return true;
    }

    /**
     * Method to get the seat number
     * @return seat number as an integer
     */
    public int getSeatNum() {
        return this.num;
    }

    /**
     * Method to get seat class
     * @return the class the seat in is as a string
     */
    public String getSeatClass() {
        return this.whichClass;
    }

    /**
     * Method to get if the seat is booked
     * @return the boolean of if the seat is booked
     */
    public boolean getIsBooked() {
        return this.isBooked;
    }
}