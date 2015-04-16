package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import sample.Main;
import sample.helper.Singleton;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class SignInWindowController implements Initializable{
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label labelId;

    public void signUp(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        try {
            Singleton.INSTANCE.signUp(username, password);
        }
        catch (RemoteException e) {
            System.out.println("Connection Error...");
        }
        catch (TransactionException e) {
            System.out.println("Connection Error...");
        }
    }

    public void signIn(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        try {
            boolean userExists = Singleton.INSTANCE.signIn(username, password);

            if (userExists) {
                System.out.println("User Logged");
                Singleton.INSTANCE.setUsername(username);

                this.loadUsersListWindow();
                this.closeWindow();
            }
            else {
                System.out.println("User not Logged");
            }
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (UnusableEntryException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this.labelId.getStyleClass().add("chat-bubble-from");
    }

    private void loadUsersListWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/usersListWindow.fxml"));

        try {
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow(){
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.close();
    }
}
