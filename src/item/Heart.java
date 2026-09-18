package item;

import main.GamePanel;

public class Heart extends Item {
    public Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = getSpriteImage("heart", gp.tileSize / 2, gp.tileSize / 2);
        imageOffsetX = (gp.tileSize - image.getWidth()) / 2;
        imageOffsetY = (gp.tileSize - image.getHeight()) / 2;
        setHitbox(gp.tileSize / 2, gp.tileSize / 2);
    }
}
