package sample.helper;

import net.jini.core.transaction.TransactionException;
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

    @Override
    public void run() {
        template.checked = false;

        MessageTuple tupleToAnalyse;
        ArrayList wordsFound;

        while (alive) {
            tupleToAnalyse = this.retrieveUncheckedTuple();

            if(Singleton.INSTANCE.observableWords != null && tupleToAnalyse != null){

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
            Singleton.INSTANCE.writeMessageOnSpace(tupleToAnalyse);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    private void sendInformationToWS(MessageTuple tupleToAnalyse, ArrayList wordsFound) {
        System.out.println("Detected Suspicious message: " + tupleToAnalyse);
    }

    private ArrayList analyseTuple(MessageTuple tupleToAnalyse) {
        ArrayList<String> wordsFound = new ArrayList<String>();

        for (String word : Singleton.INSTANCE.observableWords){
            if (tupleToAnalyse.content.contains(word)){
                wordsFound.add(word);
            }
        }

        return wordsFound;
    }

    private MessageTuple retrieveUncheckedTuple() {
        try {
            MessageTuple tuple = Singleton.INSTANCE.readMessageFromSpace(this.template);
            return tuple;
        } catch (Exception e) {
            return null;
        }
    }
}
