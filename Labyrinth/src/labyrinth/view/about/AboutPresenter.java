package labyrinth.view.about;

import javafx.scene.Cursor;
import labyrinth.view.ViewStack;

public class AboutPresenter {
    private AboutView view;

    public AboutPresenter(AboutView view) {
        this.view = view;
        this.addEventHandlers();
    }

    private void addEventHandlers() {

        view.getHome().setOnMouseClicked(event -> {
            ViewStack.getInstance().home();
            event.consume();
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