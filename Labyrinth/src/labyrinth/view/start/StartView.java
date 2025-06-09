package labyrinth.view.start;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class StartView extends GridPane {
    private VBox buttons;
    private Label blinky;
    private Button play, options, randomLevel, about, exit;

    StackPane labyrinthStack;
    Label smallText, largeText;

    VBox highScoresContainer;
    Label highScores;
    Label score1, score2, score3, score4, score5;

    // private Node attributen (controls)
    public StartView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        labyrinthStack = new StackPane();
        this.smallText = new Label("LABYRINTH");
        this.largeText = new Label("LABYRINTH");

        buttons = new VBox();

        // Start button
        play = new Button("PLAY");
        play.getStyleClass().add("start_button");

        // Random level button
        randomLevel = new Button("RANDOM");
        randomLevel.getStyleClass().add("start_button");

        // Options button
        options = new Button("OPTIONS");
        options.getStyleClass().add("start_button");

        // About button
        about = new Button("ABOUT");
        about.getStyleClass().add("start_button");

        // Exit button
        exit = new Button("EXIT");
        exit.getStyleClass().add("start_button");

        // Blinky
        Image blinkyImg = new Image("file:resources/images/blinky.png");
        ImageView blinkyImgView = new ImageView(blinkyImg);

        this.blinky = new Label("", blinkyImgView);

        // Highscores
        highScoresContainer = new VBox();
        highScores = new Label();
        score1 = new Label();
        score2 = new Label();
        score3 = new Label();
        score4 = new Label();
        score5 = new Label();
    }

    private void layoutNodes() {

        // Labyrinth label
        labyrinthStack.getChildren().addAll(largeText, smallText);
        smallText.getStyleClass().add("small_text");
        largeText.getStyleClass().add("large_text");
        labyrinthStack.setPadding(new Insets(20));
        labyrinthStack.setMaxWidth(Double.MAX_VALUE);
        labyrinthStack.setAlignment(Pos.CENTER);

        // Play buttons
        play.setMinWidth(Region.USE_PREF_SIZE);
        randomLevel.setMinWidth(Region.USE_PREF_SIZE);
        options.setMinWidth(Region.USE_PREF_SIZE);
        about.setMinWidth(Region.USE_PREF_SIZE);
        exit.setMinWidth(Region.USE_PREF_SIZE);

        buttons.setSpacing(20);
        buttons.getChildren().addAll(play, randomLevel, options, about, exit);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));

        // High scores table
        highScores.setText("HIGHSCORES");
        highScoresContainer.getChildren().addAll(highScores, score1, score2, score3, score4, score5);

        highScores.setPadding(new Insets(20));
        highScores.getStyleClass().add("high_scores_title");
        score1.setPadding(new Insets(10));
        score2.setPadding(new Insets(10));
        score3.setPadding(new Insets(10));
        score4.setPadding(new Insets(10));
        score5.setPadding(new Insets(10));
        highScoresContainer.setAlignment(Pos.CENTER);

        this.add(buttons, 0 , 1);
        this.add(labyrinthStack, 0, 0);
        this.add(blinky,1, 0, 1, 2);
        GridPane.setHalignment(blinky, HPos.CENTER);
        this.add(highScoresContainer, 2, 0, 1, 2);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(30.0);

        ColumnConstraints column2 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        column2.setPercentWidth(40.0);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(30.0);
        this.getColumnConstraints().addAll(column1, column2, column3);

        this.setAlignment(Pos.CENTER);
        this.setVgap(20);
        this.setHgap(100);

        this.setPadding(new Insets(50));

        highScoresContainer.getStyleClass().add("high_scores_menu");
    }

    // GETTERS

    public Button getPlay() {
        return play;
    }

    public Button getRandomLevel() {
        return randomLevel;
    }

    public Button getOptions() {
        return options;
    }

    public Button getAbout() {
        return about;
    }

    public Button getExit() {
        return exit;
    }

    public List<Label> getScores () {
        List<Label> scores = new ArrayList<>();
        scores.add(score1);
        scores.add(score2);
        scores.add(score3);
        scores.add(score4);
        scores.add(score5);
        return scores;
    }
}