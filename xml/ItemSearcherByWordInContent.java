import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.*;

import java.util.ArrayList;

public class ItemSearcherByWordInContent {
    public static void main(String[] args) {
        ItemSearcherByWordInContent viewer = new ItemSearcherByWordInContent();
        try {
            // InputStreamの用意
            URL url = new URL("https://news.yahoo.co.jp/pickup/rss.xml");
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            // DOMツリーの構築
            Document document = viewer.buildDocument(inputStream, "utf-8");
            // ↑↑↑  ここまでは DocumentViewer と同じ (クラス名以外は)
            // ツリーをroot要素からたどる
            // viewer.showTree(document.getDocumentElement());
            String word = "逮捕";
            // まずitem要素のリストを得る
            NodeList list = document.getElementsByTagName("item");
            for(int i = 0; i < list.getLength(); i++) {	// 各item要素について
                Node node= list.item(i);
                String link = null;
                for(Node current = node.getFirstChild();
                        current != null;
                        current = current.getNextSibling()) {	// 1つのitem要素の各子ノードについて
                    if(current.getNodeType() == Node.ELEMENT_NODE) {
                        if(current.getNodeName().equals("link")) {
                            link = current.getFirstChild().getNodeValue();
                            ContextListFinder contextList = new ContextListFinder();
                            ArrayList<String> wordList = contextList.findContextList(link, word);
                            for(String hitWord: wordList) {
                                System.out.println(hitWord);
                                System.out.println();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** DOM Tree の構築 */
    public Document buildDocument(InputStream inputStream, String encoding) {
        Document document = null;
        try {
            // DOM実装(implementation)の用意 (Load and Save用)
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS implementation = (DOMImplementationLS)registry.getDOMImplementation("XML 1.0");
            // 読み込み対象の用意
            LSInput input = implementation.createLSInput();
            input.setByteStream(inputStream);
            input.setEncoding(encoding);
            // 構文解析器(parser)の用意
            LSParser parser = implementation.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
            parser.getDomConfig().setParameter("namespaces", false);
            // DOMの構築
            document = parser.parse(input);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }
}
