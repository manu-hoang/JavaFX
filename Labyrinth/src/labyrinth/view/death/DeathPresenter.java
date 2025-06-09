package labyrinth.view.death;

import javafx.scene.Cursor;
import labyrinth.model.LabyrinthModel;
import labyrinth.view.ViewStack;
import labyrinth.view.game.GamePresenter;

import static labyrinth.view.levels.LevelsPresenter.nextLevel;
import static labyrinth.view.levels.LevelsPresenter.nextLevelExists;

public class DeathPresenter {
    private LabyrinthModel model;
    private DeathView view;

    private GamePresenter gamePresenter;

    public DeathPresenter(DeathView view, GamePresenter game) {
        this.model = LabyrinthModel.getInstance();
        this.view = view;

        this.gamePresenter = game;
        this.addEventHandlers();

        // hide button if no next level
        updateView();
    }

    private void addEventHandlers() {

        view.getHome().setOnMouseClicked(event -> {
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