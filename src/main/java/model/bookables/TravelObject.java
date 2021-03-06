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
    protected List<String> features;
    protected List<SearchFilter> filters;

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
    }

    private SearchFilter getFilter(Object filter) {
        for (SearchFilter flightFilter : FlightFilter.values()) {
            if (filter.equals(flightFilter.toString())) {
                return flightFilter;
            }
        }
        for (SearchFilter hotelFilter : HotelFilter.values()) {
            if (filter.equals(hotelFilter.toString())) {
                return hotelFilter;
            }
        }
        return null;
    }

    protected TravelObject(List<Bookable> bookables, String company, double rating) {
        this.id = UUID.randomUUID().toString();
        this.company = company;
        this.rating = rating;
        this.bookables = bookables;
        this.features = new ArrayList<String>();
        this.filters = new ArrayList<>();
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

    public String getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public double getMinCost() {
        double minPrice = Integer.MAX_VALUE;
        for (Bookable bookable : bookables) {
            if (bookable.price < minPrice) {
                minPrice = bookable.price;
            }
        }

        return minPrice;
    }

    public double getMaxCost() {
        double maxPrice = 0;
        for (Bookable bookable : bookables) {
            if (bookable.price > maxPrice) {
                maxPrice = bookable.price;
            }
        }
        return maxPrice;
    }

    public double getAvgCost() {
        double avgPrice = 0;
        for (Bookable bookable : bookables) {
                avgPrice = avgPrice + bookable.price;
        }

        return avgPrice/((double)bookables.size());
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
                ", features:" + features +
                ", filters:" + filters +
                '}';
    }
}
