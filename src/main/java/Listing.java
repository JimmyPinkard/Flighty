/**
 * Wrapper for travel objects
 */
public class Listing {
    
    private String company;
    private double rating;
    private TravelObject object;

    /**
     * Generates a new listing
     * @param company name of the company
     * @param rating  number of stars
     * @param object  travel object
     */
    public Listing(String company, double rating, TravelObject object) {
        this.company = company;
        this.rating = rating;
        this.object = object;
    }

    /**
     * Gets company name
     * @return company as a string
     */
    public String getCompany() {
        return company;
    }

    /**
     * gets rating
     * @return rating as a double
     */
    public double getRating() {
        return rating;
    }

    /**
     * gets the travel object
     * @return the object held by the listing
     */
    public TravelObject getObject() {
        return object;
    }

    /**
     * Prints the listing as a string
     */
    public String toString() {
        //TODO: Listing toString
        return "temp";
    }
}
