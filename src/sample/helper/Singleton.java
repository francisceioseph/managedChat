package sample.helper;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import sample.model.MessageTuple;
import sample.model.UserInformationTuple;

import java.rmi.RemoteException;

/**
 * Created by francisco on 15/04/15.
 */
public enum Singleton {
    INSTANCE;
    public JavaSpace space;
    public String username;


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
        this.space.write(userInformationTuple, null, Long.MAX_VALUE);
    }

    public boolean signIn(String username, String password) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        UserInformationTuple template = new UserInformationTuple(password, username);
        UserInformationTuple queryResult = (UserInformationTuple) this.space.read(template, null, Long.MAX_VALUE);

        if(queryResult != null){
            return true;
        }

        return false;
    }
}
