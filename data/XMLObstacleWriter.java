package data;

import model.Obstacle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Collection;

public class XMLObstacleWriter extends XMLWriter {
	private Element obstacles;
	private Document doc;
	private Transformer transformer;
	private DOMSource source;
	private StreamResult result;

	public XMLObstacleWriter(String fileName) {
		super(fileName);

		try {
			doc = super.getDocument();
			obstacles = doc.createElement("obstacles");
			doc.appendChild(obstacles);

		} catch (Exception e) {
			e.printStackTrace();
		}

		transformer = super.getTransformer();
		source = super.getSource();
		result = super.getResult();
	}


    public void writeObstacles(Collection<Obstacle> obstacleCollection) {
        for (Obstacle o: obstacleCollection) {
            addObstacle(o);
        }
        try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
    }

	private void addObstacle(Obstacle o) {
		Element obs = doc.createElement("obstacle");
		obstacles.appendChild(obs);

		Element idE = doc.createElement("id");
		Element posXE = doc.createElement("posX");
		Element posYE = doc.createElement("posY");
		Element lengthE = doc.createElement("length");
		Element widthE = doc.createElement("width");
		Element heightE = doc.createElement("height");
		Element degreesE = doc.createElement("degree");
		Element highestXOffsetE = doc.createElement("highestXOffset");
        Element highestYOffsetE = doc.createElement("highestYOffset");

		idE.setTextContent(o.getId());
		posXE.setTextContent(String.valueOf(o.getPosX()));
		posYE.setTextContent(String.valueOf(o.getPosY()));
		lengthE.setTextContent(String.valueOf(o.getLength()));
		widthE.setTextContent(String.valueOf(o.getWidth()));
		heightE.setTextContent(String.valueOf(o.getHeight()));
		degreesE.setTextContent(String.valueOf(o.getDegrees()));
        highestXOffsetE.setTextContent(String.valueOf(o.getHeighestX() - o.getPosX()));
        highestYOffsetE.setTextContent(String.valueOf(o.getHeighestY() - o.getPosY()));

		obs.appendChild(idE);
		obs.appendChild(posXE);
		obs.appendChild(posYE);
		obs.appendChild(lengthE);
		obs.appendChild(widthE);
		obs.appendChild(heightE);
		obs.appendChild(degreesE);
		obs.appendChild(highestXOffsetE);
		obs.appendChild(highestYOffsetE);

	}
}
