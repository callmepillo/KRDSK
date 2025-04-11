package PaooGame.Graphics;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyFonts {
    public static Font JetBrains;
    public static Font Anon;
    public static Font Roboto;
    public static Font Space;
    public static Font VT323;

    public static void Init(float size) {
        try {
            JetBrains = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/JetBrains_Mono/JetBrainsMono-VariableFont_wght.ttf")).deriveFont(size);
            Anon = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Anonymous_Pro/AnonymousPro-Regular.ttf")).deriveFont(size);
            Roboto = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Roboto_Mono/RobotoMono-VariableFont_wght.ttf")).deriveFont(size);
            Space = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Space_Mono/SpaceMono-Regular.ttf")).deriveFont(size);
            VT323 = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/VT323/VT323-Regular.ttf")).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
