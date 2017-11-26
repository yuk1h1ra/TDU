import java.io.*;
import java.net.*;

public class ShowHeadings {
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

            String line;
            while((line = reader.readLine()) != null) {
                int beginIndex = -1;
                int endIndex = -1;
                for(int i = 1; i <= 6; i++) {
                    beginIndex = line.indexOf("<h" + Integer.toString(i) + ">");
                    endIndex = line.indexOf("</h" + Integer.toString(i) + ">");
                    if(beginIndex != -1 && endIndex != -1) {
                        String hNum = line.substring(beginIndex, endIndex + 5);
                        System.out.println(hNum);
                    }
                }
            }
            reader.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
