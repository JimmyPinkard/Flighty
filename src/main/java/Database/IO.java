package Database;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * Manages Utils.IO
 */
public class IO {
    private IO instance;
    private Data data;

    private IO() {}

    /**
     * Ensures there is only one Utils.IO instance by getting the Utils.IO class instance if one already exists,
     * otherwise it returns a creates a new one
     */
    public static IO getInstance() {
        return new IO();
    }

    /**
     * Parses JSON file
     */
    private JSONObject readFile(String path) {
        return new JSONObject();
    }

    /**
     * Gets files in a directory
     */
    private List<String> readDirectoryContents(String path) {
        return new ArrayList<String>();
    }

    private void writeJSONtoFile(String path, JSONObject object) {}

    /**
     * Writes all data to the disk
     */
    public void SaveData() {}

    /**
     * Reads all data from the disk
     */
    public void LoadData() {}
}
