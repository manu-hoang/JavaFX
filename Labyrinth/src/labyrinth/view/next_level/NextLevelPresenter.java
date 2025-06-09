package labyrinth.view.next_level;

import javafx.scene.Cursor;
import labyrinth.model.LabyrinthModel;
import labyrinth.view.ViewStack;
import labyrinth.view.game.GamePresenter;

import static labyrinth.view.levels.LevelsPresenter.nextLevel;
import static labyrinth.view.levels.LevelsPresenter.nextLevelExists;

public class NextLevelPresenter {
    private LabyrinthModel model;
    private NextLevelView view;

    private GamePresenter presenter;

    public NextLevelPresenter(NextLevelView view, GamePresenter gamePresenter) {
        this.model = LabyrinthModel.getInstance();
        this.view = view;

        this.presenter = gamePresenter;
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
            presenter.resetGame();
            presenter.resumeGame();

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