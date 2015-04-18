package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.controller.SpyWindowController;
import sample.helper.Singleton;

/**
 * Created by francisco on 18/04/15.
 */
public class SpyMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/spyWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Spy O.O");

        final SpyWindowController spyController = fxmlLoader.<SpyWindowController>getController();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {

                if (spyController.analyser != null)
                    spyController.analyser.alive = false;
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        Singleton.INSTANCE.tupleSpaceLookup();
        launch(args);
    }
}