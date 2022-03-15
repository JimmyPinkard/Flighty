import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Bookable {
    protected UUID id;
    protected int num;
    protected double price;
    protected List<String> amenities;

    /**
     * Constructor for bookable
     */
    public Bookable() {
        this.id = UUID.randomUUID();
        amenities = new ArrayList<String>();
    }

    protected UUID getId() {
        return this.id;
    }

    protected int getNum() {
        return num;
    }

    protected double getPrice() {
        return price;
    }
}