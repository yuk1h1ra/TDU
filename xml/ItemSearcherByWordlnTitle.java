import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

import java.util.ArrayList;

public class ItemSearcherByWordlnTitle {
    public static void main(String[] args) {
        ItemSearcherByWordlnTitle finder = new ItemSearcherByWordlnTitle();
        try {
            // InputStreamの用意
            //URL url = new URL("https://itunes.apple.com/us/rss/topmusicvideos/limit=10/xml");
            URL url = new URL("https://news.yahoo.co.jp/pickup/rss.xml");
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            // DOMツリーの構築
            Document document = finder.buildDocument(inputStream, "utf-8");
            finder.searchTitle(document.getDocumentElement(), "北");
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
    /** 引数 node 以下の tree を表示 */
    public void findContextList(Node node, String word) {
        for(Node current = node.getFirstChild();
                current != null;
                current = current.getNextSibling()) {
            if(current.getNodeType() == Node.ELEMENT_NODE) { // ノードは要素?
                String nodeName = current.getNodeName();
                findContextList(current, word); // さらに子ノードを見る (再帰)
            }
            else if(current.getNodeType() == Node.TEXT_NODE // ノードはテキスト?
                    && current.getNodeValue().trim().length() != 0) {
                if(current.getNodeValue().matches(".*" + word + ".*")) {
                    System.out.println(current.getNodeValue());
                }
            }
            else if(current.getNodeType() == Node.CDATA_SECTION_NODE) { // ノードはCDATA?
                if(current.getNodeValue().matches(".*" + word + ".*")) {
                    System.out.println(current.getNodeValue());
                }
            } // HTMLタグなどを含む
            ; // 上記以外のノードでは何もしない
        }
    }

    public void searchTitle(Node node, String word) {
        for(Node current = node.getFirstChild();
                current != null;
                current = current.getNextSibling()) {
            if(current.getNodeType() == Node.ELEMENT_NODE) { // ノードは要素?
                searchTitle(current, word); // さらに子ノードを見る (再帰)
                if(current.getNodeName() == "title") {
                    findContextList(current, word);
                }
            }
            else if(current.getNodeType() == Node.TEXT_NODE // ノードはテキスト?
                    && current.getNodeValue().trim().length() != 0) {
                if(current.getNodeValue().matches(".*" + word + ".*")) {
                    System.out.println(current.getNodeValue());
                }
            }
            else if(current.getNodeType() == Node.CDATA_SECTION_NODE) { // ノードはCDATA?
                if(current.getNodeValue().matches(".*" + word + ".*")) {
                    System.out.println(current.getNodeValue());
                }
            } // HTMLタグなどを含む
            ; // 上記以外のノードでは何もしない
        }
    }
}
