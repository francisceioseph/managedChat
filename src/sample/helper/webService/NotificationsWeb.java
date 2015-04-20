package sample.helper.webService;

/**
 * Created by francisco on 19/04/15.
 */

import sample.helper.jms.ManagerTopicPublisher;

import javax.jms.JMSException;
import javax.jws.WebService;
import javax.naming.NamingException;

@WebService(endpointInterface = "sample.helper.webService.NotificationsWebInterface")
public class NotificationsWeb implements NotificationsWebInterface{
    ManagerTopicPublisher publisher = null;

    @Override
    public void notifyChatManager(String spyMessage) {
        //TODO Call JMS and put the message into the default topic.
        System.out.println("Message Received: " + spyMessage);

        if (publisher == null){
            try {

                publisher = new ManagerTopicPublisher();
                publisher.initializePublisher();

            } catch (NamingException e) {
                System.out.println("Problems in the name Service... :/");
            } catch (JMSException e) {
                System.out.println("Problems in JMS server");
            }
        }
        else {
            try {
                publisher.publishMessage(spyMessage);
            } catch (JMSException e) {
                System.out.println("Problems to send messages... Try Later...");
            }
        }
    }
}
