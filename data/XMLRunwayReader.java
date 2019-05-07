package data;

import exceptions.InvalidDataBaseFileException;
import exceptions.InvalidDataBaseFormatException;
import model.Designator;
import model.Runway;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class XMLRunwayReader extends XMLReader {
    public XMLRunwayReader(String fileName) throws InvalidDataBaseFileException {
        super(fileName);
    }


    public List<Runway> getAll() throws InvalidDataBaseFormatException {
        List<Runway> runways = new ArrayList<>();
        Element rootNode = super.getRootNode();
        if (rootNode != null) {
            NodeList runwayNL = rootNode.getElementsByTagName("runway");
            for (int counter = 0; counter < runwayNL.getLength(); counter++) {
                Node runwayN = runwayNL.item(counter);
                Element runwayE = (Element) runwayN;
                //String[] elementList = runwayE.getTextContent().replaceAll("\\s", ",").split(",,,");

                // Created input of runway using a processed list of strings. This is temporary
                //int direction = Integer.valueOf(elementList[1]);
                //Designator designator = Designator.fromString(elementList[2]);
                //int tora = Integer.valueOf(elementList[3]);
                //int toda = Integer.valueOf(elementList[4]);
                //int asda = Integer.valueOf(elementList[5]);
                //int lda = Integer.valueOf(elementList[6]);
                //int displacementThreshold = Integer.valueOf(elementList[7]);
                //int resa = Integer.valueOf(elementList[8]);
                //int stripEnd = Integer.valueOf(elementList[9].replace(",,", ""));

                // NOTE: This is a hacky fix. Just to ensure we get something working for testing

                int direction = Integer.parseInt(runwayE.getElementsByTagName("direction").item(0).getTextContent());
                Designator designator = Designator.fromString(runwayE.getElementsByTagName("designator").item(0).getTextContent());
                int tora = Integer.parseInt(runwayE.getElementsByTagName("tora").item(0).getTextContent());
                int toda = Integer.parseInt(runwayE.getElementsByTagName("toda").item(0).getTextContent());
                int asda = Integer.parseInt(runwayE.getElementsByTagName("asda").item(0).getTextContent());
                int lda = Integer.parseInt(runwayE.getElementsByTagName("lda").item(0).getTextContent());
                int displacementThreshold = Integer.parseInt(runwayE.getElementsByTagName("displacementThreshold").item(0).getTextContent());
                int resa = Integer.parseInt(runwayE.getElementsByTagName("resa").item(0).getTextContent());
                int stripEnd = Integer.parseInt(runwayE.getElementsByTagName("stripEnd").item(0).getTextContent());
                runways.add(new Runway(direction, designator, tora, toda, asda, lda, displacementThreshold, resa, stripEnd));
            }
        }
        return runways;
    }
}
