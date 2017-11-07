package ftl.activity;

import com.tibco.ftl.FTL;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.Publisher;
import com.tibco.ftl.Realm;
import model.CommandLineArgs;

/**
 * When sending a message with specific format, publishers can extend this class
 */
public abstract class FTLPublisher<T> {
    Publisher pub = null;
    Realm realm = null;

    public FTLPublisher(CommandLineArgs args) {
        try {
            realm = FTL.connectToRealmServer(args.getFtlRealm(), args.getFtlAppName(), null);
            System.out.println(realm);

            pub = realm.createPublisher(args.getFtlEndPoint());
            System.out.println(pub);
        } catch (FTLException e) {
            e.printStackTrace();
        }
    }

    public abstract void send(T messageToSend);

    /**
     * Must be called when sending messages is over
     */
    public void close() {
        try {
            pub.close();
            realm.close();
        } catch (FTLException e) {
            e.printStackTrace();
        }
    }
}
