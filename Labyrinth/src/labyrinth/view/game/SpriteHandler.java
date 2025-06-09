package labyrinth.view.game;

import javafx.scene.image.Image;
import labyrinth.model.LabyrinthModel;
import labyrinth.model.interfaces.SettingsHandler;
import labyrinth.model.entities.EntityModel;
import labyrinth.model.entities.Ghost;
import labyrinth.model.entities.Player;
import labyrinth.model.entities.Wall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpriteHandler {

    private enum direction { up, down, left, right }

    // PLAYER
    // faces right by default
    private direction playerDirection;
    private int playerSpriteCounter;
    private Image[] playerUp;
    private Image[] playerDown;
    private Image[] playerLeft;
    private Image[] playerRight;

    private int spriteCounterPlayer;
    private int spriteNumberPlayer;



    // GHOST
    private direction ghostDirection;
    private int ghostSpriteCounter;
    private Image[] ghostUp;
    private Image[] ghostDown;
    private Image[] ghostLeft;
    private Image[] ghostRight;

    private int spriteCounterGhost;
    private int spriteNumberGhost;



    // WALLS
    private int wallSpriteCounter;    // 5 animation frames
    private Image[] walls;

    /*
     *   WALL IMAGES:
     *   wall 0 - 3      = singular connected edge pieces ( down, left , up, right )
     *   wall 4 - 5      = double connected side pieces ( left + right, up + down )
     *   wall 6 - 9      = double connected corner pieces
     *   wall 10 - 13    = triple connected corner pieces
     *   wall 14         = quadruple connected piece
     *   wall 15         = singular piece i.e. not connected to any
     */

    private Image timePotion;
    private Image portal;
    private Image exit;

    private final int tileSize;

    SpriteHandler(){
        playerDirection = direction.right;
        ghostDirection = direction.right;

        spriteCounterPlayer = 0;
        spriteNumberPlayer = 0;

        tileSize = SettingsHandler.getTileSize();
    }

    public void setSprites () {
        setWallSprites();

        setPlayerSprites();
        setGhostSprites();

        timePotion = new Image("file:resources/sprites/environment/time.png");
        portal = new Image("file:resources/sprites/environment/portal.png");
        exit = new Image("file:resources/sprites/environment/exit.png");
    }

    void setPlayerSprites () {
        Path directory = Paths.get("resources/sprites/player/up");
        try {
            playerSpriteCounter = (int) Files.list(directory).count();}
        catch (IOException e) {System.out.println("Error reading player sprite directory: " + e.getMessage());}

        playerUp = new Image[playerSpriteCounter];
        playerDown = new Image[playerSpriteCounter];
        playerLeft = new Image[playerSpriteCounter];
        playerRight = new Image[playerSpriteCounter];

        for (int i = 0; i <= playerSpriteCounter - 1; i++) {
            playerUp[i] = new Image("file:resources/sprites/player/up/tile" + i + ".png");
            playerDown[i] = new Image("file:resources/sprites/player/down/tile" + i + ".png");
            playerLeft[i] = new Image("file:resources/sprites/player/left/tile" + i + ".png");
            playerRight[i] = new Image("file:resources/sprites/player/right/tile" + i + ".png");
        }
    }

    void setGhostSprites () {
        Path directory = Paths.get("resources/sprites/ghost/up");
        try { ghostSpriteCounter = (int) Files.list(directory).count(); }
        catch (IOException e) {System.out.println("Error reading ghost sprite directory: " + e.getMessage());}

        ghostUp = new Image[ghostSpriteCounter];
        ghostDown = new Image[ghostSpriteCounter];
        ghostLeft = new Image[ghostSpriteCounter];
        ghostRight = new Image[ghostSpriteCounter];

        for (int i = 0; i <= ghostSpriteCounter - 1; i++) {
            ghostUp[i] = new Image("file:resources/sprites/ghost/up/tile" + i + ".png");
            ghostDown[i] = new Image("file:resources/sprites/ghost/down/tile" + i + ".png");
            ghostLeft[i] = new Image("file:resources/sprites/ghost/left/tile" + i + ".png");
            ghostRight[i] = new Image("file:resources/sprites/ghost/right/tile" + i + ".png");
        }
    }

    void setWallSprites () {
        Path directory = Paths.get("resources/sprites/environment/walls");
        try { wallSpriteCounter = (int) Files.list(directory).count(); }
        catch (IOException e) {System.out.println("Error reading walls directory: " + e.getMessage());}

        walls = new Image[wallSpriteCounter];
        for (int i = 0; i <= wallSpriteCounter - 1; i++) {
            walls[i] = new Image("file:resources/sprites/environment/walls/wall" + i + ".png");
        }
    }

    public Image getPlayerSprite (Player player) {
        Image sprite = null;
        if(player == null){return sprite;} // player entity does not exit

        if(player.getGoNorth() && !player.getGoSouth()) { playerDirection = direction.up; }
        if(player.getGoSouth() && !player.getGoNorth()) { playerDirection = direction.down; }
        if(player.getGoWest()  && !player.getGoEast() ) { playerDirection = direction.left; }
        if(player.getGoEast()  && !player.getGoWest() ) { playerDirection = direction.right; }

        int walkUpdateCount = (player.sprinting()) ? 10 : 20;

        // check if sprite needs to be updated
        if (spriteCounterPlayer >= walkUpdateCount){
            spriteCounterPlayer = 0;
            spriteNumberPlayer++;
            if(spriteNumberPlayer >= playerSpriteCounter) {
                spriteNumberPlayer = 0;}
        }

        // set directional sprite
        switch (playerDirection){
            case up:
                sprite = playerUp[spriteNumberPlayer];
                break;

            case down:
                sprite = playerDown[spriteNumberPlayer];
                break;

            case left:
                sprite = playerLeft[spriteNumberPlayer];
                break;

            case right:
                sprite = playerRight[spriteNumberPlayer];
                break;

            default:
                break;
        }

        spriteCounterPlayer++;
        return sprite;
    }

    public Image getGhostSprite (Ghost ghost) {
        Image sprite = null;
        if(ghost == null){return sprite;} // ghost entity does not exit

        if(ghost.getGoNorth() && !ghost.getGoSouth()) { ghostDirection = direction.up; }
        if(ghost.getGoSouth() && !ghost.getGoNorth()) { ghostDirection = direction.down; }
        if(ghost.getGoWest()  && !ghost.getGoEast() ) { ghostDirection = direction.left; }
        if(ghost.getGoEast()  && !ghost.getGoWest() ) { ghostDirection = direction.right; }

        int walkUpdateCount = (ghost.sprinting()) ? 10 : 20;

        // check if sprite needs to be updated
        if (spriteCounterGhost >= walkUpdateCount){
            spriteCounterGhost = 0;
            spriteNumberGhost++;
            if(spriteNumberGhost >= ghostSpriteCounter) {
                spriteNumberGhost = 0;}
        }

        // set directional sprite
        switch (ghostDirection){
            case up:
                sprite = ghostUp[spriteNumberGhost];
                break;

            case down:
                sprite = ghostDown[spriteNumberGhost];
                break;

            case left:
                sprite = ghostLeft[spriteNumberGhost];
                break;

            case right:
                sprite = ghostRight[spriteNumberGhost];
                break;

            default:
                break;
        }

        spriteCounterGhost++;
        return sprite;
    }

    public Image getWallSprite (LabyrinthModel labyrinth, Wall wall) {
        int wallX = wall.getX() / tileSize;
        int wallY = wall.getY() / tileSize;

        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] direction : directions) {

            int xDirection = wallX + direction[0];
            int yDirection = wallY + direction[1];


            if (xDirection < 0 || xDirection >= labyrinth.mapColumns) {continue;}
            if (yDirection < 0 || yDirection >= labyrinth.mapRows) {continue;}

            EntityModel entity = labyrinth.getEntity(xDirection, yDirection);

            if (!(entity instanceof Wall)) {continue;}

            if(direction[1] == -1){up = true;}
            if(direction[1] == 1){down = true;}
            if(direction[0] == -1){left = true;}
            if(direction[0] == 1){right = true;}

        }

        // QUADRUPLE CONNECTED PIECE
        if( up && down && left && right ){return walls[14];}

        // NOT CONNECTED TO ANY DIRECTION
        else if( !up && !down && !left && !right ){return walls[15];}

        // SINGULAR CONNECTED EDGE PIECES
        else if( !up && down && !left && !right ){return walls[0];}
        else if( !up && !down && left && !right ){return walls[1];}
        else if( up && !down && !left && !right ){return walls[2];}
        else if( !up && !down && !left && right ){return walls[3];}

        // DOUBLE CONNECTED SIDE PIECES
        else if( !up && !down && left && right ){return walls[4];}
        else if( up && down && !left && !right ){return walls[5];}

        // DOUBLE CONNECTED CORNER PIECES
        else if( !up && down && !left && right ){return walls[6];}
        else if( !up && down && left && !right ){return walls[7];}
        else if( up && !down && left && !right ){return walls[8];}
        else if( up && !down && !left && right ){return walls[9];}

        // TRIPLE CONNECTED CORNER PIECES
        else if( up && down && !left && right ){return walls[10];}
        else if( !up && down && left && right ){return walls[11];}
        else if( up && down && left && !right ){return walls[12];}
        else if( up && !down && left && right ){return walls[13];}

        return null;
    }

    public Image getPortalSprite (){
        return portal;
    }

    public Image getExitSprite (){
        return exit;
    }

    public Image getTimePotion () {
        return timePotion;
    }
}
