package database;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.json.JSONObject;

/**
 * Manages Utils.IO
 */
@Deprecated
public class IO {
    private static IO instance;
    private final Data data;

    /**
     * HashMap for path lookups to make our lives easier
     */
    private static final Map<String, String> paths = new HashMap();
    static {
        final String root = "database/";
        paths.put("flight", root + "flights.json");
        paths.put("hotel", root + "hotels.json");
        paths.put("user", root + "userData.json");
        paths.put("booking", root + "bookings.json");
    }

    /**
     *
     */
    private IO() {
        this.data = Data.getInstance();
    }

    /**
     * Ensures there is only one Utils.IO instance by getting the Utils.IO class instance if one already exists,
     * otherwise it returns a creates a new one
     */
    public static IO getInstance() {
        if(instance == null) {
            instance = new IO();
        }
        return instance;
    }

    /**
     * Parses JSON file
     * @param path
     * @author Jimmy
     */
    private JSONObject readFile(final String path) {
        final StringBuilder builder = new StringBuilder();
        try {
            final Scanner in = new Scanner(path);
            while(in.hasNextLine()) {
                builder.append(in.nextLine());
            }
        }
        catch (Exception exception) {
            System.err.println(exception.getCause().getMessage());
            System.exit(1);
        }
        return new JSONObject(builder.toString());
    }

    /**
     * Gets contents of all files in a directory
     * @param path
     * @author Jimmy
     */
    private List<String> readDirectoryContents(final String path) {
        final List<String> contents = new ArrayList<String>();
        final File dir = new File(path);
        if(!dir.isDirectory()) {
            return contents;
        }
        final File[] files = dir.listFiles();
        if(files == null) {
            return contents;
        }
        for(File file : files) {
            if(file.isDirectory()) {
                contents.addAll(readDirectoryContents(file.getPath()));
                continue;
            }
            contents.add(readFile(file.getPath()).toString());
        }
        return contents;
    }

    /**
     * Writes JSON to a file
     * @param path
     * @param object
     * @author Jimmy
     */
    private void writeJSONtoFile(final String path, final JSONObject object) {
        try {
            final PrintWriter writer = new PrintWriter(path);
            writer.write(object.toString());
        }
        catch (IOException exception) {
            System.err.println(exception.getCause().getMessage());
            System.exit(1);
        }
    }

    /**
     * Writes JSON to a file
     * @param path
     * @param object
     * @author Jimmy
     */
    private void writeJSONtoFile(final String path, final String object) {
        try {
            final PrintWriter writer = new PrintWriter(path);
            writer.write(object.toString());
        }
        catch (IOException exception) {
            System.err.println(exception.getCause().getMessage());
            System.exit(1);
        }
    }

    /**
     * Writes all data to the disk
     */
    public void SaveData() {
        saveList(paths.get("flight"), Collections.singletonList(this.data.getFlights()));
        saveList(paths.get("hotel"), Collections.singletonList(this.data.getHotels()));
        saveList(paths.get("booking"), Collections.singletonList(this.data.bookings));
        saveList(paths.get("user"), Collections.singletonList(this.data.users));
    }

    /**
     * Reads all data from the disk
     * Needs a lot of improvement, but should work
     * @author Jimmy
     */
    public void LoadData() {
        for(final Map.Entry<String, String> entry : paths.entrySet()) {
            final List<String> contents = readDirectoryContents(entry.getValue());
            final List<JSONObject> objects = new ArrayList<>();
            for(final String text : contents) {
                objects.add(new JSONObject(text));
            }
            final String key = entry.getKey();
            loadObjects(key, objects);
        }
    }

    /**
     * Utility function for loadData
     * @param objects
     * @author Jimmy
     */
    private void loadObjects(final String key, final List<JSONObject> objects) {
        /*
        if(key.equalsIgnoreCase("flight")) {
            for(JSONObject object : objects) {
                this.data.travelObjects.add(new Flight(object));
            }
        }
        else if(key.equalsIgnoreCase("hotel")) {
            for(JSONObject object : objects) {
                this.data.travelObjects.add(new Hotel(object));
            }
        }
        else if(key.equalsIgnoreCase("user")) {
            for(JSONObject object : objects) {
                this.data.users.add(new User(object));
            }
        }
        else if(key.equalsIgnoreCase("booking")) {
            for(JSONObject object : objects) {
                this.data.bookings.add(new Booking(object));
            }
        }
        */
    }

    /**
     * Utility function for saveData
     * @param path
     * @param objects
     * @author Jimmy
     */
    private void saveList(final String path, final List<Object> objects) {
        for(Object object : objects) {
            writeJSONtoFile(path, object.toString());
        }
    }
}
