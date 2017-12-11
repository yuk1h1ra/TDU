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

import javax.xml.xpath.*;

public class ItemSearcherByWordInTitleXPath {
    public static void main(String[] args) {
        ItemSearcherByWordInTitleXPath viewer = new ItemSearcherByWordInTitleXPath();
        try {
            // InputStreamの用意
            URL url = new URL("https://www.lifehacker.jp/feed/index.xml");
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            // DOMツリーの構築
            Document document = viewer.buildDocument(inputStream, "utf-8");
            // ↑↑↑  ここまでは DocumentViewer と同じ (クラス名以外は)
            XPathFactory factory = XPathFactory.newInstance();
            XPath xPath = factory.newXPath();
            NodeList titleNodeList = (NodeList)xPath.evaluate("/rss/channel/item/title", document, XPathConstants.NODESET);
            NodeList descNodeList = (NodeList)xPath.evaluate("/rss/channel/item/description", document, XPathConstants.NODESET);

            if(titleNodeList.getLength() == 0) {
                titleNodeList = (NodeList)xPath.evaluate("/RDF/item/title", document, XPathConstants.NODESET);
                descNodeList = (NodeList)xPath.evaluate("/RDF/item/description", document, XPathConstants.NODESET);
            }

            for(int i = 0; i < titleNodeList.getLength(); i++) {
                if(titleNodeList.item(i).getTextContent().contains("Amazon")) {
                    System.out.println(titleNodeList.item(i).getTextContent());
                    System.out.println(descNodeList.item(i).getTextContent());
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
