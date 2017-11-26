import java.io.*;
import java.net.*;
import java.util.regex.*;

public class ShowHeadingsRegEx {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("使用法: java ShowHeadings URL");
            System.out.println("  例  : java ShowHeadings https://www.google.com");
            System.exit(0);
        }

        try {
            URL url = new URL(args[0]);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);

            Pattern pattern = Pattern.compile("<h[123456]>.+</h[123456]>");
            String line;
            while((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while(matcher.find()) {
                    System.out.println(matcher.group());
                }
            }
            reader.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
