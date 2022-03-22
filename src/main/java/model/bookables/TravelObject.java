package model.bookables;

import search.filters.SearchFilter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@MappedSuperclas
public abstract class TravelObject implements Serializable {
    @Id
    @Column(name = "id")
    protected String id;
    protected List<Bookable> bookables;
    @Column(name = "company")
    protected String company;
    @Column(name = "cost")
    protected double cost;
    @Column(name = "rating")
    protected double rating;
    protected BookingLayout layout;
    @Column(name = "features")
    protected List<String> features;
    @Column(name = "filters")
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
