package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import sample.helper.jms.JMSSingleton;
import sample.helper.jms.ManagerTopicSubscriber;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by francisco on 20/04/15.
 */
public class ChatManager implements Initializable{
    public ListView<String> suspiciousMessagesListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        suspiciousMessagesListView.setItems(JMSSingleton.INSTANCE.suspiciousMessagesReceived);

        System.out.println("Shanya");
        ManagerTopicSubscriber subscriber = new ManagerTopicSubscriber();
        try {
            subscriber.subscribeDefaultTopic();
        } catch (NamingException e) {
            System.out.println("Problems in the name Service... :/");
        } catch (JMSException e) {
            System.out.println("Problems in JMS server");
        }
    }
}
