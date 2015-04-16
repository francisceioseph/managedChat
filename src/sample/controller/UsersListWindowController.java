package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnusableEntryException e) {
            e.printStackTrace();
        }

        this.usersList.setItems(Singleton.INSTANCE.chatUsers);

        this.usersList.setCellFactory(new Callback<ListView<UserInformationTuple>, ListCell<UserInformationTuple>>() {
            @Override
            public ListCell<UserInformationTuple> call(ListView<UserInformationTuple> userInformationTupleListView) {
                return new UserListCell();
            }
        });

        this.usersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserInformationTuple>() {
            @Override
            public void changed(ObservableValue<? extends UserInformationTuple> observableValue, UserInformationTuple userInformationTuple, UserInformationTuple t1) {

            }
        });
    }

    public void populateUsersFromChat() throws RemoteException, TransactionException, InterruptedException, UnusableEntryException {

        UserInformationTuple template = new UserInformationTuple();
        Singleton.INSTANCE.chatUsers.addAll(Singleton.INSTANCE.listChatUsers(null));
    }

    static class UserListCell extends ListCell<UserInformationTuple>{
        @Override
        public void updateItem(UserInformationTuple item, boolean empty) {
            super.updateItem(item, empty);
            Label label = new Label();

            if (item != null) {
                label.setText(item.username);
                setGraphic(label);
            }
        }
    }
}

