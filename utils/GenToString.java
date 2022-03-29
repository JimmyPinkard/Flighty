import java.util.*;

public class GenToString {
    private static IO io;
    public static void main(final String[] args) {
        if(args.length < 2) {
            System.err.println("No args");
            System.exit(1);
        }
        io = new IO();
        String output = "";
        if(args[0].equals("-f")) {
            String contents = io.readFile(args[1]);
            System.out.println(getVariables(contents).toString());
        }
        else if(args[0].equals("-d")) {
            try {
                Map<String, String> files = io.readDir(args[1]);
                List<Variable> vars = new ArrayList<>();
                for(Map.Entry<String, String> file : files.entrySet()) {
                    vars.add(getVariables(file.getValue()));
                }
                for(Variable variable : vars) {
                    for(Variable sub : vars) {
                        if(variable.parent.equals(sub.name)) {
                            variable.addParent(sub);
                            break;
                        }
                    }
                    output += variable.genClass() + "\n\n";
                }
                io.writeFile("Test.java", output);
            }
            catch (Exception e) {
                fatal(e.getMessage());
            }
        }
    }

    private static Variable getVariables(String contents) {
        int braces = 0, start = 0;
        List<String> variables = new ArrayList<>();
        boolean dontSkip = true;
        String firstLine = "";
        for(int i = 0; i < contents.length(); ++i) {
            char c = contents.charAt(i);
            if(braces == 0) {
                firstLine += c;
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
        start = firstLine.indexOf(" ", firstLine.indexOf("class")) + 1;
        String name = firstLine.substring(start, firstLine.indexOf(" ", start)), parent;
        start = firstLine.indexOf("extends", start);
        if(start != -1) {
            start = firstLine.indexOf(" ", start) + 1;
            parent = firstLine.substring(start, firstLine.indexOf(" ", start));
        }
        else {
            parent = "";
        }
        return new Variable(name, parent, variables);
    }

    public static void fatal(final String message) {
        System.err.println(message);
        System.exit(1);
    }
}