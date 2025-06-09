package trivial_pursuit;

import trivial_pursuit.model.BoardModel;
import trivial_pursuit.view.ApplicatieNaamPresenter;
import trivial_pursuit.view.ApplicatieNaamView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        BoardModel model = new BoardModel();
        ApplicatieNaamView view = new ApplicatieNaamView();
        new ApplicatieNaamPresenter(model, view);

        primaryStage.setScene(new Scene(view));

        primaryStage.setMinWidth(1920);
        primaryStage.setMinHeight(1080);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
