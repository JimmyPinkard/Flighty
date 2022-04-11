package utils;
import java.util.Iterator;
import java.util.Map;

public class Diagnostics {
    private static Diagnostics instance; 

    public Diagnostics() {
        
    }

    public void printMap(Map m) {
        Iterator temp = m.entrySet().iterator();
        while(temp.hasNext()) {
            Map.Entry mapElement = (Map.Entry)temp.next();
            System.out.println(mapElement.getKey() + " : " + m.get(mapElement.getKey()));
        }
    }

    public static Diagnostics getInstance() {
        if (instance == null) {
            instance = new Diagnostics();
        }
        return instance;
    }

}
