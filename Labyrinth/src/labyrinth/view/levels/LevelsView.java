package labyrinth.view.levels;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LevelsView extends GridPane {
    ScrollPane levelsScrollPane;
    VBox levelsBox;
    List<Button> buttons;

    Button returnButton;

    public LevelsView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        levelsScrollPane = new ScrollPane();
        levelsBox = new VBox();
        buttons = new ArrayList<>();

        int levelsCount = 0;

        Path directory = Paths.get("resources/csv");
        try { levelsCount = (int) Files.list(directory).count(); }
        catch (IOException e) {System.out.println("Error reading levels directory: " + e.getMessage());}

        createLevelButtons(levelsCount);

        returnButton = new Button("RETURN");
    }

    void createLevelButtons(int amount) {

        for (int i = 1; i <= amount; i++) {
            Button button = new Button("LEVEL " + i);
            buttons.add(button);
            button.getStyleClass().add("start_button");

            Image file = new Image("file:resources/images/blinky.png");
            ImageView imageView = new ImageView(file);

            imageView.setFitHeight(250);
            imageView.setFitWidth(250);

            Label image = new Label("", imageView);
            levelsBox.getChildren().addAll(image, button);
        }

    }

    private void layoutNodes() {
        levelsScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        levelsScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        levelsScrollPane.setFitToWidth(true);
        levelsBox.setSpacing(50);

        levelsScrollPane.setPadding(new Insets(20, 50, 20, 50));

        returnButton.getStyleClass().add("start_button");

        this.setAlignment(Pos.CENTER);

        levelsScrollPane.setContent(levelsBox);

        this.add(levelsScrollPane, 0, 0);
        this.add(returnButton, 0, 1);
        this.setVgap(50);
        GridPane.setHalignment(returnButton, HPos.CENTER);

        this.setPadding(new Insets(100,0,100,0));

        levelsScrollPane.getStyleClass().add("levels_scroll");
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public Button getReturnButton() {
        return returnButton;
    }
}