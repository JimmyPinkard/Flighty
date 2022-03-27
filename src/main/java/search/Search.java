package search;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import model.bookables.TravelObject;
import search.filters.SearchFilter;

public interface Search {
    public static List<? extends TravelObject> execute(
            EnumMap<? extends SearchFilter, String> preferences) {
        return new ArrayList<TravelObject>();
    }
}
