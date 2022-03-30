package model.bookables.hotel;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.TravelObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Jack Hyatt
 */
public class Room extends Bookable {
    private String info;
    private Set<LocalDate> bookedDays;
    private int sleepingCapacity;
    private Hotel hotel;

    /**
     * Constructor for room
     */
    // TODO delete b/c unused
    public Room(int floor, String roomNum, int sleepingCapacity, TravelObject travelObject) {
        super(floor, roomNum, travelObject);
        info = "A Bookables.Hotel.Hotel.Room";
        bookedDays = new HashSet<LocalDate>();
        this.sleepingCapacity = sleepingCapacity;
    }

    @SuppressWarnings("unchecked")
    public Room(DBObject object, TravelObject travelObject) {
        super((int) object.get("num"), (String) object.get("floor"), object, travelObject);
        info = (String) object.get("bedInfo");
        this.sleepingCapacity = (int) object.get("bedCount");
        bookedDays = new HashSet<>();
        bookedDays.addAll((List<LocalDate>)object.get("bookedDates"));
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

    public boolean isBooked(LocalDate from, LocalDate to) {
        for (LocalDate date = from; date.isBefore(to); date = date.plusDays(1)) {
            if (bookedDays.contains(date)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to add days that are booked for a room
     * 
     * @param from the from day to be booked
     * @param to the to day to be booked
     */
    public void bookRange(LocalDate from, LocalDate to) {
        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            bookedDays.add(date);
        }
    }

    public void unbookRange(LocalDate from, LocalDate to) {
        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            bookedDays.remove(date);
        }
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
        var l = new ArrayList<LocalDate>();
        l.addAll(bookedDays);
        return l;
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
