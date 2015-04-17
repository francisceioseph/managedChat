package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import sample.helper.Singleton;
import sample.model.UserInformationTuple;

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

        this.usersList.setItems(Singleton.INSTANCE.chatUsers);

        this.usersList.setCellFactory(new Callback<ListView<UserInformationTuple>, ListCell<UserInformationTuple>>() {
            @Override
            public ListCell<UserInformationTuple> call(ListView<UserInformationTuple> userInformationTupleListView) {
                return new UserListCell();
            }
        });
    }

    public void populateUsersFromChat() throws RemoteException, TransactionException, InterruptedException, UnusableEntryException {
        UserInformationTuple template = new UserInformationTuple();
        Singleton.INSTANCE.chatUsers.addAll(Singleton.INSTANCE.listChatUsers(new UserInformationTuple()));
    }

    public void updateUserList(ActionEvent actionEvent) {
        try {
            Singleton.INSTANCE.chatUsers.clear();
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

                hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Stage stage = Singleton.INSTANCE.loadWindow("view/chatWindow.fxml");
                        stage.setTitle(label.getText());
                        stage.show();
                    }
                });
                hBox.getChildren().add(label);
                setGraphic(hBox);
            }
        }
    }
}

