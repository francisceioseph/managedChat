
package sample.helper.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sample.helper.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NotifyChatManager_QNAME = new QName("http://webService.helper.sample/", "notifyChatManager");
    private final static QName _NotifyChatManagerResponse_QNAME = new QName("http://webService.helper.sample/", "notifyChatManagerResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sample.helper.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NotifyChatManager }
     * 
     */
    public NotifyChatManager createNotifyChatManager() {
        return new NotifyChatManager();
    }

    /**
     * Create an instance of {@link NotifyChatManagerResponse }
     * 
     */
    public NotifyChatManagerResponse createNotifyChatManagerResponse() {
        return new NotifyChatManagerResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyChatManager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService.helper.sample/", name = "notifyChatManager")
    public JAXBElement<NotifyChatManager> createNotifyChatManager(NotifyChatManager value) {
        return new JAXBElement<NotifyChatManager>(_NotifyChatManager_QNAME, NotifyChatManager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyChatManagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webService.helper.sample/", name = "notifyChatManagerResponse")
    public JAXBElement<NotifyChatManagerResponse> createNotifyChatManagerResponse(NotifyChatManagerResponse value) {
        return new JAXBElement<NotifyChatManagerResponse>(_NotifyChatManagerResponse_QNAME, NotifyChatManagerResponse.class, null, value);
    }

}
