package Search;

import Search.Filters.SearchFilter;
import Bookables.Listing;
import Users.SearchPreferences;

import java.util.ArrayList;
import java.util.List;
/**
 * Search.Search abstract class
 */
public abstract class Search {

    protected List<SearchFilter> possibleFilters;
    protected SearchPreferences preferences;
    protected List<Listing> travelObjects;
    public Search() {
        //TODO: Search.Search Constructor
    }

    /**
     * Search.Search Function
     * @param filters
     * @return
     */
    public List<Listing> search(List<SearchFilter> filters) {
        //TODO: Search.Search Function
        return new ArrayList<Listing>();
    }
}