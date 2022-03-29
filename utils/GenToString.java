import java.util.*;
import java.io.*;

public class GenToString {
    private static IO io;
    public static void main(final String[] args) {
        if(args.length < 2) {
            System.err.println("No args");
            System.exit(1);
        }
        io = new IO();
        if(args[0].equals("-f")) {
            String contents = io.readFile(args[1]);
            System.out.println(getVariables(contents).toString());
        }
        else if(args[0].equals("-d")) {
            try {
                Map<String, String> files = io.readDir(args[1]);
                for(Map.Entry<String, String> file : files.entrySet()) {
                    System.out.printf("%s: %s\n", file.getKey(), getVariables(file.getValue()).toString());
                }
            }
            catch (Exception e) {
                fatal(e.getMessage());
            }
        }
    }

    private static List<String> getVariables(String contents) {
        int braces = 0, start = 0;
        List<String> variables = new ArrayList<>();
        boolean dontSkip = true;
        String parent = "";
        for(int i = 0; i < contents.length(); ++i) {
            char c = contents.charAt(i);
            if(braces == 0) {
                parent += c;
            }
            if(c == '{') {
                ++braces;
            }
            else if(c == '}') {
                --braces;
            }
            else if(c == '=') {
                dontSkip = false;
            }
            else if(c == ';' && braces == 1) {
                if(dontSkip) {
                    String[] arr = contents.substring(start, i).split(" ");
                    variables.add(arr[arr.length - 1]);
                }
                else {
                    dontSkip = true;
                }
                start = i + 1;
            }
        }
        //Contains
        start = parent.indexOf("extends");
        if(start != -1) {
            start = parent.indexOf(" ", start) + 1;
            parent = parent.substring(start, parent.indexOf(" ", start));
        }
        return variables;
    }

    private static void fatal(final String message) {
        System.err.println(message);
        System.exit(1);
    }
}