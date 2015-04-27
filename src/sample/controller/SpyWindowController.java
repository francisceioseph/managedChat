package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import sample.helper.javaSpaces.JavaSpacesSingleton;
import sample.helper.javaSpaces.MessagesAnalyser;
import sample.helper.webService.wsGeneratedFiles.NotificationsWebService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by francisco on 14/04/15.
 */
public class SpyWindowController implements Initializable{
    public ListView wordsListView;
    public TextField newWordTextField;
    public MessagesAnalyser analyser;

    public void includeObservableWord(ActionEvent actionEvent) {
        String newWord = newWordTextField.getText();

        if (!newWord.equals(" ") && !newWord.equals("")) {
            JavaSpacesSingleton.INSTANCE.observableWords.add(newWord);
            newWordTextField.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JavaSpacesSingleton.INSTANCE.observableWords = FXCollections.observableArrayList();
        wordsListView.setItems(JavaSpacesSingleton.INSTANCE.observableWords);

        this.startWordAnalyses();
    }

    private void startWordAnalyses(){
        this.analyser = new MessagesAnalyser();
        Thread thread = new Thread(analyser);
        thread.start();
    }
}
