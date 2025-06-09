package labyrinth.view.options;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

/**
 * TO BE IMPLEMENTED (not finished)
 * */
public class OptionsView extends GridPane {
    Label authorImage;

    Slider widthSlider;
    Label widthValue;

    Slider heightSlider;
    Label heightValue;

    Label home;

    Label smiley;

    public OptionsView() {
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

        widthSlider = new Slider();
        widthValue =  new Label("");

        heightSlider = new Slider();
        heightValue =  new Label("");

        // HOME BUTTON
        file = new Image("file:resources/images/home.png");
        imageView = new ImageView(file);
        home = new Label("", imageView);

        // SMILEY
        file = new Image("file:resources/images/smiley.png");
        imageView = new ImageView(file);
        smiley = new Label("", imageView);
    }

    private void layoutNodes() {

        List<Slider> sliders = new ArrayList<>();
        sliders.add(widthSlider); sliders.add(heightSlider);

        for (Slider slider : sliders) {
            slider.setOrientation(Orientation.HORIZONTAL);

            slider.setMax(100);
            slider.setMin(10);

            slider.setBlockIncrement(1); // Step size for arrow keys
            slider.setMajorTickUnit(10);
            slider.setMinorTickCount(9);
            slider.setSnapToTicks(true); // Ensure it only moves in whole numbers

            slider.setShowTickMarks(true);

            GridPane.setHalignment(slider, HPos.CENTER);
        }

        widthSlider.setMaxWidth(Double.MAX_VALUE);
        heightSlider.setMaxWidth(Double.MAX_VALUE);
        this.add(widthSlider, 1, 1);
        this.add(heightSlider, 1, 3);

        widthValue.setText("WIDTH: " + widthSlider.getValue());
        widthValue.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(widthValue, HPos.CENTER);
        this.add(widthValue, 1, 2);

        heightValue.setText("HEIGHT: " + heightSlider.getValue());
        heightValue.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(heightValue, HPos.CENTER);
        this.add(heightValue, 1, 4);

        this.add(home, 1, 5);
        GridPane.setHalignment(home, HPos.CENTER);

        this.add(smiley, 0, 3);

        this.setAlignment(Pos.CENTER);
        this.setVgap(20);
        this.setHgap(20);
        this.setMaxWidth(500); this.setMaxHeight(500);

        this.getStyleClass().add("menu");
        this.getStyleClass().add("options_menu_border");
        home.getStyleClass().add("options_menu");
        widthSlider.getStyleClass().add("options_menu");
        heightSlider.getStyleClass().add("options_menu");
        widthValue.getStyleClass().add("options_menu");
        heightValue.getStyleClass().add("options_menu");
    }

    public Label getHome() {
        return home;
    }

    public Slider getWidthSlider() {
        return widthSlider;
    }

    public Slider getHeightSlider() {
        return heightSlider;
    }

    public Label getWidthValue() {
        return widthValue;
    }

    public Label getHeightValue() {
        return heightValue;
    }

    public Label getSmiley() {
        return smiley;
    }
}