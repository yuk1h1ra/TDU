import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

public class ItemSearcherByWordInTitleXPath {
    public static void main(String[] args) {
        ItemSearcherByWordInTitleXPath viewer = new ItemSearcherByWordInTitleXPath();
        try {
            // InputStreamの用意
            URL url = new URL("https://qiita.com/tags/vim/feed.atom");
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            // DOMツリーの構築
            Document document = viewer.buildDocument(inputStream, "utf-8");
            // ↑↑↑  ここまでは DocumentViewer と同じ (クラス名以外は)
            // ツリーをroot要素からたどる
            // viewer.showTree(document.getDocumentElement());
            String word = "首相";
            // まずitem要素のリストを得る
            NodeList list = document.getElementsByTagName("item");
            for(int i = 0; i < list.getLength(); i++) {	// 各item要素について
                Node  node= list.item(i);
                String title = null;
                String link = null;
                for(Node current = node.getFirstChild();
                        current != null;
                        current = current.getNextSibling()) {	// 1つのitem要素の各子ノードについて
                    if(current.getNodeType() == Node.ELEMENT_NODE) {
                        if(current.getNodeName().equals("title")) {
                            //title = current.getTextContent();
                            title = current.getFirstChild().getNodeValue();
                        }
                        else if(current.getNodeName().equals("link")) {
                            link = current.getTextContent();
                        }
                        // title, link 以外の要素の場合には何もしない
                    }
                    // 要素以外 (テキストなど) の場合には何もしない
                }
                if(title.contains(word)) {		// 指定された語 word が title 要素に含まれていたら
                    System.out.println(title);
                    System.out.println(link);
                    System.out.println();
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
