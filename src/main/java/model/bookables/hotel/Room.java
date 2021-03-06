package model.bookables.hotel;

import com.mongodb.DBObject;
import model.bookables.Bookable;
import model.bookables.TravelObject;
import utils.CollectionUtils;
import utils.TimeUtils;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 * @author Jack Hyatt
 */
public class Room extends Bookable {
    private String info;
    private Set<LocalDate> bookedDays;
    private int sleepingCapacity;

    public Room(int floor, String roomNum, int sleepingCapacity, Set<LocalDate> bookedDays,
            TravelObject travelObject) {
        super(floor, roomNum, travelObject);
        info = "A Room";
        this.bookedDays = bookedDays;
        this.sleepingCapacity = sleepingCapacity;
    }

    public Room(int floor, String roomNum, int sleepingCapacity, TravelObject travelObject) {
        super(floor, roomNum, travelObject);
        info = "A Room";
        bookedDays = new HashSet<>();
        this.sleepingCapacity = sleepingCapacity;
    }

    /*
    public Room(DBObject object, TravelObject travelObject) {
        super((int) object.get("num"), (String) object.get("floor"), object, travelObject);
        TimeUtils timeUtils = TimeUtils.getInstance();
        info = (String) object.get("bedInfo");
        this.sleepingCapacity = (int) object.get("bedCount");
        bookedDays = new HashSet<>();
        for (String day : (List<String>) object.get("bookedDates")) {
            bookedDays.add(timeUtils.generateDate(day));
        }
    }
    */

    @SuppressWarnings("unchecked")
    public Room(DBObject object, TravelObject travelObject) {
        super((int) object.get("row"), (String) object.get("col"), object, travelObject);
        TimeUtils timeUtils = TimeUtils.getInstance();
        info = (String) object.get("info");
        this.sleepingCapacity = (int) object.get("sleepingCapacity");
        bookedDays = new HashSet<>();
        for (String day : (List<String>) object.get("bookedDays")) {
            bookedDays.add(timeUtils.generateDate(day));
        }
    }

    public boolean isBooked(LocalDate from, LocalDate to) {
        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            if (bookedDays.contains(date)) {
                return true;
            }
        }
        return false;
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
        return (Hotel) travelObject;
    }

    /**
     * Method to get the room number
     * 
     * @return room number as an integer
     */
    public int getRoomNum() {
        return this.row;
    }

    /**
     * Method to get room info
     * 
     * @return room infor as a string
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * Method to get the days the room is booked
     * 
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
        TimeUtils timeUtils = TimeUtils.getInstance();
        return "{" + "\"id\": \"" + id + "\", "
                + "\"info\": \"" + info + "\", "
                + "\"bookedDays\": " + CollectionUtils.stringArray(bookedDays.stream().map(timeUtils::toString).toArray()) + ", "
                + "\"sleepingCapacity\": " + sleepingCapacity + ", "
                + "\"row\": " + row + ", "
                + "\"col\": \"" + col + "\", "
                + "\"price\": " + price + "}";
    }
}
