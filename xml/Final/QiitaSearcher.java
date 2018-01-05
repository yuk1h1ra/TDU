package Final;

import java.util.ArrayList;

class QiitaSearcher {
    public static void main(String[] args) {
        Feed feed = new Feed();
        feed.setURL("https://usn.ubuntu.com/usn/rss.xml");
        // feed.setURL("https://qiita.com/tags/"+ keyword +"/feed.atom");
        feed.run();
        ArrayList<Item> itemList = feed.getItemList();
        for(Item item: itemList) {
            System.out.println(item.getTitle());
        }
        // Feed feed = new Feed();
        // feed.setURL("https://qiita.com/tags/"+ "" +"/feed.atom");
    }
}
