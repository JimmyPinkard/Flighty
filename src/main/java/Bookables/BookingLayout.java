package Bookables;

import Bookables.Bookable;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the user viewable layout of bookables for a travelobject
 */
public abstract class BookingLayout {
    /**
     * All possible bookable objects that may or may not be avalable
     */
    protected List<Bookable> options;

    /**
     * String that shows the layout and avalable options of the travelobject
     */
    public abstract String toString();

    /**
     * Get an option based on the layout string
     */
    public Bookable getOption(int x, int y) {
        return new Bookable() {};
    }

    public List<Bookable> getOptions() {
        return new ArrayList<Bookable>();
    }
}
