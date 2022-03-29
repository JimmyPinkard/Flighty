package model.bookables.hotel;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.TravelObject;
import utils.TimeUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jack Hyatt
 */
public class Room extends Bookable {
    private String info;
    private List<LocalDate> bookedDays;
    protected int sleepingCapacity;
    private Hotel hotel;

    /**
     * Constructor for room
     */
    public Room(int floor, String roomNum, int sleepingCapacity, TravelObject travelObject) {
        super(floor, roomNum, travelObject);
        info = "A Bookables.Hotel.Hotel.Room";
        bookedDays = new ArrayList<LocalDate>();
        this.sleepingCapacity = sleepingCapacity;
    }

    @SuppressWarnings("unchecked")
    public Room(DBObject object, TravelObject travelObject) {
        super((int) object.get("num"), (String) object.get("floor"), object, travelObject);
        TimeUtils timeUtils = TimeUtils.getInstance();
        info = (String) object.get("bedInfo");
        this.sleepingCapacity = (int) object.get("bedCount");
        bookedDays = new ArrayList<>();
        for(String day : (List<String>)object.get("bookedDates")) {
            bookedDays.add(timeUtils.generateDate(day));
        }
    }

    /**
     * Bogus constructor for Room.
     * for testing purposes only
     * @author rengotap
     */
    public Room(int row, String col, Hotel obj) {
        super(row, col, obj);
        this.hotel = obj;
        id = "Test case";
        price = 999;
        info = "Two queen beds";
    }


    /**
     * Method to add days that are booked for a room
     * @param start the start day to be booked
     * @param end the end day to be booked
     * @return true if it booked, false otherwise. Would mainly return false when there was a date already booked
     */
    public boolean setBookedDays(LocalDate start, LocalDate end) {
      return true;  
    }

    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Method to get the room number
     * @return room number as an integer
     */
    public int getRoomNum() {
        return this.row;
    }

    /**
     * Method to get room info
     * @return room infor as a string
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * Method to get the days the room is booked
     * @return List of days the room is booked
     */
    public List<LocalDate> getBookedDays() {
        return this.bookedDays;
    }

    public int getSleepingCapacity() {
        return sleepingCapacity;
    }

    @Override
    public String toString() {
        return "{" +
                "bookable:" + super.toString() +
                "info:'" + info + '\'' +
                ", numBeds:" + sleepingCapacity +
                ", bookedDays:" + bookedDays +
                "}";
    }
}
