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
import sample.model.UserInformationTuple;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by francisco on 16/04/15.
 */
public class NewUserInformationListener implements RemoteEventListener{
    UserInformationTuple template;
    public RemoteEventListener stub;

    public NewUserInformationListener(UserInformationTuple template) throws RemoteException {
        this.template = template;
        Exporter myDefaultExporter = new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
                                                            new BasicILFactory(), false, true);

        stub = (RemoteEventListener) myDefaultExporter.export(this);
    }

    @Override
    public void notify(RemoteEvent remoteEvent) throws UnknownEventException, RemoteException {
        System.out.println("New User Added");

        try {
            UserInformationTuple newUser = Singleton.INSTANCE.readUserTuple(this.template);
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (UnusableEntryException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
