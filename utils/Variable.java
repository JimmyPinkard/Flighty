import java.util.*;
import java.io.*;

public class Variable {
    public String name;
    public String parent;
    public List<String> attributes;

    public Variable(String name, String parent, List<String> attributes) {
        this.name = name;
        this.parent = parent;
        this.attributes = attributes;
    }

    public void addParent(Variable parent) {
        this.attributes.addAll(parent.attributes);
    }

    public String genJSON() {
        StringBuilder builder = new StringBuilder("\"{\" + ");
        for(String attribute : attributes) {
            builder.append("\"\\\"").append(attribute).append("\\\": \\\"\" + ").append(attribute).append(" + \"\\\", \"\n\t\t + ");
        }
        return builder.delete(builder.length() - 9, builder.length()).append("}\";").toString();
    }

    public String genToString() {
        return "@Override\n\tpublic String toString() {\n\t\t return " + genJSON() + "\n\t}";
    }

    public String genClass() {
        return "public class " + name + " {\n\t" + genToString() + "\n}";
    }
}