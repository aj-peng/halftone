package entity;

import common.Direction;
import common.GameState;
import main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class NPC extends Entity {
    String[] dialogueEntries;
    private int dialogueIndex = 0;

    public NPC(GamePanel gp) {
        super(gp);
        setImages();
        setDialogue();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (gp.player.visible(worldX, worldY)) {
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            BufferedImage image = getSpriteImage();
            g2.drawImage(image, screenX, screenY, null);
            if (gp.debug) {
                g2.setColor(Color.RED);
                g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
            }
        }
    }

    void speak() {
        if (dialogueEntries == null || dialogueEntries.length == 0) return;
        if (dialogueEntries[dialogueIndex] == null) {
            gp.gameState = GameState.PLAY;
            dialogueIndex = 0;
            return;
        }

        gp.ui.setDialogueText(dialogueEntries[dialogueIndex]);
        dialogueIndex = ++dialogueIndex < dialogueEntries.length ? dialogueIndex : 0;

        direction = switch (gp.player.direction) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
    }

    abstract void act();

    abstract void setImages();

    abstract void setDialogue();
}
