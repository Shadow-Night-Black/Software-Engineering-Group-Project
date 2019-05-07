package data;

import exceptions.InvalidDataBaseFileException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public abstract class XMLReader {
    private Element root;

    public XMLReader(String fileName) throws InvalidDataBaseFileException {
        File f = new File(fileName);
        if (f.exists() && f.isFile() && f.canRead()) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            try {
                docBuilder = dbFactory.newDocumentBuilder();
                // dummyXMLFile.xml placed in data package to enable ease of getResourceAsStream for time being
                Document doc = docBuilder.parse(f);
                doc.normalize();

                // Changed "airport" to "environment", as per the XML file.
                //NodeList rootNodes = doc.getElementsByTagName("environments");
                //Element airportsE = (Element) rootNodes.item(0);

                root = doc.getDocumentElement();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                throw new InvalidDataBaseFileException(fileName);
            } catch (SAXException e) {
                e.printStackTrace();
                throw new InvalidDataBaseFileException(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                throw new InvalidDataBaseFileException(fileName);
            }
        }

	}

    public Element getRootNode() {
        return root;
    }
}
