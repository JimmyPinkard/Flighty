import java.util.*;
import java.io.*;

public class GenToString {
    public static void main(final String[] args) {
        if(args.length < 2)
        {
            System.err.println("No args");
            System.exit(1);
        }
        else if(args[0].equals("-f"))
        {
            String contents = readFile(args[1]);
            System.out.println(getVariables(contents).toString());
        }
        else if(args[0].equals("-d")){
            try {
                var files = readDir(args[1]);
                for(var file : files.entrySet()) {
                    System.out.printf("%s: %s\n", file.getKey(), getVariables(file.getValue()).toString());
                }
            }
            catch (Exception e) {
                fatal(e.getMessage());
            }
        }
    }

    private static String readFile(String path) {
        String contents = "";
        try {
            Scanner in = new Scanner(new File(path));
            while(in.hasNextLine())
            {
                contents += in.nextLine();
            }
        }
        catch (Exception e) {
            fatal(e.getMessage());
        }
        return contents;
    }

    public static Map<String, String> readDir(final String dir)
    {
        Map<String, String> contents = new HashMap<>();
        try
        {
            File[] files = new File(dir).listFiles();
            assert files != null;
            for(File file : files)
            {
                boolean isDir = file.isDirectory();
                if(isDir)
                {
                    var map = readDir(file.getPath());
                    contents.putAll(map);
                }
                else
                {
                    String path = file.getPath();
                    contents.put(path, readFile(path));
                }
            }
        }
        catch (Exception e)
        {
            fatal(e.getMessage());
        }
        return contents;
    }


    private static List<String> getVariables(String contents) {
        int braces = 0, start = 0;
        List<String> variables = new ArrayList<>();
        for(int i = 0; i < contents.length(); ++i) {
            char c = contents.charAt(i);
            if(c == '{') {
                ++braces;
            }
            else if(c == '}') {
                --braces;
            }
            else if(c == ';' && braces == 1) {
                String[] arr = contents.substring(start, i).split(" ");
                variables.add(arr[arr.length - 1]);
                start = i + 1;
            }
        }
        return variables;
    }

    private static void fatal(final String message) {
        System.err.println(message);
        System.exit(1);
    }
}