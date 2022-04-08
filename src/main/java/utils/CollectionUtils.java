package utils;

public class CollectionUtils {
    public static String stringArray(Object[] arr) {
        if(arr.length == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(Object str : arr) {
            builder.append("\"").append(str).append("\", ");
        }
        return builder.delete(builder.length() - 2, builder.length()).append("]").toString();
    }
}
