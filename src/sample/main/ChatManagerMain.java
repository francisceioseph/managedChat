package sample.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.helper.javaSpaces.JavaSpacesSingleton;

/**
 * Created by francisco on 20/04/15.
 */
public class ChatManagerMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/chatManager.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Chat Manager");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}