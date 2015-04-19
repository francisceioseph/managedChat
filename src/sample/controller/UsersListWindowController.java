package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import sample.main.ChatMain;
import sample.helper.javaSpaces.JavaSpacesSingleton;
import sample.model.UserInformationTuple;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by francisco on 14/04/15.
 */
public class UsersListWindowController implements Initializable{
    public ListView<UserInformationTuple> usersList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            this.populateUsersFromChat();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.usersList.setItems(JavaSpacesSingleton.INSTANCE.chatUsers);

        this.usersList.setCellFactory(new Callback<ListView<UserInformationTuple>, ListCell<UserInformationTuple>>() {
            @Override
            public ListCell<UserInformationTuple> call(ListView<UserInformationTuple> userInformationTupleListView) {
                return new UserListCell();
            }
        });
    }

    public void populateUsersFromChat() throws RemoteException, TransactionException, InterruptedException, UnusableEntryException {
        UserInformationTuple template = new UserInformationTuple();
        JavaSpacesSingleton.INSTANCE.chatUsers.addAll(JavaSpacesSingleton.INSTANCE.listChatUsers(template));
    }

    public void updateUserList(ActionEvent actionEvent) {
        try {
            JavaSpacesSingleton.INSTANCE.chatUsers.clear();
            this.populateUsersFromChat();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class UserListCell extends ListCell<UserInformationTuple>{
        private final Font labelFont = new Font(15);

        @Override
        public void updateItem(UserInformationTuple item, boolean empty) {
            super.updateItem(item, empty);

            final HBox hBox = new HBox();
            final Label label = new Label();

            if (item != null) {
                label.setText(item.username);
                label.setFont(labelFont);

                final String username = item.username;

                hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Stage stage = loadChatWindow(username);
                        stage.setTitle(JavaSpacesSingleton.INSTANCE.username + "  talks to  " + username);
                        stage.show();
                    }
                });

                hBox.getChildren().add(label);
                setGraphic(hBox);
            }
        }

        public Stage loadChatWindow(String username) {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatMain.class.getResource("../view/chatWindow.fxml"));

            try {
                Parent root = (Parent) fxmlLoader.load();

                final ChatWindowController controller = fxmlLoader.<ChatWindowController>getController();
                controller.anotherUsername = username;

                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        controller.finder.alive = false;
                    }
                });

                return stage;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

