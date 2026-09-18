package main;

import common.Drawable;
import common.GameState;
import entity.Player;
import item.Item;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int scalar = 3;
    final int originalTileSize = 16; // 16x16
    public final int tileSize = scalar * originalTileSize; // 48x48

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 768 px
    public final int screenHeight = maxScreenRow * tileSize; // 576 px

    public final int maxWorldCol = 15;
    public final int maxWorldRow = 15;
    public final int maxWorldX = maxWorldCol * tileSize;
    public final int maxWorldY = maxWorldRow * tileSize;

    Sound sound = new Sound(), music = new Sound();
    KeyInput keyInput = new KeyInput(this);
    TileManager tileManager = new TileManager(this);
    public UserInterface ui = new UserInterface(this);
    public Collision collision = new Collision(this);

    final Comparator<Drawable> drawOrder = Comparator.comparingInt(Drawable::getDrawOrder);
    final ArrayList<Drawable> drawList = new ArrayList<>();
    public Player player = new Player(this, keyInput);
    public Item[] items = new Item[8];

    public boolean debug = false;
    public GameState gameState = GameState.TITLE;

    final int tickRate = 60;
    final double drawDelta = 1_000_000_000D / tickRate;
    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyInput);
        this.setFocusable(true);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long currentTime;
        double delta = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawDelta;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == GameState.TITLE) {
            ui.draw(g2);
        } else {
            tileManager.draw(g2);

            drawList.clear();
            drawList.add(player);
            for (Item item : items) {
                if (item != null) drawList.add(item);
            }
            drawList.sort(drawOrder);
            for (Drawable sprite : drawList) {
                sprite.draw(g2);
            }

            ui.draw(g2);
        }
    }

    public void playSound(int index) {
        sound.setFile(index);
        sound.play();
    }

    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    void update() {
        if (gameState == GameState.PLAY) {
            player.update();
        }
    }

    void setupGame() {
        // Setup Props and NPCs
    }

    void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
}
