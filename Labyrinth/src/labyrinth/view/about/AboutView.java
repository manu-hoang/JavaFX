package labyrinth.view.about;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class AboutView extends GridPane {
    Label authorImage;
    Label aboutText;
    Label home;

    public AboutView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        Image file;
        ImageView imageView;

        // AUTHOR IMAGE
        file = new Image("file:resources/images/blinky.png");
        imageView = new ImageView(file);
        authorImage = new Label("", imageView);

        // ABOUT TEXT
        aboutText = new Label("");

        // HOME BUTTON
        file = new Image("file:resources/images/home.png");
        imageView = new ImageView(file);
        home = new Label("", imageView);
    }

    private void layoutNodes() {
        authorImage.setMaxWidth(10);
        authorImage.setMaxHeight(10);
        this.add(authorImage, 0, 0);
        GridPane.setHalignment(authorImage, HPos.CENTER);


        aboutText.setText("BLINKY © WAS CREATED BY MANU HOANG \n STUDENT INF 106b");
        aboutText.setWrapText(true);
        aboutText.setAlignment(Pos.CENTER);
        aboutText.setTextAlignment(TextAlignment.CENTER);
        this.add(aboutText, 0, 1);
        GridPane.setHalignment(aboutText, HPos.CENTER);


        this.add(home, 0, 2);
        GridPane.setHalignment(home, HPos.CENTER);


        this.setAlignment(Pos.CENTER);
        this.setVgap(20); this.setHgap(20);
        this.setMaxWidth(500); this.setMaxHeight(500);

        this.getStyleClass().add("menu");
    }

    public Label getHome() {
        return home;
    }
}