package data;

import model.Aircraft;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Collection;

public class XMLAircraftWriter extends XMLWriter{
	private Element aircrafts;
	private Document doc;
	private Transformer transformer;
	private DOMSource source;
	private StreamResult result;

    public XMLAircraftWriter(String fileName) {
        super(fileName);

		try {
			doc = super.getDocument();
			aircrafts = doc.createElement("aircrafts");
			doc.appendChild(aircrafts);

		} catch (Exception e) {
			e.printStackTrace();
		}

		transformer = super.getTransformer();
		source = super.getSource();
		result = super.getResult();
	}

    public void writeAircraft(Collection<Aircraft> l) {
        for (Aircraft a : l) {
            addAircraft(a);
        }
        try {

            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private void addAircraft(Aircraft a) {

		Element aircraft = doc.createElement("aircraft");
		aircrafts.appendChild(aircraft);

		Element idE = doc.createElement("id");
		Element blastAllowanceE = doc.createElement("blastAllowance");

        idE.setTextContent(a.getId());
        blastAllowanceE.setTextContent(String.valueOf(a.getBlastAllowance()));

		aircraft.appendChild(idE);
		aircraft.appendChild(blastAllowanceE);


	}
}
