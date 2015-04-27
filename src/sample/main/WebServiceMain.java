package sample.main;

import sample.helper.webService.NotificationsWeb;

import javax.xml.ws.Endpoint;

/**
 * Created by francisco on 19/04/15.
 */
public class WebServiceMain {
    public static void main(String args[]){
        Endpoint.publish("http://localhost:9999/webService", new NotificationsWeb());
        System.out.println("WebService no Ar!!!!");
    }
}
