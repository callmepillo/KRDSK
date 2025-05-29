package PaooGame.GameWindow;
import PaooGame.Database.DatabaseManager;
import PaooGame.Database.PlayerData;
import PaooGame.Game;
import PaooGame.Graphics.Messages;
import PaooGame.Tiles.Tile;

import java.util.HashMap;
import java.util.Map;

public class CliDictionary {
    private Map<String, Action<Object>> map;
    public CliDictionary(GameWindow win) {
        map = new HashMap<>();

        Action<Object> exitLevel = new Action<Object>() {
            @Override public void execute(Object T) {
                if (win.IsInLevel())
                    win.ExitLevel();
                else
                    win.SetStop(true);
            }
        };

        Action<Object> clear = new Action<Object>() {
            @Override
            public void execute(Object item) {
                win.GetCliWindow().clearHistory();
            }
        };

        Action<Object> play = new Action<Object>() {
            @Override
            public void execute(Object item) {
                String str = (String) item;
                if (str != null)
                    try {
                        win.StartLevel(Integer.parseInt(str));
                        return;
                    }
                    catch(NumberFormatException ex) {
                        System.out.println("whoops");
                    }
                win.GetCliWindow().addText(Messages.lvlNotAvalible);
            }
        };

        Action<Object> db = new Action<Object>() {
            @Override
            public void execute(Object item) {
                String str = (String) item;
                if (str == null) {
                    win.GetCliWindow().addText(Messages.error);
                    return;
                }

                String[] strs = str.split(" ");
                if (strs.length != 2) {
                    win.GetCliWindow().addText(Messages.error);
                    return;
                }

                if (!strs[0].equals("enter")) {
                    win.GetCliWindow().addText(Messages.error);
                    return;
                }

                try {
                    win.EnterRoom(Integer.parseInt(strs[1]), 0, 2* Tile.TILE_HEIGHT);
                    win.HidePauseMenu();
                }
                catch(NumberFormatException ex) {
                    win.GetCliWindow().addText(Messages.error);
                }
            }
        };

        Action<Object> option = new Action<Object>() {
            @Override
            public void execute(Object item) {
                String str = (String) item;
                if (str == null) {
                    win.GetCliWindow().addText(Messages.error);
                    return;
                }

                String[] strs = str.split(" ");
                if (strs.length < 1 || strs.length > 2) {
                    win.GetCliWindow().addText(Messages.error);
                    return;
                }

                if(strs.length == 2) {
                    switch (strs[0]) {
                        case "wasd":
                            try {
                                PlayerData.opt.setWASD(Boolean.parseBoolean(strs[1]));
                                win.GetCliWindow().addText(Messages.option("wasd", strs[1]));
                                win.HidePauseMenu();
                            } catch (NumberFormatException ex) {
                                win.GetCliWindow().addText(Messages.error);
                            }
                            break;
                        case "space":
                            try {
                                PlayerData.opt.setSpace(Boolean.parseBoolean(strs[1]));
                                win.GetCliWindow().addText(Messages.option("space", strs[1]));
                                win.HidePauseMenu();
                            } catch (NumberFormatException ex) {
                                System.out.println("whoops");
                            }
                            break;
                        default:
                            win.GetCliWindow().addText(Messages.optionNotAvalible);
                    }
                }
                else if (strs.length == 1 && strs[0].equals("status"))
                    win.GetCliWindow().addText(Messages.optionStatus());
            }
        };

        Action<Object> help = new Action<Object>() {
            @Override
            public void execute(Object item) {
                String str = (String) item;
                if (str == null) {
                    win.GetCliWindow().addText(Messages.help);
                    return;
                }

                switch (str) {
                    case "option":
                        win.GetCliWindow().addText(Messages.optionHelp);
                        break;
                    default:
                        win.GetCliWindow().addText(Messages.helpPageNotAvalible);
                        break;
                }
            }
        };

        Action<Object> title = new Action<Object>() {
            @Override
            public void execute(Object item) {
                win.GetCliWindow().addText(Messages.title);
            }
        };

        Action<Object> paused = new Action<Object>() {
            @Override
            public void execute(Object item) {
                win.GetCliWindow().addText(Messages.title);
            }
        };

        Action<Object> numpie = new Action<Object>() {
            @Override
            public void execute(Object item) {
                win.GetCliWindow().addText(Messages.title);
            }
        };

        Action<Object> notFound = new Action<Object>() {
            @Override
            public void execute(Object item) {
                win.GetCliWindow().addText("Command \"" + ((String) item) + "\" not found");
            }
        };

        Action<Object> notLoggedIn = new Action<Object>() {
            @Override
            public void execute(Object item) {
                win.GetCliWindow().addText("User not logged in.");
            }
        };

        Action<Object> login = new Action<Object>() {
            @Override
            public void execute(Object item) {
                String str = (String) item;
                if (str != null)
                    try {
                        win.Login(str);
                        return;
                    }
                    catch(NumberFormatException ex) {
                        System.out.println("whoops");
                    }
                win.GetCliWindow().addText("User not logged in.");
            }
        };

        Action<Object> save = new Action<Object>() {
            @Override
            public void execute(Object item) {
                String str = (String) item;

                DatabaseManager.create();
                if(str != null)
                    win.GetCliWindow().addText(Messages.error);
                else if (DatabaseManager.playerExists(PlayerData.name)) {
                    DatabaseManager.save();
                    win.GetCliWindow().addText("Saved progression and options!");
                }
                else {
                    DatabaseManager.addNewPlayer();
                    win.GetCliWindow().addText("Created a profile and saved progression and options!");
                }
            }
        };

        map.put("exit", exitLevel);
        map.put("clear", clear);
        map.put("play", play);
        map.put("db", db);
        map.put("option", option);
        map.put("help", help);
        map.put("title", title);
        map.put("paused", paused);
        map.put("numpie", numpie);
        map.put("notFound", notFound);
        map.put("notLoggedIn", notLoggedIn);
        map.put("login", login);
        map.put("save", save);
    }

    public void execute(String s, String args) {
        if(!GameWindow.loggedIn) {
            if (s.equals("login"))
                map.get(s).execute(args);
            else
                map.get("notLoggedIn").execute(null);
        }
        else {
            if (map.containsKey(s))
                map.get(s).execute(args);
            else
                map.get("notFound").execute(s);
        }
    }
}
