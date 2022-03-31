package utils;

import java.util.List;

public class CollectionUtils {
    public static String stringList(List<Object> list) {
        if(list.size() == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(Object str : list) {
            builder.append("\"").append(str).append("\", ");
        }
        return builder.delete(builder.length() - 2, builder.length()).append("]").toString();
    }
}
