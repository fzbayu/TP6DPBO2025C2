import java.awt.*;

public class Pipe {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityX;
    boolean passed = false;
    public boolean isPassed = false;


    public Pipe(int posX, int posY, int width, int height, Image image){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;

        this.velocityX = -1;
        this.passed = false;
    }

    // getter

    public int getWidth() {
        return width;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public int getVelocityX() {
        return velocityX;
    }


    // setter

    public void setWidth(int width) {
        this.width = width;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }
}

