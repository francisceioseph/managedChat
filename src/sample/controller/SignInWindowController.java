package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import sample.helper.javaSpaces.JavaSpacesSingleton;
import sample.model.UserInformationTuple;

import javax.swing.*;
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

        UserInformationTuple userTuple = this.retreaveUserByName(username);

        if(userTuple == null){
            this.registerNewUser(username, password);
        }
        else{
            JOptionPane.showMessageDialog(null, "Usuário já cadastrado!", "Aviso", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void registerNewUser(String username, String password) {
        try {
            JavaSpacesSingleton.INSTANCE.signUp(username, password);
            JOptionPane.showMessageDialog(null, "Usuário Cadastrado com Sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (RemoteException e) {
            System.out.println("Connection Error...");
        }
        catch (TransactionException e) {
            System.out.println("Connection Error...");
        }
    }

    private UserInformationTuple retreaveUserByName(String username) {

        UserInformationTuple template = new UserInformationTuple(null, username);
        try {

            UserInformationTuple retreavedTuple = JavaSpacesSingleton.INSTANCE.readUserTuple(template);
            return retreavedTuple;

        } catch (Exception e) {
            return null;
        }

    }

    public void signIn(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        try {
            boolean userExists = JavaSpacesSingleton.INSTANCE.signIn(username, password);

            if (userExists) {
                System.out.println("User Logged");
                JavaSpacesSingleton.INSTANCE.setUsername(username);

                Stage stage = JavaSpacesSingleton.INSTANCE.loadWindow("../view/usersListWindow.fxml");
                stage.setTitle("Managed Chat: " + username);
                stage.show();

                this.closeWindow();
            }
            else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado no sistema...", "Aviso", JOptionPane.ERROR_MESSAGE);
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
    }

    private void closeWindow(){
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.close();
    }
}
