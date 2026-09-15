package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UserInterface {
    GamePanel gp;
    Graphics2D g2;

    final Font maruMonica;
    final Font textFont, boldFont, subTitleFont, titleFont;

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
}
