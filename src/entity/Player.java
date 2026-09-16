package entity;

import common.Direction;
import main.GamePanel;
import main.KeyInput;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyInput keyInput;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyInput keyInput) {
        super(gp);
        this.keyInput = keyInput;

        screenX = (gp.screenWidth - gp.tileSize) / 2;
        screenY = (gp.screenHeight - gp.tileSize) / 2;

        hitbox = new Rectangle(12, 16, 24, 32);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;

        setValues();
        setImages();
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = getSpriteImage();
        g2.drawImage(image, screenX, screenY, null);
        if (gp.debug) {
            g2.setColor(Color.RED);
            g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
        }
    }

    public void update() {
        Direction nextDirection = keyInput.getKeyDirection();
        idle = (nextDirection == null);

        if (nextDirection != null) {
            direction = nextDirection;

            // collision check tile, objects, entities
            collision = gp.collision.checkTile(this);

            if (!collision) {
                switch (direction) {
                    case UP -> worldY -= speed;
                    case DOWN -> worldY += speed;
                    case LEFT -> worldX -= speed;
                    case RIGHT -> worldX += speed;
                }

                spriteCounter++;
                if (spriteCounter >= spriteInterval) {
                    spriteNum = (spriteNum == 3) ? 0 : spriteNum + 1;
                    spriteCounter = 0;
                }
            }
        }
    }

    public boolean visible(int x, int y) {
        return (x + gp.tileSize > worldX - screenX) && (x - gp.tileSize < worldX + screenX) &&
                (y + gp.tileSize > worldY - screenY) && (y - gp.tileSize < worldY + screenY);
    }
    void setValues() {
        worldX = gp.tileSize * 7;
        worldY = gp.tileSize * 2;
        speed = 2;
        direction = Direction.DOWN;
        maxHealth = 5;
        health = maxHealth;
    }

    void setImages() {
        spriteImage.put(Direction.UP, new BufferedImage[] {
                loadImage("/player/mono_up_1", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_up_2", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_up_3", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_up_4", gp.tileSize, gp.tileSize),
        });

        spriteImage.put(Direction.DOWN, new BufferedImage[] {
                loadImage("/player/mono_down_1", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_down_2", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_down_3", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_down_4", gp.tileSize, gp.tileSize),
        });

        spriteImage.put(Direction.LEFT, new BufferedImage[] {
                loadImage("/player/mono_left_1", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_left_2", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_left_3", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_left_4", gp.tileSize, gp.tileSize),
        });

        spriteImage.put(Direction.RIGHT, new BufferedImage[] {
                loadImage("/player/mono_right_1", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_right_2", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_right_3", gp.tileSize, gp.tileSize),
                loadImage("/player/mono_right_4", gp.tileSize, gp.tileSize),
        });
    }
}
