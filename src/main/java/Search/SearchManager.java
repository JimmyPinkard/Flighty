package Search;

import Search.Filters.SearchFilter;
import Bookables.Listing;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages searching
 */
public class SearchManager {
    private SearchFilter filter;
    private Search search;

    public SearchManager() {}

    public void setSearch(Search search) {}

    public List<Listing> getSearchResults() {
        return new ArrayList<Listing>();
    }

    public void addFilter(SearchFilter filter) {}

    public void removeFilter(SearchFilter filter) {}

    public SearchFilter getFilters() {
        //ToDo: stub
        return null;
    }
}
