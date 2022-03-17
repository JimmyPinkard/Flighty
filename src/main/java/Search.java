import java.util.ArrayList;
import java.util.List;
/**
 * Search abstract class
 */
public abstract class Search {

    protected SearchFilter possibleFilters;
    protected SearchPreferences prefrences;

    public Search() {
        //TODO: Search Constructor
    }

    /**
     * Search Function
     * @param filters
     * @return
     */
    public List<Listing> search(List<SearchFilter> filters) {
        //TODO: Search Function
        return new ArrayList<Listing>();
    }
}