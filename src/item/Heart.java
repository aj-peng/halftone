package item;

import main.GamePanel;

public class Heart extends Item {
    public Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        int width = gp.tileSize / 3, height = gp.tileSize / 3;
        image = getSpriteImage("heart", width, height);
        imageOffsetX = (gp.tileSize - image.getWidth()) / 2;
        imageOffsetY = (gp.tileSize - image.getHeight()) / 2;
        setHitbox(width + 4, height + 4);
    }

    @Override
    public int getDrawOrder() {
        return worldY - imageOffsetY;
    }

    public void pickUp() {
        super.pickUp();
    }
}
