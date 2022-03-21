package database;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * Manages Utils.IO
 */
public class IO {
    private static IO instance;
    private final Data data;

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
        StringBuilder builder = new StringBuilder();
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
     * Writes all data to the disk
     */
    public void SaveData() {}

    /**
     * Reads all data from the disk
     */
    public void LoadData() {}
}
