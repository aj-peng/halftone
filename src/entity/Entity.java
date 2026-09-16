package entity;

import common.Direction;
import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public abstract class Entity {
    GamePanel gp;
    Utility utility = new Utility();

    // public enum DIRECTION {UP, DOWN, LEFT, RIGHT}
    public Direction direction = Direction.DOWN;

    public int worldX, worldY, speed;
    boolean collision = false;
    boolean idle = true;

    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    int hitboxDefaultX, hitboxDefaultY;

    int spriteNum = 0;
    int spriteCounter = 0;
    static final int spriteInterval = 10;
    HashMap<Direction, BufferedImage[]> spriteImage = new HashMap<>();

    public int maxHealth;
    public int health;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public abstract void draw(Graphics2D g2);

    public abstract void update();

    BufferedImage loadImage(String imagePath, int width, int height) {
        BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = utility.scaleImage(image, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    BufferedImage getSpriteImage() {
        BufferedImage[] images = spriteImage.get(direction);
        if (images != null) {
            return images[idle ? 0 : spriteNum];
        }
        return null;
    }
}
