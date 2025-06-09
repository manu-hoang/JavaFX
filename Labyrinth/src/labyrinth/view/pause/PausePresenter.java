package labyrinth.view.pause;

import javafx.scene.Cursor;
import labyrinth.model.LabyrinthModel;
import labyrinth.view.ViewStack;
import labyrinth.view.game.GamePresenter;

import static labyrinth.view.levels.LevelsPresenter.nextLevel;
import static labyrinth.view.levels.LevelsPresenter.nextLevelExists;

public class PausePresenter {
    private LabyrinthModel model;
    private PauseView view;

    private GamePresenter gamePresenter;

    public PausePresenter(PauseView view, GamePresenter gamePresenter) {
        this.model = LabyrinthModel.getInstance();
        this.view = view;

        this.gamePresenter = gamePresenter;
        this.addEventHandlers();

        // hide button if no next level
        updateView();
    }

    private void addEventHandlers() {

        view.getHome().setOnMouseClicked(event -> {
            gamePresenter.removeEventHandlers();
            model.reset();
            ViewStack.getInstance().home();
            event.consume();
        });

        view.getRetry().setOnMouseClicked(event -> {
            gamePresenter.resetGame();
            gamePresenter.resumeGame();

            ViewStack.getInstance().pop();
            ViewStack.getInstance().peek().requestFocus();

            event.consume();
        });

        view.getNext().setOnMouseClicked(event -> {
            model.reset();

            // Previous game model
            gamePresenter.removeEventHandlers();

            nextLevel(event, model);
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

        view.getRetry().setOnMouseEntered(event -> {
            view.getRetry().setCursor(Cursor.HAND);
        });

        view.getRetry().setOnMouseExited(event -> {
            view.getRetry().setCursor(Cursor.DEFAULT);
        });

        view.getNext().setOnMouseEntered(event -> {
            view.getNext().setCursor(Cursor.HAND);
        });

        view.getNext().setOnMouseExited(event -> {
            view.getNext().setCursor(Cursor.DEFAULT);
        });
    }

    private void updateView() {
        if (!nextLevelExists(model)) {
            view.getNext().setVisible(false);
        }
    }

}