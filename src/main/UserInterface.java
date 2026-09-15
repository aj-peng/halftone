package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UserInterface {
    GamePanel gp;
    Graphics2D g2;

    final Font maruMonica;
    final Font textFont, boldFont, subTitleFont, titleFont;

    final int arc = 25, stroke = 4, padding = 8;
    final BasicStroke strokeRect = new BasicStroke(stroke);
    final Color windowAlpha = new Color(0, 0, 0, 210),
            pauseAlpha = new Color(0, 0, 0, 150);

    final String titleText = "halftone", pauseText = "pause";
    final String[] menuEntries = {"play", "settings", "quit"};
    String dialogueText = "Lorem Ipsum";
    int commandNum = 0;

    public UserInterface(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf"));
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        textFont = maruMonica.deriveFont(24F);
        boldFont = maruMonica.deriveFont(Font.BOLD, 24F);
        subTitleFont = maruMonica.deriveFont(Font.BOLD, 32F);
        titleFont = maruMonica.deriveFont(Font.BOLD, 48F);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        switch (gp.gameState) {
            case PLAY -> {
                drawPlayerHealth();
                drawMessages();
            }
            case PAUSE -> {
                drawPlayerHealth();
                drawPauseMenu();
            }
            case TITLE -> drawTitleMenu();
            case DIALOGUE -> drawDialogueMenu();
        }
    }

    public void setDialogueText(String text) {
        dialogueText = text;
    }

    public void setCommandNum(boolean next) {
        commandNum = switch (gp.gameState) {
            case PAUSE, TITLE ->  next ? (commandNum == 2 ? 0 : ++commandNum) : (commandNum == 0 ? 2 : --commandNum);
            default -> 0;
        };
    }

    void drawPlayerHealth() {
        int x = gp.tileSize / 4;
        g2.setColor(Color.WHITE);
        g2.setFont(boldFont);

        FontMetrics metrics = g2.getFontMetrics();
        g2.drawString(gp.player.health + "/" + gp.player.maxHealth, x, x + metrics.getAscent());
    }

    void drawMessages() {
        // no implementation
    }

    void drawPauseMenu() {
        g2.setColor(pauseAlpha);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(subTitleFont);
        g2.setColor(Color.YELLOW);
        int x = getCenteredTextX(pauseText), y = gp.tileSize * 4;
        g2.drawString(pauseText, x, y);

        g2.setColor(Color.WHITE);
        g2.setFont(boldFont);
        for (int i = 0; i < menuEntries.length; i++) {
            x = getCenteredTextX(menuEntries[i]);
            y += gp.tileSize;
            g2.drawString(menuEntries[i], x, y);
            if (commandNum == i) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

    void drawTitleMenu() {
        g2.setColor(Color.YELLOW);
        g2.setFont(titleFont);
        int x = getCenteredTextX(titleText), y = gp.tileSize * 4;
        g2.drawString(titleText, x, y);

        g2.setColor(Color.WHITE);
        g2.setFont(subTitleFont);
        for (int i = 0; i < menuEntries.length; i++) {
            x = getCenteredTextX(menuEntries[i]);
            y += gp.tileSize * 3 / 2;
            g2.drawString(menuEntries[i], x, y);
            if (commandNum == i) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

    void drawDialogueMenu() {
        int x = gp.tileSize * 2, y = gp.tileSize * 2;
        int width = gp.screenWidth - 2 * x, height = gp.tileSize * 3;
        drawWindow(x, y, width, height);

        x += gp.tileSize / 4 + padding;
        y += gp.tileSize / 2 + padding;

        g2.setFont(textFont);
        for (String line : dialogueText.split("\n")) {
            g2.drawString(line, x, y);
            y += gp.tileSize / 2 + padding;
        }
    }

    void drawWindow(int x, int y, int width, int height) {
        g2.setColor(windowAlpha);
        g2.fillRoundRect(x, y, width, height, arc, arc);

        g2.setColor(Color.WHITE);
        g2.setStroke(strokeRect);
        g2.drawRoundRect(x + stroke, y + stroke, width - 2 * stroke, height - 2 * stroke, arc, arc);
    }

    int getCenteredTextX(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenWidth - length) / 2;
    }
}
