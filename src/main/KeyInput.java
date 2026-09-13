package main;

import entity.Entity;

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
        int code = e.getKeyCode();
        setKeyState(code, true);

        if (code == KeyEvent.VK_BACK_QUOTE) {
            gp.debug = !gp.debug;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        setKeyState(code, false);
    }

    public Entity.DIRECTION getKeyDirection() {
        int x = (rightPressed ? 1 : 0) - (leftPressed ? 1 : 0);
        int y = (upPressed ? 1 : 0) - (downPressed ? 1 : 0);

        if (x == 0 && y == 0) {
            return null;
        }

        if (Math.abs(y) < Math.abs(x)) {
            return x > 0 ? Entity.DIRECTION.RIGHT : Entity.DIRECTION.LEFT;
        } else {
            return y > 0 ? Entity.DIRECTION.UP : Entity.DIRECTION.DOWN;
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
}
