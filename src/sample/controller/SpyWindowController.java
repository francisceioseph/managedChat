package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import sample.helper.MessagesAnalyser;
import sample.helper.Singleton;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by francisco on 14/04/15.
 */
public class SpyWindowController implements Initializable{
    public ListView wordsListView;
    public TextField newWordTextField;
    public MessagesAnalyser analyser;

    public ToggleButton startButton;

    public void includeObservableWord(ActionEvent actionEvent) {
        String newWord = newWordTextField.getText();

        if (!newWord.equals(" ") && !newWord.equals("")) {
            Singleton.INSTANCE.observableWords.add(newWord);
            newWordTextField.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Singleton.INSTANCE.observableWords = FXCollections.observableArrayList();
        wordsListView.setItems(Singleton.INSTANCE.observableWords);
    }

    private void startWordAnalyses(){
        this.analyser = new MessagesAnalyser();
        Thread thread = new Thread(analyser);
        thread.start();
    }

    public void onStartButtonClick(ActionEvent actionEvent) {
        this.startButton.setDisable(true);
        this.startWordAnalyses();
    }
}
