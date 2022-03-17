import java.util.List;
/**
 * Hotel object
 * @author rengotap
 */
public class Hotel extends TravelObject {

    private List<String> features;
    private String location;

    /**
     * Creates a new hotel object
     */
    public Hotel(List<String> features, String location, BookingLayout layout) {
        super(layout);
        this.features = features;
        this.location = location;
    }

    /**
     * Gets the hotel's features
     * @return List of features
     */
    public List<String> getFeatures() {
        return features;
    }

    /**
     * Gets the hotel's location
     * @return location
     */
    public String getLocation() {
        return location;
    }

}