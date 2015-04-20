package sample.helper.jms;

import javafx.application.Platform;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * Created by francisco on 20/04/15.
 */


public class ManagerTopicSubscriber implements MessageListener{

    public void subscribeDefaultTopic() throws NamingException, JMSException {
        Hashtable properties = new Hashtable();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://localhost:3035");

        Context context = new InitialContext(properties);
        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
        TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
        TopicSession topicSession  = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topicConnection.start();

        Topic targetTopic = (Topic) context.lookup("topic1");
        javax.jms.TopicSubscriber subscriber = topicSession.createSubscriber(targetTopic);
        subscriber.setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            try {
                //System.out.println(((TextMessage) message).getText());

                final String stringMessage = ((TextMessage) message).getText();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        JMSSingleton.INSTANCE.suspiciousMessagesReceived.add(stringMessage);
                    }
                });
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
