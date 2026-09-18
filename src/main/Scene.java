package main;

import item.Heart;
import item.Item;

public class Scene {
    GamePanel gp;

    public Scene(GamePanel gp) {
        this.gp = gp;
    }

    public void setItems() {
        setItem(0, 2, 2, new Heart(gp));
        setItem(1, 3, 3, new Heart(gp));
    }

    void setItem(int index, int col, int row, Item item) {
        if (gp.items[index] != null) return;
        item.setPosition(col, row);
        gp.items[index] = item;
    }
}
