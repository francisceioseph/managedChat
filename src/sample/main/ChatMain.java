package sample.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.helper.javaSpaces.JavaSpacesSingleton;

public class ChatMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/signInWindow.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }


    public static void main(String[] args) {
        JavaSpacesSingleton.INSTANCE.tupleSpaceLookup();
        launch(args);
    }
}
