package sample.helper;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;
import net.jini.core.event.UnknownEventException;
import net.jini.core.transaction.TransactionException;
import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;
import sample.model.MessageTuple;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by francisco on 16/04/15.
 */
public class MessageListener implements RemoteEventListener {
    private final MessageTuple template;
    public final RemoteEventListener stub;

    public MessageListener(MessageTuple template) throws RemoteException {
        this.template = template;
        Exporter myDefaultExporter = new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
                new BasicILFactory(), false, true);

        stub = (RemoteEventListener) myDefaultExporter.export(this);
    }

    @Override
    public void notify(RemoteEvent remoteEvent) throws UnknownEventException, RemoteException {

        try {
            MessageTuple messageRecord = Singleton.INSTANCE.readMessageFromSpace(this.template);
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (UnusableEntryException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}