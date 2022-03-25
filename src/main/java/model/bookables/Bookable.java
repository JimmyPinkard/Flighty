package model.bookables;

import dev.morphia.annotations.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity("Bookable")
public abstract class Bookable {
    @Id
    protected String id;
    @Property("num")
    protected int num;
    @Property("price")
    protected double price;
    @Property("amenities")
    protected List<String> amenities;

    /**
     * Constructor for bookable
     */
    public Bookable() {
        this.id = UUID.randomUUID().toString();
        amenities = new ArrayList<String>();
    }

    public Bookable(JSONObject json) {
        fromJSON(json);
    }

    /**
     * Gets bookable's ID
     * @return UUID
     */
    protected String getId() {
        return this.id;
    }

    /**
     * Turns object into JSON
     * @return
     */
    protected JSONObject toJSON() {
        return null;
    }

    /**
     * Constructs object from JSON
     * @param json
     */
    protected void fromJSON(final JSONObject json) {
    }
}