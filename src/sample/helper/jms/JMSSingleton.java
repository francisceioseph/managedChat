package sample.helper.jms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by francisco on 20/04/15.
 */
public enum JMSSingleton {
    INSTANCE;

    public ObservableList<String> suspiciousMessagesReceived = FXCollections.observableArrayList();

}
