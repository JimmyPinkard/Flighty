import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class IO {
    private IO instance;
    private Data data;

    private IO() {}

    public static IO getInstance() {
        return new IO();
    }

    private JSONObject readFile(String path) {
        return new JSONObject();
    }

    private List<String> readDirectory(String path) {
        return new ArrayList<String>();
    }

    private void writeFile(String path, JSONObject object) {}

    public void SaveData() {}

    public void LoadData() {}
}
