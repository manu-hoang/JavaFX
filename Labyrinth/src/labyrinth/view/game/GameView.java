package labyrinth.view.game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import labyrinth.model.*;
import labyrinth.model.entities.*;

public class GameView extends BorderPane {
    private Canvas canvas;

    private final double CANVAS_WIDTH;
    private final double CANVAS_HEIGHT;
    private final Color BACKGROUND_COLOR;

    SpriteHandler spriteHandler;
    GraphicsContext gc;

    private HBox topPane;
    private MenuItem afsluiten;

    private HBox bottomPane;

    private Label timerText;
    private Label timerTime;

    private Label livesText;
    private Label livesCount;

    // private Node attributen (controls)
    public GameView(int map_width, int map_height) {
        CANVAS_WIDTH = map_width;
        CANVAS_HEIGHT = map_height;

        // TODO: Read attributes out of css file?
        BACKGROUND_COLOR = Color.rgb(14,14,14,1);

        this.initialiseNodes();
        this.layoutNodes();

        spriteHandler = new SpriteHandler();
        spriteHandler.setSprites();
    }

    private void initialiseNodes() {
        // BACKGROUND
        this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.gc = this.canvas.getGraphicsContext2D();

        // TOP PANE
        this.topPane = new HBox();

        // MENU BAR
        this.afsluiten = new MenuItem("SOLVE MAZE");

        // BOTTOM PANE
        this.bottomPane = new HBox();

        // TIMER
        this.timerText = new Label("TIME:");
        this.timerTime =  new Label();

        this.livesText = new Label("LIVES:");
        this.livesCount =  new Label();
    }

    private void layoutNodes() {

        // TOP
        final Menu bestandMenu = new Menu("HELP", null, this.afsluiten);
        final MenuBar menuBar = new MenuBar(bestandMenu);

        topPane.getChildren().addAll(menuBar);
        this.setTop(topPane);
        topPane.setMinHeight(Region.USE_PREF_SIZE);


        // CENTER
        this.setCenter(this.canvas);

        // BOTTOM
        bottomPane.setSpacing(20);
        bottomPane.setPadding(new Insets(20));
        bottomPane.getChildren().addAll(this.timerText, this.timerTime, this.livesText, this.livesCount);

        this.setBottom(bottomPane);
        bottomPane.setMinHeight(Region.USE_PREF_SIZE);

    }

    void drawEntities(LabyrinthModel maze) {

        // Wall sprites
        drawWallSprites(maze);

        // Exit sprites
        drawExit(maze);

        // Portal sprites
        drawPortalSprites(maze);

        // Potion sprites
        drawTimePotionSprites(maze);

        // Player sprite
        drawPlayer(maze);

        // Ghost sprites
        drawGhostSprites(maze);

        // DFS
        drawFocused(maze);
    }

    private void drawWallSprites (LabyrinthModel maze) {
        Image sprite;
        for (Wall wall : maze.getWalls()) {
            sprite = spriteHandler.getWallSprite(maze, wall);
            gc.drawImage(sprite, wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
        }
    }

    private void drawPortalSprites (LabyrinthModel maze) {
        Image sprite;
        for (Portal portal : maze.getPortals()) {
            sprite = spriteHandler.getPortalSprite();
            gc.drawImage(sprite, portal.getX(), portal.getY(), portal.getWidth(), portal.getHeight());
        }
    }

    private void drawTimePotionSprites (LabyrinthModel maze) {
        Image sprite;
        for (TimePotion potion : maze.getTimePotions()) {
            if(!potion.isConsumed()) {
                sprite = spriteHandler.getTimePotion();
                gc.drawImage(sprite, potion.getHitboxX(), potion.getHitboxY(), potion.getHitboxWidth(), potion.getHitboxHeight());
            }
        }
    }

    private void drawPlayer (LabyrinthModel maze) {
        Player player = maze.getPlayer();
        if(player == null){return;}

        Image sprite;
        sprite = spriteHandler.getPlayerSprite(player);
        gc.drawImage(sprite, player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    private void drawGhostSprites (LabyrinthModel maze) {
        Image sprite;
        for (Ghost ghost : maze.getGhosts()) {
            sprite = spriteHandler.getGhostSprite(ghost);
            gc.drawImage(sprite, ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight());
        }
    }

    private void drawExit (LabyrinthModel maze) {
        Exit exit = maze.getExit();
        if(exit == null){return;}

        Image sprite;
        sprite = spriteHandler.getExitSprite();
        gc.drawImage(sprite, exit.getX(), exit.getY(), exit.getWidth(), exit.getHeight());
    }

    private void drawFocused (LabyrinthModel maze) {
        EntityModel[][] map = maze.getMaze();

        for(EntityModel[] row : map){
            for(EntityModel entity : row){
                if(!entity.focused){ continue; }

                gc.setFill(Color.WHITE);
                gc.fillOval(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
            }
        }
    }

    public void drawBackground() {
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    // implementatie van de nodige
    // package-private Getters
    Label getTimerTime() { return timerTime; }

    public Label getLivesCount() {
        return livesCount;
    }
}