package model.bookables.flight;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.TravelObject;
import model.users.info.Passport;

/**
 * 
 * @author Jack Hyatt
 */
public class Seat extends Bookable {
    private String whichClass;
    private Passport owner;
    private boolean isBooked;

    /**
     * Constructor for seat
     */
    public Seat(int seatRow, String seatCol, boolean isBooked, double price, Flight travelObject) {
        super(seatRow, seatCol, price, travelObject);
        whichClass = "First Class";
        isBooked = false;
    }

    public Seat(DBObject object, TravelObject travelObject) {
        super((int)object.get("row"), (String) object.get("column"), object, travelObject);
        this.whichClass = (String) object.get("class");
        this.isBooked = (boolean) object.get("isBooked");
    }
    
    public Flight getFlight() {
        return (Flight)travelObject;
    }

    /**
     * Method to get the seat number
     * @return seat number as an integer
     */
    public int getSeatNum() {
        return this.row;
    }

    /**
     * Method to set the booking of the seat
     * 
     * @return true if it booked, false if already booked
     */
    public void book() {
        isBooked = true;
    }

    /**
     * Method to unbook the seat
     * 
     * @return false if unable to unbook
     */
    public void unbook() {
        isBooked = false;
    }

    /**
     * Method to get if the bookable is booked
     * 
     * @return the boolean of if the bookable is booked
     */
    public boolean getIsBooked() {
        return this.isBooked;
    }

    /**
     * Method to get seat class
     * @return the class the seat in is as a string
     */
    public String getSeatClass() {
        return this.whichClass;
    }

    public void setOwner(Passport owner) {
        this.owner = owner;
    }

    public Passport getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "{" + "\"class\": \"" + whichClass + "\", "
                + "\"owner\": " + owner + ", "
                + "\"id\": \"" + id + "\", "
                + "\"row\": " + row + ", "
                + "\"column\": \"" + col + "\", "
                + "\"price\": " + price + ", "
                + "\"isBooked\": " + isBooked + "}";
    }
}