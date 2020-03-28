package per.jxnflzc.utils;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

public class XMLUtil {
    public static Object getBean(String path, String filename, String tagName) {
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new File(path + "/" + filename + ".xml"));

            NodeList nl = doc.getElementsByTagName(tagName);
            Node classNode = nl.item(0).getFirstChild();
            String cName = classNode.getNodeValue();

            Class c = Class.forName(cName);
            Object obj = c.newInstance();
            return obj;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
