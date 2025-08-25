# KRDSK

KRDSK is a 2D puzzle/stealth platformer game that takes place from the perspective of a drone operator (the player), using a terminal-like interface. Throughout a level, the user will have access to multiple security cameras to track the robot's progress to the next level and avoid dangers in subsequent stages. The goal of a level is to reach the final room and eavesdrop on a conversation between two characters.

## Important mechanics
1. Emulated Terminal Menu

    The menu is similar to an emulated terminal, where we load multiple windows representing game spaces (stages), which open inside the main game window and also represent the user's function to start the game, save it, etc.
2. Multiple game windows (FauxWindow)
   
    The "agent" (user) to be able to open, close, and manipulate multiple game windows during the game, whose position and order are important for solving puzzles and completing the level.

3. Implementation of a CRT effect
   
    The game uses a shader in Swing that mimics the look and feel of a CRT. The player will have between 2 and 10 FauxWindow windows available, which will represent, in the context of the game, the perspectives of surveillance cameras accessible to them. These windows can be opened and closed using the 0-9 keys and will represent the main way to navigate through the game.

## Main menu
<img width="1920" height="1200" alt="Menu" src="https://github.com/user-attachments/assets/342e9f43-6da0-47bf-8f3c-6827a8a6a077" />

## First level
<img width="1920" height="1200" alt="Level 1" src="https://github.com/user-attachments/assets/575dc7e8-8b5e-43d5-a47c-f1aad3d67948" />
