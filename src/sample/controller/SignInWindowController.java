package sample.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import sample.helper.Singleton;

import java.rmi.RemoteException;

public class SignInWindowController {
    public TextField usernameTextField;
    public PasswordField passwordTextField;

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
}
