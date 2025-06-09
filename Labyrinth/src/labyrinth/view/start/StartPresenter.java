package labyrinth.view.start;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import labyrinth.model.DFSMazeGenerator;
import labyrinth.model.interfaces.ScoreOutputHandler;
import labyrinth.model.interfaces.SettingsHandler;
import labyrinth.view.ViewStack;
import labyrinth.view.about.AboutPresenter;
import labyrinth.view.about.AboutView;
import labyrinth.view.levels.LevelsPresenter;
import labyrinth.view.levels.LevelsView;
import labyrinth.view.options.OptionsPresenter;
import labyrinth.view.options.OptionsView;

import java.io.File;
import java.util.List;

import static labyrinth.view.levels.RandomLevelAnimation.startRandomLevelAnimation;

public class StartPresenter {
    private StartView view;

    public StartPresenter(StartView view) {
        this.view = view;
        this.addEventHandlers();
        updateScores();
    }

    // KEY / EVENT HANDLER
    private void addEventHandlers() {
        view.getPlay().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LevelsView levelsView  = new LevelsView();
                LevelsPresenter presenter = new LevelsPresenter(levelsView);

                ViewStack.getInstance().push(levelsView);
                levelsView.setVisible(true);

            }
        });

        view.getRandomLevel().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // Clear previous randomly generated level
                DFSMazeGenerator.clearDirectory(new File("resources/csv/random_maze_snapshots"));

                DFSMazeGenerator generator = new DFSMazeGenerator();

                int mazeWidth = SettingsHandler.getRandomMazeWidth();
                int mazeHeight = SettingsHandler.getRandomMazeHeight();

                generator.generateRandomMaze(mazeWidth,mazeHeight);

                // Show all iterations of DFS maze generation
                startRandomLevelAnimation();
            }
        });

        // Options button
        view.getOptions().setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OptionsView optionsView  = new OptionsView();
                ViewStack.getInstance().push(optionsView);;

                optionsView.setVisible(true); optionsView.requestFocus();

                new OptionsPresenter(optionsView);
            }
        });

        // About button
        view.getAbout().setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AboutView aboutView  = new AboutView();
                ViewStack.getInstance().push(aboutView);;

                aboutView.setVisible(true); aboutView.requestFocus();

                new AboutPresenter(aboutView);
            }
        });

        // Exit button
        view.getExit().setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) view.getScene().getWindow();
                stage.close();
            }
        });

        // If back on start screen -> update scoreboard
        view.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {updateScores();}
        });

        addCursorHandlers();
    }

    private void addCursorHandlers() {
        view.getPlay().setOnMouseEntered(event -> {
            view.getPlay().setCursor(Cursor.HAND);
        });

        view.getPlay().setOnMouseExited(event -> {
            view.getPlay().setCursor(Cursor.DEFAULT);
        });

        view.getRandomLevel().setOnMouseEntered(event -> {
            view.getRandomLevel().setCursor(Cursor.HAND);
        });

        view.getRandomLevel().setOnMouseExited(event -> {
            view.getRandomLevel().setCursor(Cursor.DEFAULT);
        });

        view.getOptions().setOnMouseEntered(event -> {
            view.getOptions().setCursor(Cursor.HAND);
        });

        view.getOptions().setOnMouseExited(event -> {
            view.getOptions().setCursor(Cursor.DEFAULT);
        });

        view.getAbout().setOnMouseEntered(event -> {
            view.getAbout().setCursor(Cursor.HAND);
        });

        view.getAbout().setOnMouseExited(event -> {
            view.getAbout().setCursor(Cursor.DEFAULT);
        });

        view.getExit().setOnMouseEntered(event -> {
            view.getExit().setCursor(Cursor.HAND);
        });

        view.getExit().setOnMouseExited(event -> {
            view.getExit().setCursor(Cursor.HAND);
        });
    }

    // Update high scores
    private void updateScores () {
        List<String> highScores = ScoreOutputHandler.getHighScores();
        if(highScores == null) {return;}

        List<Label> scoreLabels =  view.getScores();

        for ( int x = 0; x < highScores.size(); x++ ) {
            String score = highScores.get(x);
            scoreLabels.get(x).setText(score);
        }

    }

}