package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import net.jini.core.transaction.TransactionException;
import sample.helper.Singleton;
import sample.helper.MessagesFinder;
import sample.model.MessageTuple;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by francisco on 14/04/15.
 */
public class ChatWindowController implements Initializable{
    public TextField messageTextField;
    private String anotherUsername;

    public ListView<MessageTuple> chatListView;
    private ObservableList<MessageTuple> messages;

    public void enviarMensagem(ActionEvent actionEvent) {
        MessageTuple tupleToSend = new MessageTuple(
                Singleton.INSTANCE.username,
                anotherUsername,
                messageTextField.getText(),
                false
        );

        messages.add(tupleToSend);
        messageTextField.clear();
        
        try {
            Singleton.INSTANCE.writeMessageOnSpace(tupleToSend);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messages = FXCollections.observableArrayList();

        chatListView.setItems(messages);

        chatListView.setCellFactory(new Callback<ListView<MessageTuple>, ListCell<MessageTuple>>() {
            @Override
            public ListCell<MessageTuple> call(ListView<MessageTuple> messageTupleListView) {
                return new MessagesListCell();
            }
        });

        MessageTuple template = new MessageTuple(anotherUsername, Singleton.INSTANCE.username, null, true);
        Thread thread = new Thread(new MessagesFinder(true, anotherUsername, template));
        thread.start();
    }

    static class MessagesListCell extends ListCell<MessageTuple>{
        private final Font labelFont = new Font(12);

        @Override
        public void updateItem(MessageTuple item, boolean empty) {
            super.updateItem(item, empty);
            final Label label = new Label();
            final HBox hBox = new HBox();

            if (item != null) {
                StringBuilder builder = new StringBuilder();
                int tokensnumber = 0;

                for (String token : item.content.split(" ")){
                    tokensnumber++;

                    if (tokensnumber % 3 == 0){
                        builder.append(token.concat("\n"));
                    }
                    else {
                        builder.append(token.concat(" "));
                    }
                }

                label.setText(builder.toString());
                label.setFont(labelFont);

                if (item.userFrom.equals(Singleton.INSTANCE.username)) {
                    label.setStyle("-fx-shape:\"m 263.40043,371.81354 239.20661,0 c 11.29436,2.52786 12.09164,7.15514 13.26758,11.70669 l 0.78044,108.87218 -252.47419,1.17066 c -8.99968,-2.6432 -10.99897,-9.28662 -12.87735,-15.99913 l 0,-88.97081 c 0.89009,-7.29788 2.65743,-14.11985 12.09691,-16.77959 z\";" +
                                    "-fx-background-color: #AD1457;" +
                                    "-fx-background-insets:0,0;" +
                                    "-fx-padding: 10, 10, 10, 20;" +
                                    "-fx-scale-shape: true;" +
                                    "-fx-background-size:cover;" +
                                    "-fx-text-fill: white;"
                    );
                }
                else{
                    label.setStyle("-fx-shape:\"m 504.56173,371.81354 -239.20661,0 c -11.29436,2.52786 -12.09164,7.15514 -13.26758,11.70669 l -0.78044,108.87218 252.47419,1.17066 c 8.99968,-2.6432 10.99897,-9.28662 12.87735,-15.99913 l 0,-88.97081 c -0.89009,-7.29788 -2.65743,-14.11985 -12.09691,-16.77959 z\";" +
                                    "-fx-background-color: #673AB7;" +
                                    "-fx-background-insets:0,0;" +
                                    "-fx-padding: 10, 10, 10, 20;" +
                                    "-fx-scale-shape: true;" +
                                    "-fx-background-size:cover;" +
                                    "-fx-text-fill: white;"
                    );
                }
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.getChildren().add(label);
                setGraphic(hBox);
            }
        }
    }
}
