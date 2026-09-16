package tile;

import main.GamePanel;
import common.Utility;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    Utility utility = new Utility();

    Tile[] tiles;
    int[][] mappedTiles;
    static final int bound = 4;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mappedTiles = new int[gp.maxWorldCol][gp.maxWorldRow];

        setTiles();
        loadMap("world01");
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mappedTiles[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            if (gp.player.visible(worldX, worldY)) {
                g2.drawImage(tiles[tileNum].image, screenX, screenY, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    public int getTileNum(int worldX, int worldY) {
        if (worldX < bound || worldX > gp.maxWorldX - bound || worldY < bound || worldY > gp.maxWorldY - bound) {
            return 0;
        }
        return mappedTiles[worldX / gp.tileSize][worldY / gp.tileSize];
    }

    public boolean getTileCollision(int tileNum) {
        return tiles[tileNum].collision;
    }

    void setTiles() {
        loadTile(0, "void", true);
        loadTile(1, "path", false);
        loadTile(2, "stone", false);
    }

    void loadTile(int index, String imageName, boolean collision) {
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read((Objects.requireNonNull(
                    getClass().getResourceAsStream("/tiles/" + imageName + ".png"))));
            tiles[index].image = utility.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
            tiles[index].collision = collision;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void loadMap(String mapName) {
        try {
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream("/maps/" + mapName + ".txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String[] nums = line.split(" ");
                    int num = Integer.parseInt(nums[col]);
                    mappedTiles[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
