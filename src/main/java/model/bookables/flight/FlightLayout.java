package model.bookables.flight;

import java.util.List;

import model.bookables.Bookable;
import model.bookables.BookingLayout;

public class FlightLayout extends BookingLayout {
    /**
     * Constructor for a layout of flights
     * @param placeHolderArg Just a place holder arguement so that something (idk what yet) can be passed into the loadOptions
     */
    public FlightLayout(String placeHolderArg) {
        super(BookingLayout.loadOptions(placeHolderArg));
    }

    
}
