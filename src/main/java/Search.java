import java.util.ArrayList;
import java.util.List;
/**
 * Search abstract class
 */
public abstract class Search {

    protected Enum possibleFilters;
    protected SearchPreferences prefrences;

    public Search() {
        //TODO: Search Constructor
    }

    /**
     * Search Function
     * @param filters
     * @return
     */
    public List<Listing> search(List<Enum> filters) {
        //TODO: Search Function
        return new ArrayList<Listing>();
    }
}