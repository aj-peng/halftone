package item;

import common.Drawable;
import common.Utility;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Item implements Drawable {
    GamePanel gp;
    Utility utility = new Utility();

    String name;
    int worldX, worldY;

    BufferedImage image;
    int imageOffsetX, imageOffsetY;

    public Rectangle hitbox;
    int hitboxDefaultX, hitboxDefaultY;

    public Item(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (gp.player.visible(worldX, worldY)) {
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g2.drawImage(image, screenX + imageOffsetX, screenY + imageOffsetY, null);
            if (gp.debug) {
                g2.setColor(Color.RED);
                g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
            }
        }
    }

    @Override
    public int getDrawOrder() {
        return worldY;
    }

    public void pickUp() {
        gp.playSound(1);
        System.out.println("Picked up: " + name);
    }

    public void setPosition(int col, int row) {
        worldX = gp.tileSize * col;
        worldY = gp.tileSize * row;
    }

    public void convertWorldHitbox() {
        hitbox.x += worldX;
        hitbox.y += worldY;
    }

    public void resetWorldHitbox() {
        hitbox.x = hitboxDefaultX;
        hitbox.y = hitboxDefaultY;
    }

    void setHitbox(int width, int height) {
        width = Math.clamp(width, 1, gp.tileSize);
        height = Math.clamp(height, 1, gp.tileSize);
        hitboxDefaultX = (gp.tileSize - width) / 2;
        hitboxDefaultY = (gp.tileSize - height) / 2;
        hitbox = new Rectangle(hitboxDefaultX, hitboxDefaultY, width, height);
    }

    BufferedImage getSpriteImage(String imageName, int width, int height) {
        BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/items/" + imageName + ".png")));
            image = utility.scaleImage(image, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
