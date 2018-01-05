package Final;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.net.URL;
import java.net.HttpURLConnection;

class QiitaSearcher {
    public static void main(String[] args) {
        // archFeedよりarchのRSS取得
        Feed archFeed = new Feed();
        archFeed.setURL("https://www.archlinux.jp/feeds/packages.xml");
        archFeed.run();
        ArrayList<Item> archItemList = archFeed.getItemList();
        HashSet<String> packageList = new HashSet<String>();
        HashSet<String> notFoundPackage = new HashSet<String>();
        for(Item item: archItemList) {
            packageList.add(item.getTitle().split("[ -]")[0]);
        }
        for(String packageName: packageList) {
            InputStream inputStream = null;
            try {
                URL url = new URL("https://qiita.com/tags/"+ packageName +"/feed.atom");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)");
                connection.connect();
                inputStream = connection.getInputStream();
            }
            catch(IOException e) {
                notFoundPackage.add(packageName);
            }
        }
        // Qiitaで記事が見つからないものを削除
        for(String notPackageName: notFoundPackage) {
            packageList.remove(notPackageName);
        }
        // Qiitaで検索
        Feed qiitaFeed = new Feed();
        for(String packageName: packageList) {
            System.out.println(packageName);
            qiitaFeed.setURL("https://qiita.com/tags/"+ packageName +"/feed.atom");
            qiitaFeed.run();
            ArrayList<Item> qiitaItemList = qiitaFeed.getItemList();
            // packageの名前で検索されたタイトル表示
            ArrayList<String> postedList = new ArrayList<String>();
            for(Item item: qiitaItemList) {
                postedList.add(item.getTitle());
            }
            Collections.sort(postedList);
            for(String title: postedList) {
                System.out.println(title);
            }
            System.out.println("");
        }
    }
}
