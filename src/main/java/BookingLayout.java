import java.util.ArrayList;
import java.util.List;

public abstract class BookingLayout {
    protected List<Bookable> options;

    public abstract String toString();

    public abstract Bookable getOption(int x, int y);

    public List<Bookable> getOptions() {
        return new ArrayList<Bookable>();
    }
}
