package labyrinth.view.game;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;
import labyrinth.model.LabyrinthModel;
import labyrinth.model.interfaces.SettingsHandler;
import labyrinth.model.StopwatchModel;
import labyrinth.model.entities.Player;
import labyrinth.view.ViewStack;
import labyrinth.view.death.DeathPresenter;
import labyrinth.view.death.DeathView;
import labyrinth.view.next_level.NextLevelPresenter;
import labyrinth.view.next_level.NextLevelView;
import labyrinth.view.pause.PausePresenter;
import labyrinth.view.pause.PauseView;

import java.util.Optional;

public class GamePresenter {
    private LabyrinthModel game;
    private GameView view;

    // MAIN GAME LOOP
    private AnimationTimer gameLoop;

    private boolean paused;

    public GamePresenter(GameView view) {
        this.game = LabyrinthModel.getInstance();
        this.view = view;

        paused = false;
        startGameLoop();

        StopwatchModel.getInstance().start();

        resumeGame();
        addEventHandlers();
        updateView();
    }

    // Main Game Loop
    private void startGameLoop () {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.tick();

                StopwatchModel.getInstance().fpsTick();

                if(game.died()) {
                    pauseGame();
                    deathScreen();
                }

                if (game.LevelFinished()) {
                    pauseGame();
                    game.outputScore();
                    nextLevelScreen();
                }

                updateView();
            }
        };
    }

    public void resumeGame() {
        gameLoop.start();
        StopwatchModel.getInstance().start();
    }

    public void pauseGame() {
        gameLoop.stop();
        StopwatchModel.getInstance().stop();
    }

    public void resetGame() {
        LabyrinthModel.getInstance().reset();

        addEventHandlers();
        paused = false;
    }

    private void addEventHandlers() {
        view.getScene().setOnKeyPressed( new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                Player player = game.getPlayer();
                if (player == null) {return;}

                // Get keys from settings
                KeyCode upKey = KeyCode.valueOf(SettingsHandler.getUpKey());
                KeyCode downKey = KeyCode.valueOf(SettingsHandler.getDownKey());
                KeyCode leftKey = KeyCode.valueOf(SettingsHandler.getLeftKey());
                KeyCode rightKey = KeyCode.valueOf(SettingsHandler.getRightKey());
                KeyCode sprintKey = KeyCode.valueOf(SettingsHandler.getSprintKey());

                // Compare event key with dynamic settings
                if (event.getCode() == upKey) {player.setGoNorth(true);}
                else if (event.getCode() == downKey) {player.setGoSouth(true);}
                else if (event.getCode() == leftKey) {player.setGoWest(true);}
                else if (event.getCode() == rightKey) {player.setGoEast(true);}
                else if (event.getCode() == sprintKey) {player.setSprint(true);}
                else if (event.getCode() == KeyCode.ESCAPE) {pauseScreen();}

                event.consume();
            }
        });

        view.getScene().setOnKeyReleased( new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                Player player = game.getPlayer();
                if (player == null) {return;}

                // Get keys from settings
                KeyCode upKey = KeyCode.valueOf(SettingsHandler.getUpKey());
                KeyCode downKey = KeyCode.valueOf(SettingsHandler.getDownKey());
                KeyCode leftKey = KeyCode.valueOf(SettingsHandler.getLeftKey());
                KeyCode rightKey = KeyCode.valueOf(SettingsHandler.getRightKey());
                KeyCode sprintKey = KeyCode.valueOf(SettingsHandler.getSprintKey());

                // Compare event key with dynamic settings
                if (event.getCode() == upKey) {player.setGoNorth(false);}
                else if (event.getCode() == downKey) {player.setGoSouth(false);}
                else if (event.getCode() == leftKey) {player.setGoWest(false);}
                else if (event.getCode() == rightKey) {player.setGoEast(false);}
                else if (event.getCode() == sprintKey) {player.setSprint(false);}

                event.consume();
            }
        });
    }

    public void removeEventHandlers() {
        view.getScene().setOnKeyPressed(null);
        view.getScene().setOnKeyReleased(null);
    }

    private void nextLevelScreen() {
        removeEventHandlers();

        NextLevelView nextLevelView = new NextLevelView();

        ViewStack.getInstance().push(nextLevelView);
        nextLevelView.setVisible(true);
        nextLevelView.requestFocus();

        new NextLevelPresenter(nextLevelView, this);
    }

    private void deathScreen() {
        removeEventHandlers();

        DeathView deathView = new DeathView();

        ViewStack.getInstance().push(deathView);
        deathView.setVisible(true);
        deathView.requestFocus();

        new DeathPresenter(deathView, this);
    }

    private void pauseScreen () {

        if (!paused) {
            paused = true;
            pauseGame();

            // Show Pause Menu
            PauseView pauseView = new PauseView();

            ViewStack.getInstance().push(pauseView);
            pauseView.setVisible(true);
            pauseView.requestFocus();

            new PausePresenter(pauseView, this);

        } else {
            paused = false;
            resumeGame();

            // Remove Pause Menu
            ViewStack.getInstance().pop();
            ViewStack.getInstance().peek().requestFocus();
        }

    }

    private void updateView() {
    // Vult de view met data uit model
        view.drawBackground();
        view.drawEntities(game);

        this.view.getTimerTime().setText(String.format("%02d", StopwatchModel.getInstance().getSeconds()));

        // Player information
        Player player = game.getPlayer();
        if(player == null) {return;}

        this.view.getLivesCount().setText(String.format("%02d", game.getPlayer().getLives()));
    }

    public void addWindowEventHandlers () {
        view.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                final Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
                exit.setHeaderText("Exit?");
                Optional<ButtonType> option = exit.showAndWait();
                if (option.get().getText().equalsIgnoreCase("CANCEL")) {
                    event.consume();
                }}});
    }

}