package sample.helper.webService;

/**
 * Created by francisco on 19/04/15.
 */

import javax.jws.WebService;

@WebService(endpointInterface = "sample.helper.webService.NotificationsWebInterface")
public class NotificationsWeb implements NotificationsWebInterface{
    @Override
    public void notifyChatManager(String spyMessage) {
        //TODO Call JMS and put the message into the default topic.

        System.out.println("Message Received: " + spyMessage);
    }
}
