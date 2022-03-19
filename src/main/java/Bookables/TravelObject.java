package Bookables;

import Bookables.Bookable;
import Bookables.BookingLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class TravelObject {
    protected List<Bookable> bookables;
    protected String company;
    protected double cost;
    protected double rating;
    protected BookingLayout layout;
    protected List<String> features;

/**
 * Constructor for Bookables.TravelObject
 * @param layout the layout of either hotel or flight
 */
    public TravelObject(BookingLayout layout) {
        this.company = "";
        this.cost = 0;
        this.rating = 0;
        this.bookables = new ArrayList<Bookable>();
        this.features = new ArrayList<String>();
        this.layout = layout;
    }

    /**
     * Method to get the bookables
     * @return the List of bookables options
     */
    public List<Bookable> getOptions(){
        return bookables;
    }

    /**
     * Books a bookable by removing it from lists of bookables.
     * @return true if booking was successful, false if it wasnt
     */
    public boolean book(Bookable booking){
        return bookables.remove(booking);
    }

}
