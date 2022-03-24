package model.bookables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
     * Constructor for the booking layout
     */
    public BookingLayout(List<Bookable> options) {
        Collections.sort(options);
        this.options = options;
    }

    /**
     * String that shows the layout and avalable options of the travelobject
     * (Notes for James): each bookable has it's row as an int, and its column as a string. Bookable's toString is just {row}{col}.
     * So like a seat would be 13B and a room would be 165.
     */
    public String toString(){
        Iterator<Bookable> itr = options.iterator();
        
        return "";
    }

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
