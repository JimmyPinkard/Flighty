package model.bookables;

import org.json.JSONObject;
import search.filters.SearchFilter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public abstract class TravelObject {
    @Id
    protected UUID id;
    protected List<Bookable> bookables;
    protected String company;
    protected double cost;
    protected double rating;
    protected BookingLayout layout;
    protected List<String> features;
    protected List<SearchFilter> filters;

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

    public TravelObject(JSONObject json) {
        fromJSON(json);
    }

    public TravelObject() {

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

    /**
     * Turns object into JSON
     * @return
     */
    protected JSONObject toJSON() {
        //TODO: Implement
        return null;
    }

    /**
     * Constructs object from JSON
     * @param json
     */
    protected void fromJSON(final JSONObject json) {
        //TODO: Implement
    }

    public List<SearchFilter> getFilters() {
        return filters;
    }

    public void setFilters(final List<SearchFilter> filters) {
        //TODO: Implement
    }

}
