package model.bookables;

import org.json.JSONObject;

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

    public Bookable(JSONObject json) {
        fromJSON(json);
    }

    /**
     * Gets bookable's ID
     * @return UUID
     */
    protected UUID getId() {
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