package labyrinth.view.levels;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import labyrinth.model.LabyrinthModel;
import labyrinth.view.ViewStack;
import labyrinth.view.game.GamePresenter;
import labyrinth.view.game.GameView;

import java.util.List;

import static labyrinth.model.interfaces.CSVInputHandler.fileExists;

public class LevelsPresenter {
    private LevelsView view;

    public LevelsPresenter(LevelsView view) {
        this.view = view;
        this.addEventHandlers();
    }

    private void addEventHandlers() {
        // iterative handlers
        setLevelsEventHandlers(view.getButtons());
    }

    private void setLevelsEventHandlers(List<Button> buttons) {

        for ( int i = 0; i < buttons.size(); i++ ) {

            int buttonNumber = i;

            buttons.get(i).setOnMouseClicked(event -> {

                String level = "level" + (buttonNumber + 1) + ".csv";

                if(!fileExists(level)) {
                    System.out.println("Level does not exist");
                    event.consume();
                    return;
                }

                LabyrinthModel gameModel = LabyrinthModel.getInstance();
                boolean success = gameModel.init(level);

                if(!success) {
                    System.out.println("Level not valid");
                    event.consume();
                    return;
                }

                GameView gameView  = new GameView(gameModel.getMapWidth(), gameModel.getMapHeight());

                ViewStack.getInstance().push(gameView);
                gameView.setVisible(true);
                gameView.requestFocus();

                GamePresenter gamePresenter = new GamePresenter(gameView);

                event.consume();
            });
        }

        view.getReturnButton().setOnMouseClicked(event -> {
            ViewStack.getInstance().home();
            event.consume();
        });

    }

    public static void nextLevel(MouseEvent event, LabyrinthModel model) {
        String currentLevel = model.getInputFile();
        int count = Integer.parseInt(currentLevel.replaceAll("\\D+","")); // remove all non-digits
        count++;
        String next = "level" + count + ".csv";

        if(!fileExists(next)) {
            System.out.println("Next level does not exist");
            event.consume();
            return;
        }

        LabyrinthModel gameModel = LabyrinthModel.getInstance();
        boolean success = gameModel.init(next);

        if(!success) {
            System.out.println("Level not valid");
            event.consume();
            return;
        }

        GameView nextLevel  = new GameView(gameModel.getMapWidth(), gameModel.getMapHeight());
        ViewStack.getInstance().home();
        ViewStack.getInstance().push(nextLevel);

        nextLevel.setVisible(true);
        nextLevel.requestFocus();

        GamePresenter gamePresenter = new GamePresenter(nextLevel);
        gamePresenter.addWindowEventHandlers();

        event.consume();
    }

    public static boolean nextLevelExists(LabyrinthModel model) {
        String currentLevel = model.getInputFile();
        int count = Integer.parseInt(currentLevel.replaceAll("\\D+","")); // remove all non-digits
        count++;
        String next = "level" + count + ".csv";

        if(!fileExists(next)) {
            return false;
        }

        return true;
    }
}