package main;

import entity.Entity;

public class Collision {
    GamePanel gp;

    public Collision(GamePanel gp) {
        this.gp = gp;
    }

    public boolean checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.hitbox.x;
        int entityRightWorldX = entityLeftWorldX + entity.hitbox.width;
        int entityTopWorldY = entity.worldY + entity.hitbox.y;
        int entityBottomWorldY = entityTopWorldY + entity.hitbox.height;
        int tileNum1 = 0, tileNum2 = 0;

        switch (entity.direction) {
            case UP -> {
                entityTopWorldY -= entity.speed;
                tileNum1 = gp.tileManager.getTileNum(entityLeftWorldX, entityTopWorldY);
                tileNum2 = gp.tileManager.getTileNum(entityRightWorldX, entityTopWorldY);
            }
            case DOWN -> {
                entityBottomWorldY += entity.speed;
                tileNum1 = gp.tileManager.getTileNum(entityLeftWorldX, entityBottomWorldY);
                tileNum2 = gp.tileManager.getTileNum(entityRightWorldX, entityBottomWorldY);
            }
            case LEFT -> {
                entityLeftWorldX -= entity.speed;
                tileNum1 = gp.tileManager.getTileNum(entityLeftWorldX, entityTopWorldY);
                tileNum2 = gp.tileManager.getTileNum(entityLeftWorldX, entityBottomWorldY);
            }
            case RIGHT -> {
                entityRightWorldX += entity.speed;
                tileNum1 = gp.tileManager.getTileNum(entityRightWorldX, entityTopWorldY);
                tileNum2 = gp.tileManager.getTileNum(entityRightWorldX, entityBottomWorldY);
            }
        }

        return (gp.tileManager.getTileCollision(tileNum1) || gp.tileManager.getTileCollision(tileNum2));
    }
}
