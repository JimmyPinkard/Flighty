public class IO {
    public void writeFile(String path, String contents) {
        try {
            PrintWriter writer = new PrintWriter(new File(path));
            writer.write(contents);
        }
        catch (Exception e) {
            fatal(e.getMessage());
        }
    }

    public String readFile(String path) {
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

    public Map<String, String> readDir(final String dir) {
        Map<String, String> contents = new HashMap<>();
        try {
            File[] files = new File(dir).listFiles();
            assert files != null;
            for(File file : files) {
                boolean isDir = file.isDirectory();
                if(isDir) {
                    var map = readDir(file.getPath());
                    contents.putAll(map);
                }
                else {
                    String path = file.getPath(), fileName = path.substring(path.lastIndexOf("/") + 1);
                    contents.put(fileName, readFile(path));
                }
            }
        }
        catch (Exception e) {
            fatal(e.getMessage());
        }
        return contents;
    }
}