package sample.helper.javaSpaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import sample.main.ChatMain;
import sample.model.MessageTuple;
import sample.model.UserInformationTuple;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by francisco on 15/04/15.
 */
public enum JavaSpacesSingleton {
    INSTANCE;
    public JavaSpace space;
    public String username;
    public ObservableList<UserInformationTuple> chatUsers = FXCollections.observableArrayList();
    public ObservableList<String> observableWords;


//    ************************
//    Get/Set Methods
//    ************************

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    ************************
//       JavaSpaces Methods
//    ************************

    public void tupleSpaceLookup(){
        Lookup finder = new Lookup(JavaSpace.class);
        this.space = (JavaSpace) finder.getService();
    }

    public void writeMessageOnSpace(MessageTuple messageTuple) throws RemoteException, TransactionException {

        this.space.write(messageTuple, null, Long.MAX_VALUE);
    }

    public MessageTuple readMessageFromSpace (MessageTuple template) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        return (MessageTuple) this.space.take(template, null, 100);
    }

    public void signUp(String username, String password) throws RemoteException, TransactionException {
        UserInformationTuple userInformationTuple = new UserInformationTuple(password, username);
        this.writeUserTuple(userInformationTuple);
    }

    public boolean signIn(String username, String password) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        UserInformationTuple template = new UserInformationTuple(password, username);
        UserInformationTuple queryResult = this.readUserTuple(template);

        if (queryResult != null) {
            return true;
        }

        return false;
    }

    private void writeUserTuple(UserInformationTuple userTuple) throws RemoteException, TransactionException {
        this.space.write(userTuple, null, Long.MAX_VALUE);
    }

    public UserInformationTuple readUserTuple(UserInformationTuple template) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        return (UserInformationTuple) this.space.read(template, null, 1000);
    }

    private UserInformationTuple takeUserTuple(UserInformationTuple template) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        return (UserInformationTuple) this.space.take(template, null, 1000);
    }

    public ArrayList<UserInformationTuple> listChatUsers(UserInformationTuple template) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        ArrayList<UserInformationTuple> tuples = new ArrayList<UserInformationTuple>();
        UserInformationTuple tuple;

        while((tuple = (UserInformationTuple) this.takeUserTuple(template)) != null) {
            tuples.add(tuple);
        }

        for (UserInformationTuple aTuple : tuples){
            this.writeUserTuple(aTuple);
        }

        return tuples;
    }

    // ************************************
    // GUI Methods
    // ************************************

    public Stage loadWindow(String path){
        FXMLLoader fxmlLoader = new FXMLLoader(ChatMain.class.getResource(path));

        try {
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            return stage;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
