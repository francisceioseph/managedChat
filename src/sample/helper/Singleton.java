package sample.helper;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import sample.model.ChatMessageRecord;

import java.rmi.RemoteException;

/**
 * Created by francisco on 15/04/15.
 */
public enum Singleton {
    INSTANCE;
    public JavaSpace space;

    public void tupleSpaceLookup(){
        Lookup finder = new Lookup(JavaSpace.class);
        this.space = (JavaSpace) finder.getService();
    }

    public void writeMessageOnSpace(String from, String to, String message) throws RemoteException, TransactionException {
        ChatMessageRecord messageRecord;
        messageRecord = new ChatMessageRecord(from, to, message, false);
        this.space.write(messageRecord, null, Long.MAX_VALUE);
    }

    public ChatMessageRecord readMessageFromSpace (ChatMessageRecord template) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        return (ChatMessageRecord) this.space.take(template, null, 60);
    }
}
