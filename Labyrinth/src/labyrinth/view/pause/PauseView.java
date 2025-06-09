package labyrinth.view.pause;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PauseView extends GridPane {

    Label text;

    Label home, retry, next;

    public PauseView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        Image file;
        ImageView imageView;

        file = new Image("file:resources/images/home.png");
        imageView = new ImageView(file);

        home = new Label("", imageView);

        file = new Image("file:resources/images/retry.png");
        imageView = new ImageView(file);

        retry = new Label("", imageView);

        file = new Image("file:resources/images/next.png");
        imageView = new ImageView(file);

        next = new Label("", imageView);

        text = new Label("PAUSED");
    }

    private void layoutNodes() {
        this.add(text, 0, 0, 3, 1);
        GridPane.setHalignment(text, HPos.CENTER);

        this.add(home, 0, 1);
        this.add(retry, 1, 1);
        this.add(next, 2, 1);

        this.setAlignment(Pos.CENTER);
        this.setVgap(20);
        this.setHgap(20);

        this.setMaxWidth(250);
        this.setMaxHeight(150);

        this.getStyleClass().add("menu");
        this.getStyleClass().add("pause_menu");
        home.getStyleClass().add("pause_menu");
        retry.getStyleClass().add("pause_menu");
        next.getStyleClass().add("pause_menu");
        text.getStyleClass().add("pause_menu");
    }

    public Label getHome() {
        return home;
    }

    public Label getRetry() {
        return retry;
    }

    public Label getNext() {
        return next;
    }
}