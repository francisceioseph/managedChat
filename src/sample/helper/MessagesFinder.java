package sample.helper;

import javafx.application.Platform;
import sample.model.MessageTuple;

/**
 * Created by francisco on 17/04/15.
 */
public class MessagesFinder implements Runnable {
    public boolean alive = true;
    public String anotherUsername;
    private final MessageTuple template;

    public MessagesFinder(boolean alive, String anotherUsername, MessageTuple template) {
        this.alive = alive;
        this.anotherUsername = anotherUsername;
        this.template = template;
    }

    @Override
    public void run() {
        while (alive) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        MessageTuple tuple = null;
                        try {
//                                tuple = Singleton.INSTANCE.readMessageFromSpace(template);
//
//                                if (tuple != null)
//                                    messages.add(tuple);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                Thread.sleep(300);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}