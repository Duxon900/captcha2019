package captcha2019;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Parent mainUI;
    private Stage stage;
    private Scene sceneM;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        stage.setTitle("Captcha");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        mainUI = (Parent) loader.load();
        sceneM = new Scene(mainUI);

        stage.setScene(sceneM);
        stage.show();
    }
}
