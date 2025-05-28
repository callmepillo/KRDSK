package PaooGame.Graphics;

import PaooGame.Database.Options;
import PaooGame.Database.PlayerData;

public class Messages {
    public static String title = "oooo    oooo ooooooooo.   oooooooooo.    .oooooo..o oooo    oooo \n" +
                                 "`888   .8P'  `888   `Y88. `888'   `Y8b  d8P'    `Y8 `888   .8P'  \n" +
                                 " 888  d8'     888   .d88'  888      888 Y88bo.       888  d8'    \n" +
                                 " 88888[       888ooo88P'   888      888  `\"Y8888o.   88888[      \n" +
                                 " 888`88b.     888`88b.     888      888      `\"Y88b  888`88b.    \n" +
                                 " 888  `88b.   888  `88b.   888     d88' oo     .d8P  888  `88b.  \n" +
                                 "o888o  o888o o888o  o888o o888bood8P'   8\"\"88888P'  o888o  o888o \n\n" +
                                 "-----------Kronell-Rapid-Drone-Scouter-and-Keyfinder-------------\n" +
                                 "----------------------------v3.4.4-------------------------------\n" +
                                 "------------------------------TUI--------------------------------\n";
    public static String help = "play <x>   -- play the 'x' level\n" +
                                "title      -- display the title\n" +
                                "help       -- display the avalible commands\n" +
                                "option <op> <value> -- change the option given by op to the given value (WIP)\n" +
                                "option help -- display the avalible options\n" +
                                "option help <op> -- display the avalible values for the option given by op\n" +
                                "save <name> -- save the game (WIP)\n" +
                                "load <name> -- load the saved game with the given name (WIP)\n" +
                                "stats -- see the current players statistics (WIP)\n" +
                                "exit -- leaves the game\n";
    public static String paused =   "ooooooooo.         .o.       ooooo     ooo  .oooooo..o oooooooooooo oooooooooo.   \n" +
                                    "`888   `Y88.      .888.      `888'     `8' d8P'    `Y8 `888'     `8 `888'   `Y8b  \n" +
                                    " 888   .d88'     .8\"888.      888       8  Y88bo.       888          888      888 \n" +
                                    " 888ooo88P'     .8' `888.     888       8   `\"Y8888o.   888oooo8     888      888 \n" +
                                    " 888           .88ooo8888.    888       8       `\"Y88b  888    \"     888      888 \n" +
                                    " 888          .8'     `888.   `88.    .8'  oo     .d8P  888       o  888     d88' \n" +
                                    "o888o        o88o     o8888o    `YbodP'    8\"\"88888P'  o888ooooood8 o888bood8P'   \n" +
                                    "---------------------------------------TUI------------------------------------------\n";
    public static String gameOver = "  .oooooo.        .o.      ooo        oooo oooooooo   .oooooo. oooooo     oooo oooooooo ooooooooo.   \n" +
                                    " d8P'  `Y8b      .88.      `88.       .88' `88'  `8  d8P'  `Y8b `888.     .8'  `88'  `8 `888   `Y88. \n" +
                                    "888             .8\"88.      888b     d'88   88      888      888 `888.   .8'    88       888   .d88' \n" +
                                    "888            .8' `88.     8 Y88. .P  88   88oo8   888      888  `888. .8'     88oo8    888ooo88P'  \n" +
                                    "888     ooooo .88ooo888.    8  `888'   88   88 \"    888      888   `888.8'      88 \"     888`88b.    \n" +
                                    "`88.    .88' .8'     `88.   8    Y     88   88    o `88b    d88'    `888'       88    o  888  `88b.  \n" +
                                    " `Y8bood8P' o88o     o888o o8o        o88o o88oood8  `Y8bood8P'      `8'       o88oood8 o888o  o888o \n" +
                                    "-------------------------------------------------TUI--------------------------------------------------\n";
    public static String lvlNotAvalible = "Sorry, but this level is not avalible.";
    public static String optionNotAvalible = "Sorry, this option is not avalible.";
    public static String optionHelp =   "option <op> <value> -- changes the given option to the given value\n" +
                                        "avalible options are:\n" +
                                        "wasd <true/false> -- set the controls to wasd if true, or to arrow keys if false\n" +
                                        "space <true/false> -- set the jump control to spacebar if true, or to the up equivalent if false\n";
    public static String helpPageNotAvalible = "Sorry, but this help page is not avalible.\n";
    public static String error = "An error has accoured.\n";
    public static String option(String opt, String val) {
        return "Set the option <" + opt + "> to <" + val + ">\n";
    }
    public static String optionStatus() {
        return PlayerData.opt.getStatus();
    }
}
