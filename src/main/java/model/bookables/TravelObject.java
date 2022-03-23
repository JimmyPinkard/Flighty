package model.bookables;

import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import search.filters.SearchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TravelObject {
    @Id
    protected String id;
    @Property("bookables")
    protected List<Bookable> bookables;
    @Property("company")
    protected String company;
    @Property("cost")
    protected double cost;
    @Property("rating")
    protected double rating;
    @Property("layout")
    protected BookingLayout layout;
    @Property("features")
    protected List<String> features;
    @Property("filters")
    protected List<SearchFilter> filters;

/**
 * Constructor for Bookables.TravelObject
 * @param layout the layout of either hotel or flight
 */
    public TravelObject(BookingLayout layout) {
        this.id = UUID.randomUUID().toString();
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



    public List<SearchFilter> getFilters() {
        return filters;
    }

    public void setFilters(final List<SearchFilter> filters) {
        //TODO: Implement
    }

}
