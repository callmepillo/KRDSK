package PaooGame.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;
import PaooGame.Graphics.Assets;
/*! \class public class Tile
    \brief Retine toate dalele intr-un vector si ofera posibilitatea regasirii dupa un id.
 */
public class Tile
{
    private static final int NO_TILES   = 100;
    public static Tile[] tiles          = new Tile[NO_TILES];       /*!< Vector de referinte de tipuri de dale.*/

    /// De remarcat ca urmatoarele dale sunt statice si publice. Acest lucru imi permite sa le am incarcate
    /// o singura data in memorie
    public static Tile left_upper_door = new Tile(Assets.left_upper_door, 16);
    public static Tile left_midle_door = new Tile(Assets.left_midle_door, 17);
    public static Tile left_down_door = new Tile(Assets.left_down_door, 18);
    public static Tile right_upper_door = new Tile(Assets.right_upper_door, 19);
    public static Tile right_midle_door = new Tile(Assets.right_midle_door, 20);
    public static Tile right_down_door = new Tile(Assets.right_down_door, 21);
    public static Tile thin_half_wall_left_up = new Tile(Assets.thin_half_wall_left_up, 22);
    public static Tile thin_half_wall_left_middle = new Tile(Assets.thin_half_wall_left_middle, 23);
    public static Tile thin_half_wall_left_down = new Tile(Assets.thin_half_wall_left_down, 24);
    public static Tile thin_half_wall_right_up = new Tile(Assets.thin_half_wall_right_up, 25);
    public static Tile thin_half_wall_right_middle = new Tile(Assets.thin_half_wall_right_middle, 26);
    public static Tile thin_half_wall_right_down = new Tile(Assets.thin_half_wall_right_down, 27);
    public static Tile thick_half_wall_left_up = new Tile(Assets.thick_half_wall_left_up, 28);
    public static Tile thick_half_wall_left_middle = new Tile(Assets.thick_half_wall_left_middle, 29);
    public static Tile thick_half_wall_left_down = new Tile(Assets.thick_half_wall_left_down, 30);
    public static Tile thin_window_up = new Tile(Assets.thin_window_up, 31);
    public static Tile thin_window_middle = new Tile(Assets.thin_window_middle, 32);
    public static Tile thin_window_down = new Tile(Assets.thin_window_down, 33);
    public static Tile rounded_wall_up = new Tile(Assets.rounded_wall_up, 34);
    public static Tile rounded_wall_middle = new Tile(Assets.rounded_wall_middle, 35);
    public static Tile rounded_wall_down = new Tile(Assets.rounded_wall_down, 36);
    public static Tile half_left_window = new Tile(Assets.half_left_window, 37);
    public static Tile half_right_window = new Tile(Assets.half_right_window, 38);
    public static Tile arcade_tree_up = new Tile(Assets.arcade_tree_up, 39);
    public static Tile arcade_tree_down = new Tile(Assets.arcade_tree_down, 40);
    public static Tile small_window = new Tile(Assets.small_window, 41);
    public static Tile large_desk_left = new Tile(Assets.large_desk_left, 42);
    public static Tile large_desk_middle = new Tile(Assets.large_desk_middle, 43);
    public static Tile large_desk_right = new Tile(Assets.large_desk_right, 44);
    public static Tile small_desk = new Tile(Assets.small_desk, 45);
    public static Tile one_piece_window_up1 = new Tile(Assets.one_piece_window_up1, 46);
    public static Tile one_piece_window_down1 = new Tile(Assets.one_piece_window_down1, 47);
    public static Tile one_piece_window_up2 = new Tile(Assets.one_piece_window_up2, 48);
    public static Tile one_piece_window_down2 = new Tile(Assets.one_piece_window_down2, 49);
    public static Tile noarchway_window_left_up = new Tile(Assets.noarchway_window_left_up, 50);
    public static Tile noarchway_window_left_down = new Tile(Assets.noarchway_window_left_down, 51);
    public static Tile noarchway_window_right_up = new Tile(Assets.noarchway_window_right_up, 52);
    public static Tile noarchway_window_right_down = new Tile(Assets.noarchway_window_right_down, 53);
    public static Tile round_tree_up = new Tile(Assets.round_tree_up, 54);
    public static Tile round_tree_down = new Tile(Assets.round_tree_down, 55);
    public static Tile bonzai = new Tile(Assets.bonzai, 56);
    public static Tile floor_left = new Tile(Assets.floor_left, 57);
    public static Tile floor_middle = new Tile(Assets.floor_middle, 58);
    public static Tile floor_right = new Tile(Assets.floor_right, 59);
    public static Tile small_fence = new Tile(Assets.small_fence, 60);
    public static Tile chair1_up = new Tile(Assets.chair1_up, 61);
    public static Tile chair1_down = new Tile(Assets.chair1_down, 62);
    public static Tile chair2_up = new Tile(Assets.chair2_up, 63);
    public static Tile chair2_down = new Tile(Assets.chair2_down, 64);
    public static Tile box = new Tile(Assets.box, 65);
    public static Tile small_table = new Tile(Assets.small_table, 66);
    public static Tile grass_left = new Tile(Assets.grass_left, 67);
    public static Tile grass_right = new Tile(Assets.grass_right, 68);
    public static Tile big_bush_left = new Tile(Assets.big_bush_left, 69);
    public static Tile big_bush_right = new Tile(Assets.big_bush_right, 70);
    public static Tile dumbell_left = new Tile(Assets.dumbell_left, 71);
    public static Tile dumbell_right = new Tile(Assets.dumbell_right, 72);
    public static Tile big_table_left = new Tile(Assets.big_table_left, 73);
    public static Tile big_table_right = new Tile(Assets.big_table_right, 74);
    public static Tile up_line = new Tile(Assets.up_line, 75);
    public static Tile small_bush = new Tile(Assets.small_bush, 76);
    public static Tile fence_left = new Tile(Assets.fence_left, 77);
    public static Tile fence_right = new Tile(Assets.fence_right, 78);
    public static Tile double_obstacle_left = new Tile(Assets.double_obstacle_left, 79);
    public static Tile double_obstacle_right = new Tile(Assets.double_obstacle_right, 80);
    public static Tile huge_bush_left_up = new Tile(Assets.huge_bush_left_up, 81);
    public static Tile huge_bush_left_down = new Tile(Assets.huge_bush_left_down, 82);
    public static Tile huge_bush_right_up = new Tile(Assets.huge_bush_right_up, 83);
    public static Tile huge_bush_right_down = new Tile(Assets.huge_bush_right_down, 84);
    public static Tile cut_bush_left_up = new Tile(Assets.cut_bush_left_up, 85);
    public static Tile cut_bush_left_down = new Tile(Assets.cut_bush_left_down, 86);
    public static Tile cut_bush_right_up = new Tile(Assets.cut_bush_right_up, 87);
    public static Tile cut_bush_right_down = new Tile(Assets.cut_bush_right_down, 88);
    public static Tile arcade_left = new Tile(Assets.arcade_left, 89);
    public static Tile arcade_right = new Tile(Assets.arcade_right, 90);
    public static Tile double_round_obstacle_left = new Tile(Assets.double_round_obstacle_left, 91);
    public static Tile double_round_obstacle_right = new Tile(Assets.double_round_obstacle_right, 92);


    public static final int TILE_WIDTH  = 70; //48                      /*!< Latimea unei dale.*/
    public static final int TILE_HEIGHT = 70; //48;                       /*!< Inaltimea unei dale.*/

    protected BufferedImage img;                                    /*!< Imaginea aferenta tipului de dala.*/
    protected final int id;                                         /*!< Id-ul unic aferent tipului de dala.*/

    /*! \fn public Tile(BufferedImage texture, int id)
        \brief Constructorul aferent clasei.

        \param image Imaginea corespunzatoare dalei.
        \param id Id-ul dalei.
     */
    public Tile(BufferedImage image, int idd)
    {
        img = image;
        id = idd;

        tiles[id] = this;
    }

    /*! \fn public void Update()
        \brief Actualizeaza proprietatile dalei.
     */
    public void Update()
    {

    }

    /*! \fn public void Draw(Graphics g, int x, int y)
        \brief Deseneaza in fereastra dala.

        \param g Contextul grafic in care sa se realizeze desenarea
        \param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        \param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */
    public void Draw(Graphics g, int x, int y)
    {
        /// Desenare dala
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public void Draw(Graphics g, int x, int y, int width, int height)
    {
        /// Desenare dala
        g.drawImage(img, x, y, width, height, null);
    }


    /*! \fn public boolean IsSolid()
        \brief Returneaza proprietatea de dala solida (supusa coliziunilor) sau nu.
     */
    public boolean IsSolid()
    {
        return false;
    }

    /*! \fn public int GetId()
        \brief Returneaza id-ul dalei.
     */
    public int GetId()
    {
        return id;
    }
}
