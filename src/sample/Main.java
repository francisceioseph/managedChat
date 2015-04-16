package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.jini.core.transaction.TransactionException;
import sample.helper.Singleton;
import sample.model.UserInformationTuple;

import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/signInWindow.fxml"));
        primaryStage.setScene(new Scene(root));

        primaryStage.getScene().getStylesheets().add(getClass().getResource("view/bubbleFrom.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        Singleton.INSTANCE.tupleSpaceLookup();

        try {
//            if (System.getSecurityManager() == null)
//                System.setSecurityManager(new RMISecurityManager());

            Singleton.INSTANCE.registerForNewUserSubscription(new UserInformationTuple());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
