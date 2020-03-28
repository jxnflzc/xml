package per.jxnflzc.utils;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

public class XMLUtil {
    private int MAJOR = 0;
    private int MINOR = 1;
    private int BUILD = 1;
    private Version version = new Version(MAJOR, MINOR, BUILD);

    public String getVersion(){
        return this.version.toString();
    }

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
