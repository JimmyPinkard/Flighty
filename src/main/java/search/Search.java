package search;

import java.util.ArrayList;
import java.util.List;
import model.bookables.TravelObject;
import model.users.SearchPreferences;

public interface Search {
    public static List<? extends TravelObject> execute(
            SearchPreferences preferences) {
        return new ArrayList<TravelObject>();
    }
}
