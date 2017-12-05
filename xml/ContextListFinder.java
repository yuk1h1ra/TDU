import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContextListFinder {
    ArrayList<String> findContextList(String urlString, String word) {
        // 取り出した文字列を格納するための ArrayList を生成する
        ArrayList<String> contextList = new ArrayList<String>();
        // 引数で指定された URL の Webページを1行ずつ読み込む
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(), "UTF-8"));
            // 語 word の出現場所は以下の2通りを想定
            // ・要素の内容  <a href="...">microsoft word 2016</a>
            // ・属性値  <img alt="microsoft word 2016"/>
            Pattern pattern = Pattern.compile("[^>\"]*" + word + "[^<\"]*");
            String line;
            while ((line = reader.readLine()) != null) {
                // 単語 word があったら周辺の文字列ごと取り出す
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    // 取り出した文字列は ArrayList に入れる
                    contextList.add(matcher.group());	// マッチした部分全体
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最後に ArrayList を return する
        return  contextList;
    }

    public static void main(String[] args) {
        ContextListFinder finder = new ContextListFinder();
        ArrayList<String> contextList = finder.findContextList("https://www.w3.org", "W3C");
        for(String context : contextList) {
            System.out.println(context);
        }
    }
}
