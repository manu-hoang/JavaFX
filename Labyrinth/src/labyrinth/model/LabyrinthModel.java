package labyrinth.model;

import labyrinth.model.entities.*;
import labyrinth.model.interfaces.ScoreOutputHandler;
import labyrinth.model.interfaces.SettingsHandler;

import static labyrinth.model.interfaces.CSVInputHandler.*;

import java.util.*;
import java.util.Random;

public class LabyrinthModel {

    // Singleton instances
    private static LabyrinthModel single_instance = null;
    private final StopwatchModel stopwatch;


    // public static final constanten
    public final int tileSize;
    public int mapColumns;
    public int mapRows;

    public int mapWidth;
    public int mapHeight;


    // ENTITIES
    private EntityModel[][] maze; // excluding player
    private List<Wall> walls;
    private Exit exit;

    private Player player;

    private final List<Portal> portals;
    private final List<Ghost> ghosts;
    private final List<TimePotion> timePotions;


    private boolean levelFinished;
    String inputFile;

    private LabyrinthModel() {
        player = null;

        maze = null;
        walls = new ArrayList<>();
        portals = new ArrayList<>();
        ghosts = new ArrayList<>();
        timePotions = new ArrayList<>();
        exit = null;

        tileSize = SettingsHandler.getTileSize();
        mapRows = -1;      // gets updated in load maze function
        mapColumns = -1;   // idem for columns

        levelFinished = false;
        this.stopwatch = StopwatchModel.getInstance();
    }

    // Static method to create instance of Singleton class
    public static synchronized LabyrinthModel getInstance() {
        if (single_instance == null){
            single_instance = new LabyrinthModel();
        } return single_instance;
    }

    /**
     * Reads in csv file and loads information into the model
     * Also clears previous level if applicable
     */
    public boolean init(String fileName) {
        if(!fileExists(fileName)) {
            System.out.println("Labyrinth csv file: " + fileName + " not found");
            return false;
        }

        inputFile = fileName;

        clearLevel();

        // Success if only 1 player and 1 exit
        boolean success = loadMaze(readMazeFromCSV(inputFile));
        if (!success) {return false;}

        mapWidth = tileSize * mapColumns;
        mapHeight = tileSize * mapRows;

        return true;
    }

    // Clear all information out of model
    // ( Entities basically )
    private void clearLevel() {
        player = null;
        maze = null;
        exit = null;

        walls.clear();
        portals.clear();
        timePotions.clear();
        ghosts.clear();
    }

    /**
     * Simulates a game "tick"
     * Move Player and ghosts if applicable and check for collisions
    * */
    public void tick() {
        // MOVE PLAYER
        if (player == null){return;}

        player.move();

        for (EntityModel[] row : maze) {
            for (EntityModel entity : row) {
                if(!collision(player,entity)){continue;}
                entity.actionWhenCollided(player);
            }
        }

        player.updateOnPortal();


        // MOVE GHOSTS
        for(Ghost ghost : ghosts){

            ghost.move();

            if(ghost.collided()) {

                // partially undo move until against wall
                // (until no collision)
                ghost.undoMove();

                // Corridor defined as 3 open directions
                // Front, Back and either (or both) Side(s)
                if(ghost.inCorridor()) {
                    ghost.calculateDirectionDFS();
                }

                else if(ghost.deadEnd()){
                    ghost.reverseDirection();
                }
            }

            if(collision(player,ghost)){ghost.actionWhenCollided(player);}
        }
    }

    // reset game
    public void reset() {
        for (EntityModel[] row : maze) {
            for (EntityModel entity : row) {
                if(entity == null){continue;}
                entity.reset();
            }
        }

        player.reset();

        for (Ghost ghost : ghosts) {
            ghost.reset();
        }

        levelFinished = false;
        this.stopwatch.reset();
    }

    // code snippet from https://developer.mozilla.org/en-US/docs/Games/Techniques/2D_collision_detection
    public boolean collision(EntityModel entity1, EntityModel entity2) {
        if(entity1 == null || entity2 == null) {return false;}

        return  entity1.getHitboxX() < entity2.getHitboxX() + entity2.getHitboxWidth() &&
                entity1.getHitboxX() + entity1.getHitboxWidth() > entity2.getHitboxX() &&
                entity1.getHitboxY() < entity2.getHitboxY() + entity2.getHitboxHeight() &&
                entity1.getHitboxY() + entity1.getHitboxHeight() > entity2.getHitboxY();
    }

    // Returns a random Portal
    // returns null if no portals found
    public Portal randomPortal() {
        if (portals.isEmpty()) {return null;}
        if (portals.size() == 1) {return portals.getFirst();}

        Random rand = new Random();
        return this.portals.get(rand.nextInt(this.portals.size()));
    }


    // GETTERS
    public EntityModel[][] getMaze() {
        return maze;
    }

    public Player getPlayer() {
        return player;
    }

    public EntityModel getEntity(int x, int y) {
        try {return maze[y][x];}
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("get entity failed" + e);
            return null;
        }
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Portal> getPortals() {
        return portals;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public List<TimePotion> getTimePotions() {
        return timePotions;
    }

    public Exit getExit() {
        return exit;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public boolean died() {
        if(this.player == null) {return false;}
        return this.player.getLives() <= 0;
    }

    public boolean LevelFinished() {
        return levelFinished;
    }

    public String getInputFile() {
        return inputFile;
    }

    // SETTERS
    public void setLevelFinished (boolean levelFinished) {
        this.levelFinished = levelFinished;
    }


    /**
     * * Loads information into LabyrinthModel class from char[][]
     * Also initializes all entities including:
     *      Players
     *      Ghosts
     *      Time Potions
     *      Exits
     *      Walls
     *      Portals
     *
     *   If char == null -> create abstract EntityModel class with no hitbox
     *   This way we can still access positions within the maze
     *   char 'C' is reserved for displaying focused node in DFS maze generation
    * */
    boolean loadMaze(char[][] maze) {

        assert maze != null;

        this.mapRows = maze.length;
        this.mapColumns = maze[0].length;

        this.maze = new EntityModel[mapRows][mapColumns];

        int playerCount = 0;
        int exitCount = 0;

        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {

                char entity = maze[y][x];
                EntityModel empty;

                switch (entity) {

                    case 'W':   // WALL
                        Wall wall = new Wall(x * tileSize, y * tileSize, tileSize, tileSize);
                        this.maze[y][x] = wall; this.walls.add(wall);
                        break;

                    case 'p':   // PORTAL
                        Portal portal = new Portal( x * tileSize, y * tileSize, tileSize, tileSize);
                        this.maze[y][x] = portal; this.portals.add(portal);
                        break;

                    case 'P':   // PLAYER
                        Player player = new Player(x * tileSize, y * tileSize, tileSize, tileSize);
                        this.player = player;
                        playerCount++;

                        empty = new EntityModel(x * tileSize, y * tileSize, tileSize, tileSize);
                        this.maze[y][x] = empty;
                        break;

                    case 'G':   // Ghost
                        Ghost ghost = new Ghost(x * tileSize, y * tileSize, tileSize, tileSize);
                        this.ghosts.add(ghost);

                        empty = new EntityModel(x * tileSize, y * tileSize, tileSize, tileSize);
                        this.maze[y][x] = empty;
                        break;

                    case 'E':   // EXIT
                        Exit exit = new Exit(x * tileSize, y * tileSize, tileSize, tileSize);
                        this.maze[y][x] = exit; this.exit = exit;
                        exitCount++;
                        break;

                    case 'T':   // TIME POTION
                        TimePotion timePotion = new TimePotion(x * tileSize, y * tileSize, tileSize, tileSize);
                        this.maze[y][x] = timePotion; this.timePotions.add(timePotion);
                        break;

                    case 'C':   // CURRENT FOCUSED NODE (DFS)
                        empty = new EntityModel(x * tileSize, y * tileSize, tileSize, tileSize);
                        empty.focused = true;
                        this.maze[y][x] = empty;
                        break;

                    default:
                        /* unknown input */
                        empty = new EntityModel(x * tileSize, y * tileSize, tileSize, tileSize);
                        this.maze[y][x] = empty;
                        break;
                }
            }
        }

        if (playerCount > 1) {
            System.out.println("Multiple players not allowed");
            return false;
        }

        if (exitCount > 1) {
            System.out.println("Multiple exits not allowed");
            return false;
        }

        return true;
    }

    // Writes out High score (if applicable) to highscores.txt in encrypted format
    public void outputScore(){
        String username = SettingsHandler.getUserName();
        int time = StopwatchModel.getInstance().getSeconds();
        ScoreOutputHandler.writeScore(username , time);
    }
}
