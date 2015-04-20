package sample.helper.jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * Created by francisco on 20/04/15.
 */
public class ManagerTopicPublisher {
    TopicPublisher publisher;
    TopicSession topicSession;

    public void initializePublisher() throws NamingException, JMSException {
        Hashtable properties;
        Context context;

        TopicConnectionFactory topicConnectionFactory;
        TopicConnection topicConnection;
        Topic targetTopic;

        properties = new Hashtable();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
        properties.put(Context.PROVIDER_URL,
                "tcp://localhost:3035");

        context = new InitialContext(properties);

        topicConnectionFactory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
        topicConnection = topicConnectionFactory.createTopicConnection();
        targetTopic = (Topic) context.lookup("topic1");

        this.topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        this.publisher = topicSession.createPublisher(targetTopic);
    }

    public void publishMessage(String message) throws JMSException {
        TextMessage textMessage = topicSession.createTextMessage();
        textMessage.setText(message);

        this.publisher.publish(textMessage);
    }
}
