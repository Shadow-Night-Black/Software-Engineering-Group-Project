package  model;
public class Obstacle {
    private String id;
    private int posX, posY, length, width, height, degrees, highestXOffset, highestYOffset;

    public Obstacle(String id, int posX, int posY, int length, int width, int height, int degrees, int highestXOffset, int highestYOffset){
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.length = length;
        this.width = width;
        this.height = height;
        this.degrees = degrees;
        this.highestXOffset = highestXOffset;
        this.highestYOffset = highestYOffset;
    }
    /*  
     * posX, posY - Position defines point at the North-West (Top Left) of the obstruction
     * highestX, highestY - distance from posX and posY to the highest point of the obstacle
     * length - Length from Left to right of the obstacle
     * ?? - orientation of obstacle
     * distanceFromThresholdLeft - distance from the leftmost threshold, now realising it is just posX - 60 (+ length?)
    */
    //Perhaps we could use java.awt.Point instead of posX and posY (Point position(posX,posY)
    // BufferedImage sprite;
    //Not implemented just yet, for discussion and reference

    public static Obstacle getDebugObstacle() {
        return new Obstacle("DEBUG", 1800, 135, 150, 30, 25, 0, 150, 15);
    }

    public String getId() {
        return id;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
 
    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getHeighestX() {
        return getPosX() + highestXOffset;
    }

    public int getHeighestY() {
        return getPosY() + highestYOffset;
    }
 
    public int getDegrees() {
        return degrees;
    }
 
    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }
 
    public int getLength() {
        return length;
    }
 
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
