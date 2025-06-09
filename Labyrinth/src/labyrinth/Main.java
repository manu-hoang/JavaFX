package labyrinth;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import labyrinth.view.ViewStack;
import labyrinth.view.start.StartPresenter;
import labyrinth.view.start.StartView;

import java.io.IOException;
import java.util.Formatter;


public class Main extends Application {

    @Override
    public void start(Stage primary_stage) {
        StartView start_view = new StartView();
        ViewStack.getInstance().push(start_view);

        new StartPresenter(start_view);

        Scene scene = new Scene(ViewStack.getInstance().getStackPane());
        scene.getStylesheets().add("file:resources/stylesheets/stylesheet.css");

        primary_stage.setScene(scene);

        // minimum screen size = 1600x900 (HD+ 16:9 aspect ratio)
        primary_stage.setMinWidth(1920);
        primary_stage.setMinHeight(1080);
        primary_stage.setFullScreen(true);
        primary_stage.setFullScreenExitHint("");
        primary_stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        primary_stage.show();
    }

    public static void main(String[] args) {

        Application.launch(args);
    }

}
