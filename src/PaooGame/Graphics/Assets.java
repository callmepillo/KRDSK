package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
        /// Referinte catre elementele grafice (dale) utilizate in joc.
        public static BufferedImage floor_middle;

    public static BufferedImage left_upper_door;
    public static BufferedImage left_midle_door;
    public static BufferedImage left_down_door;

    public static BufferedImage right_upper_door;
    public static BufferedImage right_midle_door;
    public static BufferedImage right_down_door;

    public static BufferedImage thin_half_wall_left_up;
    public static BufferedImage thin_half_wall_left_middle;
    public static BufferedImage thin_half_wall_left_down;

    public static BufferedImage thin_half_wall_right_up;
    public static BufferedImage thin_half_wall_right_middle;
    public static BufferedImage thin_half_wall_right_down;

    public static BufferedImage thick_half_wall_left_up;
    public static BufferedImage thick_half_wall_left_middle;
    public static BufferedImage thick_half_wall_left_down;

    public static BufferedImage thin_window_up;
    public static BufferedImage thin_window_middle;
    public static BufferedImage thin_window_down;

    public static BufferedImage rounded_wall_up;
    public static BufferedImage rounded_wall_middle;
    public static BufferedImage rounded_wall_down;

    public static BufferedImage half_left_window;
    public static BufferedImage half_right_window;

    public static BufferedImage arcade_tree_up;
    public static BufferedImage arcade_tree_down;

    public static BufferedImage small_window;

    public static BufferedImage large_desk_left;
    public static BufferedImage large_desk_middle;
    public static BufferedImage large_desk_right;

    public static BufferedImage small_desk;

    public static BufferedImage one_piece_window_up1;
    public static BufferedImage one_piece_window_down1;
    public static BufferedImage one_piece_window_up2;
    public static BufferedImage one_piece_window_down2;

    public static BufferedImage noarchway_window_left_up;
    public static BufferedImage noarchway_window_left_down;
    public static BufferedImage noarchway_window_right_up;
    public static BufferedImage noarchway_window_right_down;

    public static BufferedImage round_tree_up;
    public static BufferedImage round_tree_down;

    public static BufferedImage bonzai;

    public static BufferedImage floor_left;
    public static BufferedImage floor_right;

    public static BufferedImage small_fence;

    public static BufferedImage chair1_up;
    public static BufferedImage chair1_down;

    public static BufferedImage chair2_up;
    public static BufferedImage chair2_down;

    public static BufferedImage box;
    public static BufferedImage small_table;

    public static BufferedImage grass_left;
    public static BufferedImage grass_right;

    public static BufferedImage big_bush_left;
    public static BufferedImage big_bush_right;

    public static BufferedImage dumbell_left;
    public static BufferedImage dumbell_right;

    public static BufferedImage big_table_left;
    public static BufferedImage big_table_right;

    public static BufferedImage up_line;
    public static BufferedImage small_bush;

    public static BufferedImage fence_left;
    public static BufferedImage fence_right;

    public static BufferedImage double_obstacle_left;
    public static BufferedImage double_obstacle_right;

    public static BufferedImage huge_bush_left_up;
    public static BufferedImage huge_bush_left_down;
    public static BufferedImage huge_bush_right_up;
    public static BufferedImage huge_bush_right_down;

    public static BufferedImage cut_bush_left_up;
    public static BufferedImage cut_bush_left_down;
    public static BufferedImage cut_bush_right_up;
    public static BufferedImage cut_bush_right_down;

    public static BufferedImage arcade_left;
    public static BufferedImage arcade_right;

    public static BufferedImage double_round_obstacle_left;
    public static BufferedImage double_round_obstacle_right;


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

        small_window=sheet.crop(9,2);

        large_desk_left=sheet.crop(0,3);
        large_desk_middle=sheet.crop(1,3);
        large_desk_right=sheet.crop(2,3);

        small_desk=sheet.crop(3,3);

        one_piece_window_up1=sheet.crop(4,3);
        one_piece_window_down1=sheet.crop(4,4);

        one_piece_window_up2=sheet.crop(5,3);
        one_piece_window_down2=sheet.crop(5,4);

        noarchway_window_left_up=sheet.crop(6,3);
        noarchway_window_left_down=sheet.crop(6,4);
        noarchway_window_right_up=sheet.crop(7,3);
        noarchway_window_right_down=sheet.crop(7,4);

        round_tree_up=sheet.crop(8,3);
        round_tree_down=sheet.crop(8,4);

        bonzai=sheet.crop(9, 3);

        floor_left=sheet.crop(0,4);
        floor_middle=sheet.crop(1,4);
        floor_right=sheet.crop(2,4);

        small_fence=sheet.crop(3,4);

        chair1_up=sheet.crop(0,5);
        chair1_down=sheet.crop(0,6);

        chair2_up=sheet.crop(1,5);
        chair2_down=sheet.crop(1,6);

        box=sheet.crop(2,5);

        small_table=sheet.crop(3,5);

        grass_left=sheet.crop(4,5);
        grass_right=sheet.crop(5,5);

        big_bush_left=sheet.crop(6,5);
        big_bush_right=sheet.crop(7,5);

        dumbell_left=sheet.crop(8,5);
        dumbell_right=sheet.crop(9,5);

        big_table_left=sheet.crop(2,6);
        big_table_right=sheet.crop(3,6);

        up_line=sheet.crop(4, 6);

        small_bush=sheet.crop(5,6);

        fence_left=sheet.crop(6,6);
        fence_right=sheet.crop(7,6);

        double_obstacle_left=sheet.crop(8,6);
        double_obstacle_right=sheet.crop(9,6);

        huge_bush_left_up=sheet.crop(0,7);
        huge_bush_left_down=sheet.crop(0,8);
        huge_bush_right_up=sheet.crop(1,7);
        huge_bush_right_down=sheet.crop(1,8);

        cut_bush_left_up=sheet.crop(2,7);
        cut_bush_left_down=sheet.crop(2,8);
        cut_bush_right_up=sheet.crop(3,7);
        cut_bush_right_down=sheet.crop(3,8);

        arcade_left=sheet.crop(4,7);
        arcade_right=sheet.crop(5,7);

        double_round_obstacle_left=sheet.crop(8,7);
        double_round_obstacle_right=sheet.crop(9,7);







    }
}
