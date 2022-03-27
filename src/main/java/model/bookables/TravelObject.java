package model.bookables;

import com.mongodb.DBObject;
import search.filters.SearchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TravelObject {
    protected String id;
    protected List<Bookable> bookables;
    protected String company;
    protected double rating;
    protected BookingLayout layout;
    protected List<String> features;
    protected List<SearchFilter> filters;

/**
 * Constructor for Bookables.TravelObject
 * @param layout the layout of either hotel or flight
 */
    public TravelObject(BookingLayout layout) {
        this.id = UUID.randomUUID().toString();
        this.company = "";
        this.rating = 0;
        this.bookables = new ArrayList<Bookable>();
        this.features = new ArrayList<String>();
        this.layout = layout;
        this.filters = new ArrayList<>();
    }

    public TravelObject(DBObject object) {
        this.id = (String) object.get("id");
        this.company = (String) object.get("company");
        this.rating = (Double) object.get("rating");
        this.features = (List<String>) object.get("features");
        this.filters = (List<SearchFilter>) object.get("filters");
        this.layout = null;
    }

    protected TravelObject() {
        this.id = UUID.randomUUID().toString();
        this.company = "";
        this.rating = 0;
        this.bookables = new ArrayList<Bookable>();
        this.features = new ArrayList<String>();
        this.filters = new ArrayList<>();
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

    public String getCompany() {
        return company;
    }

    public double getCost() {
        double minPrice = Integer.MAX_VALUE;
        for (Bookable bookable : bookables) {
            if (bookable.price < minPrice) {
                minPrice = bookable.price;
            }
        }

        return minPrice;
    }

    public List<SearchFilter> getFilters() {
        return filters;
    }

    public void setFilters(final List<SearchFilter> filters) {
        //TODO: Implement
    }

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", bookables:" + bookables +
                ", company:'" + company + '\''
                +
                ", rating:" + rating +
                ", layout:" + layout +
                ", features:" + features +
                ", filters:" + filters +
                '}';
    }
}
