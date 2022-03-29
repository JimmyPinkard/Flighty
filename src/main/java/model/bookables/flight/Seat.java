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

    /**
     * Constructor for seat
     */
    public Seat(int seatRow, String seatCol, Flight travelObject) {
        super(seatRow, seatCol, travelObject);
        whichClass = "First Class";
        price = 124;
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
        return "{" + "\"whichClass\": \"" + whichClass + "\", "
                + "\"owner\": " + owner + ", "
                + "\"id\": \"" + id + "\", "
                + "\"row\": " + row + ", "
                + "\"col\": \"" + col + "\", "
                + "\"price\": " + price + ", "
                + "\"isBooked\": " + isBooked + "}";
    }
}