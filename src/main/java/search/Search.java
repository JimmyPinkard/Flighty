package search;

import search.filters.SearchFilter;
import model.bookables.Listing;
import model.users.info.SearchPreferences;

import java.util.ArrayList;
import java.util.List;
/**
 * Search.Search abstract class
 */
public class Search {

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