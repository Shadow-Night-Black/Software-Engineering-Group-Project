package data;

import exceptions.InvalidDataBaseFileException;
import model.Aircraft;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class XMLAircraftReader extends XMLReader {

	public XMLAircraftReader(String fileName) throws InvalidDataBaseFileException {
		super(fileName);
    }

	public List<Aircraft> getAll() {
        List<Aircraft> aircrafts = new ArrayList<>();
		try {
            Element rootNode = super.getRootNode();

            if (rootNode != null) {
                NodeList aircraftNL = rootNode.getElementsByTagName("aircraft");
                for (int counter = 0; counter < aircraftNL.getLength(); counter++) {
                        Node aircraftN = aircraftNL.item(counter);
                        Element aircraftE = (Element) aircraftN;
                    int blastAllowance = Integer.parseInt(aircraftE.getElementsByTagName("blastAllowance").item(0).getTextContent());
                    String id = aircraftE.getElementsByTagName("id").item(0).getTextContent();
                            aircrafts.add(new Aircraft(id, blastAllowance));
                    }
                }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return aircrafts;
	}
}
