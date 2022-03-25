package model.bookables.hotel;

import model.bookables.BookingLayout;

public class HotelLayout extends BookingLayout {
    /**
     * Constructor for layout of hotel rooms
     * @param placeholderArg a place holder to be changed to whatever will be used to call the rooms from the database.
     */
    public HotelLayout(String placeholderArg) {
        super(BookingLayout.loadOptions(placeholderArg));
    }

}
