package search;

import search.filters.SearchFilter;
import model.bookables.Listing;
import model.users.SearchPreferences;

import java.util.ArrayList;
import java.util.List;
/**
 * Search.Search abstract class
 */
public class Search {

    protected List<SearchFilter> possibleFilters;
    protected SearchPreferences preferences;
    protected List<Listing> travelObjects;

    /**
     *
     */
    public Search() {
        //TODO: Search.Search Constructor
    }

    /**
     * search.Search Function
     * @param filters
     * @author Jimmy
     * @return
     */
    public List<Listing> search(final List<SearchFilter> filters) {
        //TODO: Search.Search Function
        return filter(filters);
    }

    /**
     * The searching algorithm
     * @return
     * @author Jimmy
     */
    private List<Listing> filter(final List<SearchFilter> filters) {
        final List<Listing> filteredList = new ArrayList<>();
        for(final Listing listing : this.travelObjects) {
            if(hasFilters(listing, filters)) {
                filteredList.add(listing);
            }
        }
        return filteredList;
    }

    /**
     * Needs finishing
     * @param listing
     * @param filters
     * @return
     * @author Jimmy
     */
    private boolean hasFilters(final Listing listing, final List<SearchFilter> filters) {
        /*
        for(final SearchFilter filter : filters) {
            if() {
                return false;
            }
        }
        */
        return true;
    }
}