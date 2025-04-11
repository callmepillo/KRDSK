package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
        /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage playerLeft;
    public static BufferedImage playerRight;
    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage mountain;
    public static BufferedImage townGrass;
    public static BufferedImage townGrassDestroyed;
    public static BufferedImage townSoil;
    public static BufferedImage water;
    public static BufferedImage rockUp;
    public static BufferedImage rockDown;
    public static BufferedImage rockLeft;
    public static BufferedImage rockRight;
    public static BufferedImage tree;
    public static BufferedImage plant;
    public static BufferedImage floor;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/firstCorrectSprite_resized.png"));

            /// Se obtin subimaginile corespunzatoare elementelor necesare.
//        grass = sheet.crop(0, 0);
//        soil = sheet.crop(1, 0);
//        water = sheet.crop(2, 0);
//        mountain = sheet.crop(3, 0);
//        townGrass = sheet.crop(0, 1);
//        townGrassDestroyed = sheet.crop(1, 1);
//        townSoil = sheet.crop(2, 1);
//        tree = sheet.crop(3, 1);
//        playerLeft = sheet.crop(0, 2);
//        playerRight = sheet.crop(1, 2);
//        rockUp = sheet.crop(2, 2);
//        rockDown = sheet.crop(3, 2);
//        rockLeft = sheet.crop(0, 3);
//        rockRight = sheet.crop(1, 3);

        plant = sheet.crop(9,3);
        floor_middle = sheet.crop(1, 4);


        left_upper_door=sheet.crop(0,0);
        left_midle_door=sheet.crop(0,1);
        left_down_door=sheet.crop(0,2);


        right_upper_door=sheet.crop(1,0);
        right_midle_door=sheet.crop(1,1);
        right_down_door=sheet.crop(1,2);

        thin_half_wall_left_up=sheet.crop(2, 0);
        thin_half_wall_left_middle=sheet.crop(2, 1);
        thin_half_wall_left_down=sheet.crop(2, 2);

        thin_half_wall_right_up=sheet.crop(3, 0);
        thin_half_wall_right_middle=sheet.crop(3, 1);
        thin_half_wall_right_down=sheet.crop(3, 2);


        thick_half_wall_left_up=sheet.crop(4, 0);
        thick_half_wall_left_middle=sheet.crop(4, 1);
        thick_half_wall_left_down=sheet.crop(4, 2);


        thin_window_up=sheet.crop(5,0);
        thin_window_middle=sheet.crop(5,1);
        thin_window_down=sheet.crop(5,2);


        rounded_wall_up=sheet.crop(6,0);
        rounded_wall_middle=sheet.crop(6,1);
        rounded_wall_down=sheet.crop(6,2);

        half_left_window=sheet.crop(7,0);
        half_left_window=sheet.crop(7,1);
        half_left_window=sheet.crop(7,2);

        half_right_window=sheet.crop(8,0);
        half_right_window=sheet.crop(8,1);
        half_right_window=sheet.crop(8,2);

        arcade_tree_up=sheet.crop(9,0);
        arcade_tree_down=sheet.crop(9,1);




    }
}
