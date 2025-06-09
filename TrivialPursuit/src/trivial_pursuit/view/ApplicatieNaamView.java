package trivial_pursuit.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ApplicatieNaamView extends StackPane {

    private Circle circle;

    // private Node attributen (controls)
    public ApplicatieNaamView () {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
    // Initialisatie van de Nodes
    // bvb.:
    // button = new Button("...")
    // label = new Label("...")

        circle = new Circle();

        circle.setRadius(200);
    }

    private void layoutNodes() {
    // Layout van de Nodes
    // add... methodes (of set...)
    // Insets, padding, alignment, ..
    }

    void createSpokes(double amount) {
        double angle = 360 / amount;

        for (int i = 0; i < amount; i++) {
            Line line = new Line();
            line.setFill(Color.RED);

            line.setStartX(circle.getCenterX());
            line.setStartY(circle.getCenterY());

            line.setEndX(Math.cos(angle * i) * circle.getRadius() + circle.getCenterX());
            line.setEndY(Math.sin(angle * i) * circle.getRadius() + circle.getCenterY());
            // ?????????????????? I dont get it
            this.getChildren().addAll(line);
        }

    }

    // implementatie van de nodige
    // package-private Getters
}