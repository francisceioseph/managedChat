package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sample.model.MessageTuple;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by francisco on 14/04/15.
 */
public class ChatWindowController implements Initializable{
    public TextField messageTextField;
    public Label otherUserLabel;
    public ListView chatListView;

    public void enviarMensagem(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
