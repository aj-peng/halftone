package main;

import common.Direction;
import common.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    GamePanel gp;

    boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyInput(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // no implementation
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (gp.gameState) {
            case PLAY -> playState(keyCode);
            case PAUSE -> pauseState(keyCode);
            case DIALOGUE -> dialogueState(keyCode);
            case TITLE -> titleState(keyCode);
        }

        if (keyCode == KeyEvent.VK_BACK_QUOTE) {
            gp.debug = !gp.debug;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        setKeyState(keyCode, false);
    }

    public Direction getKeyDirection() {
        int x = (rightPressed ? 1 : 0) - (leftPressed ? 1 : 0);
        int y = (upPressed ? 1 : 0) - (downPressed ? 1 : 0);

        if (x == 0 && y == 0) {
            return null;
        }

        if (Math.abs(y) < Math.abs(x)) {
            return x > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            return y > 0 ? Direction.UP : Direction.DOWN;
        }
    }

    void setKeyState(int keyCode, boolean pressed) {
        switch (keyCode) {
            case KeyEvent.VK_W -> upPressed = pressed;
            case KeyEvent.VK_A -> leftPressed = pressed;
            case KeyEvent.VK_S -> downPressed = pressed;
            case KeyEvent.VK_D -> rightPressed = pressed;
        }
    }

    void playState(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D -> setKeyState(keyCode, true);
            case KeyEvent.VK_SPACE -> {
                // no implementation
            }
            case KeyEvent.VK_ESCAPE -> {
                gp.ui.commandNum = 0;
                gp.gameState = GameState.PAUSE;
            }
        }
    }

    void pauseState(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W -> gp.ui.setCommandNum(false);
            case KeyEvent.VK_S -> gp.ui.setCommandNum(true);
            case KeyEvent.VK_ESCAPE -> gp.gameState = GameState.PLAY;
            case KeyEvent.VK_SPACE -> {
                switch (gp.ui.commandNum) {
                    case 0 -> gp.gameState = GameState.PLAY;
                    case 1 -> System.out.println("settings");
                    case 2 -> System.exit(0);
                }
            }
        }
    }

    void dialogueState(int keyCode) {
        // no implementation
    }

    void titleState(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W -> gp.ui.setCommandNum(false);
            case KeyEvent.VK_S -> gp.ui.setCommandNum(true);
            case KeyEvent.VK_SPACE -> {
                switch (gp.ui.commandNum) {
                    case 0 -> {
                        gp.gameState = GameState.PLAY;
                        gp.playMusic(0);
                    }
                    case 1 -> System.out.println("settings");
                    case 2 -> System.exit(0);
                }
            }
        }
    }
}
