package data;

import model.LogicalRunway;
import model.Runway;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Collection;

public class XMLRunwayWriter extends XMLWriter{
	
	private Element runways;
	private Document doc;
	private Transformer transformer;
	private DOMSource source;
	private StreamResult result;
	public XMLRunwayWriter(String fileName) {
		super(fileName);
		
		try {
			doc = super.getDocument();
			runways = doc.createElement("runways");
			doc.appendChild(runways);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		transformer = super.getTransformer();
		source = super.getSource();
		result = super.getResult();
	}


    public void writeRunways(Collection<Runway> l) {
        for(Runway r: l) {
            writeRunway(r);
        }

        try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
    }

	private void writeRunway(Runway r) {
        LogicalRunway l = r.getEastFacing();
		Element runwayElement = doc.createElement("runway");
		runways.appendChild(runwayElement);

        Element directionE = doc.createElement("direction");
        Element designatorE = doc.createElement("designator");
        Element toraE = doc.createElement("tora");
		Element todaE = doc.createElement("toda");
		Element asdaE = doc.createElement("asda");
		Element ldaE = doc.createElement("lda");
		Element displacementThresholdE = doc
				.createElement("displacementThreshold");
		Element resaE = doc.createElement("resa");
		Element stripEndE = doc.createElement("stripEnd");

		directionE.setTextContent(String.valueOf(l.getDirection()));
		designatorE.setTextContent(l.getDesignator().toString());
		toraE.setTextContent(String.valueOf(l.getTora()));
		todaE.setTextContent(String.valueOf(l.getToda()));
		asdaE.setTextContent(String.valueOf(l.getAsda()));
		ldaE.setTextContent(String.valueOf(l.getLda()));
		displacementThresholdE.setTextContent(String.valueOf(l.getDisplacementThreshold()));
		resaE.setTextContent(String.valueOf(l.getResa()));
		stripEndE.setTextContent(String.valueOf(l.getStripEnd()));

		runwayElement.appendChild(directionE);
		runwayElement.appendChild(designatorE);
		runwayElement.appendChild(toraE);
		runwayElement.appendChild(todaE);
		runwayElement.appendChild(asdaE);
		runwayElement.appendChild(ldaE);
		runwayElement.appendChild(displacementThresholdE);
		runwayElement.appendChild(resaE);
		runwayElement.appendChild(stripEndE);
	}

	
}
