import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jack Hyatt
 */
public class Room extends Bookable{
    private String info;
    private List<LocalDate> bookedDays;

    /**
     * Constructor for room
     */
    public Room() {
        super();
        info = "A Room";
        bookedDays = new ArrayList<LocalDate>();
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

    /**
     * Method to get the room number
     * @return room number as an integer
     */
    public int getRoomNum() {
        return this.num;
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

    
}