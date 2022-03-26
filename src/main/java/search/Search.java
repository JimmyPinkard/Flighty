package search;

import java.util.EnumMap;
import java.util.List;
import database.Data;
import model.bookables.TravelObject;
import search.filters.SearchFilter;

public interface Search {
    public abstract List<TravelObject> execute(Data data,
            EnumMap<? extends SearchFilter, String> preferences);
}
