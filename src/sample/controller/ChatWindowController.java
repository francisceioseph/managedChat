package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import sample.helper.Singleton;
import sample.model.MessageTuple;
import sample.model.UserInformationTuple;
import sun.plugin2.message.Message;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by francisco on 14/04/15.
 */
public class ChatWindowController implements Initializable{
    public TextField messageTextField;
    private String anotherUsername;

    public ListView<Label> chatListView;
    private ObservableList<Label> messages;

    public void enviarMensagem(ActionEvent actionEvent) {
        MessageTuple tupleToSend = new MessageTuple(
                Singleton.INSTANCE.username,
                anotherUsername,
                messageTextField.getText(),
                false
        );

        messages.add(this.makeChatBubble(messageTextField.getText(), true));

        try {
            Singleton.INSTANCE.writeMessageOnSpace(tupleToSend);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    public Label makeChatBubble(String message, boolean direction){
        Label label = new Label(message);
        label.setFont(new Font(16));

        if (direction) {
            label.setStyle("-fx-shape:\"m 211.78571,327.00506 265.35715,1.42857 c 3.43583,0.42441 7.17703,0.33987 7.85714,5.35715 l 0.35714,130.35714 c -0.68955,6.94804 -4.85362,7.81568 -9.28571,8.21429 L 242.5,472.00506 c -2.89525,-1.2809 -6.19455,0.26662 -8.21429,-7.14285 l 0.35715,-111.07143 z\";" +
                            "-fx-background-color: #AD1457;" +
                            "-fx-background-insets:0,0;" +
                            "-fx-padding: 50;" +
                            "-fx-background-size: contain, contain;"
            );
        }
        else{
            label.setStyle("-fx-shape:\"m 484.76823,327.00506 -265.35715,1.42857 c -3.43583,0.42441 -7.17703,0.33987 -7.85714,5.35715 l -0.35714,130.35714 c 0.68955,6.94804 4.85362,7.81568 9.28571,8.21429 l 233.57143,-0.35715 c 2.89525,-1.2809 6.19455,0.26662 8.21429,-7.14285 l -0.35715,-111.07143 z\";" +
                    "-fx-background-color: #673AB7;" +
                    "-fx-background-insets: 0,1;" +
                    "-fx-padding: 50;");
        }

        return label;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messages = FXCollections.observableArrayList();

        chatListView.setItems(messages);

        Thread thread = new Thread(new MessagesFinder());
        thread.start();
    }

    class MessagesFinder implements Runnable {
        public boolean alive = true;
        private final MessageTuple template = new MessageTuple(anotherUsername, Singleton.INSTANCE.username, null, true);

        @Override
        public void run() {
            while (alive) {
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            MessageTuple tuple = null;
                            try {
//                                tuple = Singleton.INSTANCE.readMessageFromSpace(template);
//
//                                if (tuple != null)
//                                    messages.add(tuple);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    Thread.sleep(300);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
