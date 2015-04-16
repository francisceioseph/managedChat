package sample.helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import sample.model.MessageTuple;
import sample.model.UserInformationTuple;

import java.io.IOException;
import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by francisco on 15/04/15.
 */
public enum Singleton {
    INSTANCE;
    public JavaSpace space;
    public String username;
    public ObservableList<UserInformationTuple> chatUsers = FXCollections.observableArrayList();



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

    public void registerForEvaluatedMessages(MessageTuple template) throws RemoteException, TransactionException {
        MessageListener messageListener = new MessageListener(template);

        this.space.notify(template, null, messageListener, Lease.FOREVER, null);
    }

    public void registerForSpyMessages(MessageTuple template) throws RemoteException, TransactionException {
        SpyMessagesListener spyMessagesListener = new SpyMessagesListener(template);

        this.space.notify(template, null, spyMessagesListener, Lease.FOREVER, null);
    }

    public void registerForNewUserSubscription(UserInformationTuple template) throws IOException, TransactionException {
        NewUserInformationListener newUserInformationListener = new NewUserInformationListener(null);
        this.space.notify(template, null, newUserInformationListener.stub, 24*60*60*1000, new MarshalledObject(new Integer(12345)));
    }

    public void writeMessageOnSpace(String from, String to, String message) throws RemoteException, TransactionException {
        MessageTuple messageRecord;
        messageRecord = new MessageTuple(from, to, message, false);
        this.space.write(messageRecord, null, Long.MAX_VALUE);
    }

    public MessageTuple readMessageFromSpace (MessageTuple template) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        return (MessageTuple) this.space.take(template, null, Long.MAX_VALUE);
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

    private UserInformationTuple takeUsertuple(UserInformationTuple template) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        return (UserInformationTuple) this.space.take(template, null, 1000);
    }

    public ArrayList<UserInformationTuple> listChatUsers(UserInformationTuple template) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        ArrayList<UserInformationTuple> tuples = new ArrayList<UserInformationTuple>();
        UserInformationTuple tuple;

        while((tuple = (UserInformationTuple) this.takeUsertuple(template)) != null) {
            tuples.add(tuple);
        }

        for (UserInformationTuple aTuple : tuples){
            this.writeUserTuple(aTuple);
        }

        return tuples;
    }
}
