package labyrinth.view.death;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class DeathView extends GridPane {

    Label congratulations;
    Label home, retry, next;

    public DeathView() {
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

        congratulations = new Label("YOU DIED");
    }

    private void layoutNodes() {
        this.add(congratulations, 0, 0, 3, 1);
        GridPane.setHalignment(congratulations, HPos.CENTER);

        this.add(home, 0, 1);
        this.add(retry, 1, 1);
        this.add(next, 2, 1);

        this.setAlignment(Pos.CENTER);
        this.setVgap(20);
        this.setHgap(20);

        this.setMaxWidth(250);
        this.setMaxHeight(150);

        this.getStyleClass().add("menu");
        this.getStyleClass().add("death_menu");
        home.getStyleClass().add("death_menu");
        retry.getStyleClass().add("death_menu");
        next.getStyleClass().add("death_menu");
        congratulations.getStyleClass().add("death_menu");

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