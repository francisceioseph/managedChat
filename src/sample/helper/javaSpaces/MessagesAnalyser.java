package sample.helper.javaSpaces;

import net.jini.core.transaction.TransactionException;
import sample.helper.jms.ManagerTopicPublisher;
import sample.helper.webService.wsGeneratedFiles.NotificationsWebService;
import sample.model.MessageTuple;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * Created by francisco on 18/04/15.
 *
 */
public class MessagesAnalyser implements Runnable {
    MessageTuple template = new MessageTuple();
    public boolean alive = true;

    //********************************
    //       WS Variables
    //********************************
    public sample.helper.webService.wsGeneratedFiles.NotificationsWebInterface notificationsWebService;


    @Override
    public void run() {
        this.connectToWebService();

        template.checked = false;

        MessageTuple tupleToAnalyse;
        ArrayList wordsFound;

        while (alive) {
            tupleToAnalyse = this.retrieveUncheckedTuple();

            if(JavaSpacesSingleton.INSTANCE.observableWords != null && tupleToAnalyse != null){

                wordsFound = this.analyseTuple(tupleToAnalyse);

                if (wordsFound.size() > 0){
                    this.sendInformationToWS(tupleToAnalyse, wordsFound);
                }

                this.finishAnalisis(tupleToAnalyse);
            }

        }
    }

    private void finishAnalisis(MessageTuple tupleToAnalyse) {
        tupleToAnalyse.checked = true;

        try {
            JavaSpacesSingleton.INSTANCE.writeMessageOnSpace(tupleToAnalyse);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    private void sendInformationToWS(MessageTuple tupleToAnalyse, ArrayList wordsFound) {
        if (this.notificationsWebService != null){
            this.notificationsWebService.notifyChatManager(("Detected Suspicious message: " + tupleToAnalyse));
        }
    }

    private ArrayList analyseTuple(MessageTuple tupleToAnalyse) {
        ArrayList<String> wordsFound = new ArrayList<String>();
        String messageToAnalyse = tupleToAnalyse.content.toLowerCase();

        for (String word : JavaSpacesSingleton.INSTANCE.observableWords){
            if (messageToAnalyse.contains(word.toLowerCase())){
                wordsFound.add(word);
            }
        }

        return wordsFound;
    }

    private MessageTuple retrieveUncheckedTuple() {
        try {
            MessageTuple tuple = JavaSpacesSingleton.INSTANCE.readMessageFromSpace(this.template);
            return tuple;
        } catch (Exception e) {
            return null;
        }
    }

    public void connectToWebService(){
        NotificationsWebService webService = new NotificationsWebService();
        this.notificationsWebService = webService.getNotificationsWebPort();
    }
}
