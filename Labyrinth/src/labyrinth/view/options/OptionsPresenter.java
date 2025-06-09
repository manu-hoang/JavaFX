package labyrinth.view.options;


import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import labyrinth.model.interfaces.SettingsHandler;
import labyrinth.view.ViewStack;

import java.util.Set;

/**
* TO BE IMPLEMENTED (not finished)
* */
public class OptionsPresenter {
    private OptionsView view;

    public OptionsPresenter(OptionsView view) {
        this.view = view;
        this.addEventHandlers();
    }

    private void addEventHandlers() {

        view.getWidthSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
            view.getWidthValue().setText("WIDTH: " + newValue.intValue());
        });

        view.getHeightSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
            view.getHeightValue().setText("HEIGHT: " + newValue.intValue());
        });

        view.getHome().setOnMouseClicked(event -> {
            ViewStack.getInstance().home();
            event.consume();
        });

        view.getSmiley().setOnMouseClicked(event -> {
            Alert a = new Alert(Alert.AlertType.NONE);

            // set alert type
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText(SettingsHandler.getUserName());

            // show the dialog
            a.show();
        });

        addCursorHandlers();
    }

    private void addCursorHandlers() {
        view.getHome().setOnMouseEntered(event -> {
            view.getHome().setCursor(Cursor.HAND);
        });

        view.getHome().setOnMouseExited(event -> {
            view.getHome().setCursor(Cursor.DEFAULT);
        });
    }

}