import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Manages searching
 */
public class SearchManager {
    private Filter filter;
    private Search search;

    public SearchManager() {}

    public void setSearch(Search search) {}

    public List<Listing> getSearchResults() {
        return new ArrayList<Listing>();
    }

    public void addFilter(Filter filter) {}

    public void removeFilter(Filter filter) {}

    public Filter getFilters() {}
}
