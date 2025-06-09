package labyrinth.view.levels;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import labyrinth.model.LabyrinthModel;
import labyrinth.view.ViewStack;
import labyrinth.view.game.GamePresenter;
import labyrinth.view.game.GameView;

import java.io.File;
import java.util.*;

public class RandomLevelAnimation {

    static int counter = 0;
    static GamePresenter lastPresenter = null;

    static public void startRandomLevelAnimation() {
        File dir = new File("resources/csv/random_maze_snapshots");
        File[] directoryListing = dir.listFiles();

        Map<Integer, String> treeMap = new TreeMap<>();

        for (File file : directoryListing){
            int fileCount = Integer.parseInt(file.getName().replaceAll("\\D+", ""));
            treeMap.put(fileCount,file.getName());
        }

        LabyrinthModel gameModel = LabyrinthModel.getInstance();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            ViewStack.getInstance().home();

            gameModel.init("random_maze_snapshots/" + treeMap.get(counter));

            GameView gameView  = new GameView(gameModel.getMapWidth(), gameModel.getMapHeight());

            ViewStack.getInstance().push(gameView);
            gameView.setVisible(true);

            lastPresenter = new GamePresenter(gameView);
            lastPresenter.pauseGame();

            counter++;
        }));

        timeline.setCycleCount(treeMap.size()); // Run once for each GridPane
        timeline.play();

        timeline.setOnFinished(event -> {
            lastPresenter.resumeGame();
            lastPresenter.resetGame();
            counter = 0;
        });
    }

}
