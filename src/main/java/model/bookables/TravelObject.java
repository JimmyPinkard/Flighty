package model.bookables;

import com.mongodb.DBObject;
import search.filters.FlightFilter;
import search.filters.HotelFilter;
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
     * 
     * @param layout the layout of either hotel or flight
     */
    public TravelObject(BookingLayout layout) {
        this.id = UUID.randomUUID().toString();
        this.company = "";
        this.rating = 0;
        this.bookables = new ArrayList<>();
        this.features = new ArrayList<>();
        this.layout = layout;
        this.filters = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public TravelObject(DBObject object) {
        this.id = (String) object.get("id");
        this.company = (String) object.get("company");
        this.rating = (Double) object.get("rating");
        this.features = new ArrayList<>();
        var temp = (List<String>) object.get("features");
        this.features.addAll(temp);
        this.filters = new ArrayList<>();
        var temp2 = (List<DBObject>) object.get("filters");
        for (Object filter : temp2) {
            this.filters.add(getFilter(filter));
        }
        this.layout = null;
    }

    private SearchFilter getFilter(Object filter) {
        for (SearchFilter flightFilter : FlightFilter.values()) {
            if (filter.equals(flightFilter.toString())) {
                return flightFilter;
            }
        }
        for (SearchFilter hotelFilters : HotelFilter.values()) {
            if (filter.equals(hotelFilters)) {
                return hotelFilters;
            }
        }
        return null;
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
     * 
     * @return the List of bookables options
     */
    public List<? extends Bookable> getOptions() {
        return bookables;
    }

    /**
     * Books a bookable by removing it from lists of bookables.
     * 
     * @return true if booking was successful, false if it wasnt
     */
    public boolean book(Bookable booking) {
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

    public double getRating() {
        return rating;
    }

    public List<SearchFilter> getFilters() {
        return filters;
    }

    public void setFilters(final List<SearchFilter> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", bookables:" + bookables +
                ", company:'" + company + '\'' +
                ", rating:" + rating +
                ", layout:" + layout +
                ", features:" + features +
                ", filters:" + filters +
                '}';
    }
}
