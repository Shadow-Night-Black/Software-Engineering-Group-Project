package data;

import exceptions.InvalidDataBaseFileException;
import model.Obstacle;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class XMLObstacleReader extends XMLReader {
	public XMLObstacleReader(String fileName) throws InvalidDataBaseFileException {
		super(fileName);
	}
	public List<Obstacle> getAll(){
        List<Obstacle> obstacles = new ArrayList<>();
		try{

            Element rootNode = super.getRootNode();

            if (rootNode != null) {
                NodeList obstacleNL = rootNode.getElementsByTagName("obstacle");
                for (int counter = 0; counter < obstacleNL.getLength(); counter++) {
                        Node obstacleN = obstacleNL.item(counter);
                        Element obstacleE = (Element) obstacleN;
                    String idName = obstacleE.getElementsByTagName("id").item(0).getTextContent();
                    int posX = Integer.parseInt(obstacleE.getElementsByTagName("posX").item(0).getTextContent());
                    int posY = Integer.parseInt(obstacleE.getElementsByTagName("posY").item(0).getTextContent());
                    int length = Integer.parseInt(obstacleE.getElementsByTagName("length").item(0).getTextContent());
                    int width = Integer.parseInt(obstacleE.getElementsByTagName("width").item(0).getTextContent());
                    int height = Integer.parseInt(obstacleE.getElementsByTagName("height").item(0).getTextContent());
                    int degrees = Integer.parseInt(obstacleE.getElementsByTagName("degree").item(0).getTextContent());
                    int highestXOffset = Integer.parseInt(obstacleE.getElementsByTagName("highestXOffset").item(0).getTextContent());
                    int highestYOffset = Integer.parseInt(obstacleE.getElementsByTagName("highestYOffset").item(0).getTextContent());
                        obstacles.add(new Obstacle(idName, posX, posY, length, width, height, degrees, highestXOffset, highestYOffset));
                    }
                }
		}catch (Exception e){
			e.printStackTrace();
		}
		return obstacles;
	}

}
