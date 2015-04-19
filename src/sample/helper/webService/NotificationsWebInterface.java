package sample.helper.webService;

import javax.jws.WebService;
import javax.jws.WebMethod;

/**
 * Created by francisco on 19/04/15.
 */

@WebService
public interface NotificationsWebInterface {
    @WebMethod void notifyChatManager(String spyMessage);
}
